package pong.main.comms;

import static pong.main.util.Util.nullCheck;

import java.io.DataOutputStream;
import java.io.IOException;

import pong.main.Main;
import pong.main.game_objects.Ball;
import pong.main.game_objects.Player;
import pong.main.util.Util;

public class OutputData extends Thread {

	private DataOutputStream out;
	private Player you;
	private Ball ball;

	public OutputData(DataOutputStream out, Player player) {
		this(out, player, null);
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
				out.writeDouble(you.getPosition(Util.X));
				out.writeDouble(you.getPosition(Util.Y));
				if (!nullCheck(ball)) {
					out.writeDouble(ball.getPosition(Util.X));
					out.writeDouble(ball.getPosition(Util.Y));
				}
			} catch (IOException ex) {
				System.out.println("Stopping comms...");
				Main.stop();
				break;
			}
		}
	}
}
