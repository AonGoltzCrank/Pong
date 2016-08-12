package pong.main;

import pong.main.game_objects.AI;
import pong.main.game_objects.Ball;
import pong.main.game_objects.BaseGameObject;
import pong.main.game_objects.OnlinePlayer;
import pong.main.game_objects.Player;

public final class ObjectInstantiator {

	public enum GameObjects {
		PLAYER, BALL, AI, ONLINE_PLAYER;

		static boolean contains(GameObjects obj) {
			return obj == PLAYER || obj == BALL || obj == AI || obj == ONLINE_PLAYER;
		}
	}

	public static BaseGameObject CreateNewObject(GameObjects object, Object sideIfValid) {
		if (GameObjects.contains(object)) {
			if (object == GameObjects.PLAYER)
				return new Player((byte) sideIfValid);
			else if (object == GameObjects.BALL)
				return new AI((byte) sideIfValid);

			else if (object == GameObjects.ONLINE_PLAYER)
				return new OnlinePlayer((byte) sideIfValid);

			else if (object == GameObjects.BALL)
				return new Ball();
		}
		throw new IllegalArgumentException(
				"The object you requested to instanciate does not exist in gameobjects enum.");
	}

}
