package pong.main.comms;

import java.io.DataOutputStream;
import java.io.IOException;

import pong.main.Ball;
import pong.main.Player;

public class OutputData extends Thread {

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
		you = player;
		this.ball = ball;
		start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				out.writeInt((int) you.getPositionY());
			} catch (IOException ex) {
				ex.printStackTrace();
				break;
			}
		}
	}
}
