package pong.main.comms;

import java.io.DataInputStream;
import java.io.IOException;

import pong.main.WorldManager;

import static pong.main.Util.i2double;

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
				int otherPlayerPos = in.readInt();
				double playerPos = i2double(otherPlayerPos);
				WorldManager.getInstance(null).updatePlayer2Pos(playerPos);
			} catch (IOException ex) {
				ex.printStackTrace();
				break;
			}
		}
	}
}
