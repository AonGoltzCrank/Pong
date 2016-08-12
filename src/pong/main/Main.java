package pong.main;

import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import pong.main.comms.InputData;
import pong.main.comms.OutputData;
import pong.main.game_objects.AI;
import pong.main.game_objects.Ball;
import pong.main.game_objects.OnlinePlayer;
import pong.main.game_objects.Player;
import pong.main.util.KeyHandler;

public class Main implements Runnable {

	private static boolean $running = true;
	private long $wnd;

	private final int width = 800;
	private final int height = 600;
	private final String title = "Pong";

	private WorldManager wManager;
	private CollisionsManager cManager;

	public static KeyHandler $keyHandler;

	private Player player;
	private AI ai;
	private Ball ball;

	private final int fps = 120;
	private long startTime;
	private long initTime = System.currentTimeMillis();
	private int logicStep = 0;
	private int renderStep = 0;

	// =================== Online ================
	private boolean isHost = false;
	public static boolean isOnline = false;

	private ServerSocket hostToSRV_Socket;
	private Socket fromClientToSRV_Socket;
	private Socket incomingClientToSRV_Socket;

	private final int port = 4242;

	private OnlinePlayer onlinePlayer;

	private OutputData outData;
	private InputData inData;

	public static void main(String[] args) {
		Main game = new Main(false);
		game.init_Thread();
	}

	public Main(boolean isOnline) {
		if (Main.isOnline = isOnline)
			determineHost();
	}

	public void init_Thread() {
		// >> Setting up the game thread and starting it.
		Thread gameThread = new Thread(this, title);
		gameThread.start();
	}

	public void init() {
		if (!GLFW.glfwInit())
			throw new RuntimeException("Failed to initialize GLFW Library!");

		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

		$wnd = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);

		if ($wnd == NULL)
			throw new RuntimeException("Failed to create window!");

		GLFWVidMode monitor = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

		GLFW.glfwSetWindowPos($wnd, (monitor.width() - 800) / 2, (monitor.height() - 600) / 2);

		GLFW.glfwMakeContextCurrent($wnd);

		GLFW.glfwShowWindow($wnd);

		// >> OpenGL items:

		GL.createCapabilities();

		/**
		 * >> Setting the screen in orthogonal mode so that coordinates in GL
		 * can be written in pixels.
		 */
		GL11.glOrtho(0.f, (float) width, 0.f, (float) height, 0.f, 1.f);

		// >> Input handling
		GLFW.glfwSetKeyCallback($wnd, $keyHandler = new KeyHandler());

		// >> Setting up the world.
		wManager = WorldManager.getInstance($keyHandler);
		if (wManager.getKeyHandler() == null)
			wManager.setKeyHandler($keyHandler);

		// >> Setting up the collisions manager.
		cManager = CollisionsManager.getInstance(wManager);

