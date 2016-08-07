package pong.main.comms;

import java.io.DataOutputStream;
import java.io.IOException;

import pong.main.Ball;
import pong.main.Main;
import pong.main.Player;

public class OutputData extends Thread {

	public static final byte X = 0;
	public static final byte Y = 1;

	private DataOutputStream out;
	private Player you;
	private Ball ball;

	public OutputData(DataOutputStream out, Player player) {
		this.out = out;
		you = player;
		start();
	}

	public OutputData(DataOutputStream out, Player player, Ball ball) {
		this.out = out;
		this.ball = ball;
		you = player;
		start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				out.writeDouble(you.getPosition(X));
				out.writeDouble(you.getPosition(Y));
				if (ball != null) {
					out.writeDouble(ball.getPosition(X));
					out.writeDouble(ball.getPosition(Y));
				}
			} catch (IOException ex) {
				System.out.println("Stopping comms...");
				Main.stop();
				break;
			}
		}
	}
}
