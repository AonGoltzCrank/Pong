package pong.main.comms;

import java.io.DataInputStream;
import java.io.IOException;

import pong.main.Main;
import pong.main.WorldManager;

public class InputData extends Thread {

	private DataInputStream in;

	public InputData(DataInputStream in) {
		this.in = in;
		start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				double otherPlayerPosX = in.readDouble();
				double otherPlayerPosY = in.readDouble();
				double ballX = in.readDouble();
				double ballY = in.readDouble();
				WorldManager.getInstance(null).updateObjectLocation("OnlinePlayer", otherPlayerPosX, otherPlayerPosY);
				WorldManager.getInstance(null).updateObjectLocation("Ball", ballX, ballY);
			} catch (IOException ex) {
				System.out.println("Stopping comms...");
				Main.stop();
				break;
			}
		}
	}
}