		wManager.renderScreen(ScreenManager.generateScreen(ScreenManager.Screens.MAINMENU));

	}

	public void render() {
		GLFW.glfwSwapBuffers($wnd);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		wManager.render();
	}

	public void update() {
		GLFW.glfwPollEvents();
		cManager.update();
		wManager.update();
	}

	private long getTimeSinceLastLogicStep() {
		long currentTime = System.currentTimeMillis();
		return currentTime - startTime;
	}

	@Override
	public void run() {
		init();
		while ($running) {
			// =========== Update Loop ==============
			if ((double) getTimeSinceLastLogicStep() / (double) 1000 >= (double) 1 / (double) fps) {
				update();
				startTime = System.currentTimeMillis();
				logicStep++;
				if (System.currentTimeMillis() - initTime >= 1000) {
					initTime = System.currentTimeMillis();
					System.out.println("Logic Steps in 1 Second: " + logicStep + " , Render Steps: " + renderStep);
					logicStep = 0;
					renderStep = 0;
				}
			}
			// ========== Render Loop ================
			render();
			renderStep++;

			// ========== Close Game ===============
			if (GLFW.glfwWindowShouldClose($wnd)) {
				$running = false;
				wManager.endGame();
			}

		}
		/*
		 * if (isOnline) { System.out.println("Playing in online mode...");
		 * System.out.println("We are " + (isHost ? "" : "not ") + "the host.");
		 * try { DataOutputStream out; DataInputStream in; ball = new Ball(); if
		 * (isHost) { hostToSRV_Socket = new ServerSocket(port);
		 * System.out.println("Server socket connected, awaiting client...");
		 * incomingClientToSRV_Socket = hostToSRV_Socket.accept();
		 * System.out.println("Client Connected..."); out = new
		 * DataOutputStream(incomingClientToSRV_Socket.getOutputStream()); in =
		 * new DataInputStream(incomingClientToSRV_Socket.getInputStream());
		 * byte side = (byte) new Random().nextInt(2); if (side > 1) side = 1;
		 * player = new Player(side); onlinePlayer = new OnlinePlayer((byte)
		 * (side == 1 ? 0 : 1)); out.writeByte((byte) (side == 1 ? 0 : 1));
		 * out.flush(); outData = new OutputData(out, player, ball); } else {
		 * fromClientToSRV_Socket = new Socket(getConnectionIP(), port);
		 * System.out.println("Connected to server..."); out = new
		 * DataOutputStream(fromClientToSRV_Socket.getOutputStream()); in = new
		 * DataInputStream(fromClientToSRV_Socket.getInputStream()); byte side =
		 * in.readByte(); player = new Player(side); onlinePlayer = new
		 * OnlinePlayer((byte) (side == 1 ? 0 : 1)); } if (outData == null)
		 * outData = new OutputData(out, player); if (inData == null) inData =
		 * new InputData(in, isHost); wManager.addObject(player);
		 * wManager.addObject(onlinePlayer); wManager.addObject(ball); } catch
		 * (IOException ex) { ex.printStackTrace(); } } else { player = new
		 * Player((byte) 0); ball = new Ball(); ai = new AI((byte) 1);
		 * wManager.addObject(player); wManager.addObject(ball);
		 * wManager.addObject(ai); } startTime = System.currentTimeMillis(); try
		 * { while ($running) { // =========== Update Loop ============== if
		 * ((double) getTimeSinceLastLogicStep() / (double) 1000 >= (double) 1 /
		 * (double) fps) { update(); startTime = System.currentTimeMillis();
		 * logicStep++; if (System.currentTimeMillis() - initTime >= 1000) {
		 * initTime = System.currentTimeMillis();
		 * System.out.println("Logic Steps in 1 Second: " + logicStep +
		 * " , Render Steps: " + renderStep); logicStep = 0; renderStep = 0; } }
		 * // ========== Render Loop ================ render(); renderStep++;
		 * 
		 * // ========== Close Game =============== if
		 * (GLFW.glfwWindowShouldClose($wnd)) { $running = false;
		 * inData.interrupt(); outData.interrupt(); } } } catch (Exception ex) {
		 * inData.interrupt(); outData.interrupt(); } finally { try {
		 * inData.interrupt(); outData.interrupt(); } catch
		 * (NullPointerException ex) { } }
		 */
	}

	// ======================== Online ======================

	private void determineHost() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("res\\isHost.bit")));
			String data = "";
			if ((data = reader.readLine()) != null) {
				if (data.equals("0"))
					isHost = true;
			} else {
				reader.close();
				throw new RuntimeException("No host byte present.");
			}
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private String getConnectionIP() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("res\\conn.ip")));
			String data = "";
			if ((data = reader.readLine()) != null) {
				reader.close();
				return data;
			} else {
				reader.close();
				throw new RuntimeException("No connection IP present.");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return "0.0.0.0";
	}

	public static void stop() {
		$running = false;
	}
}
