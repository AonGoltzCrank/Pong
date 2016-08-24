package pong.main.game_objects;

import pong.main.util.Util;

public final class ScoreKeeper {

	private static int score_left = 0;
	private static int score_right = 0;

	private static ScoreItem[] scores;
	private static ScoreKeeper keeper;

	public static ScoreKeeper getInstance(ScoreItem... displays) {
		return keeper == null ? new ScoreKeeper(displays) : keeper;
	}

	private ScoreKeeper(ScoreItem... displays) {
		scores = displays;
	}

	public static void pointTo(byte side) {
		if (side == Util.LEFT) {
			score_left++;
		} else if (side == Util.RIGHT) {
			score_right++;
		}
		scores[side].point(side == Util.LEFT ? score_left : score_right);
	}

	public static byte end() {
		return (score_left > score_right ? Util.LEFT : (score_left == score_right ? Util.DRAW : Util.RIGHT));
	}

	public static void reset() {
		score_left = 0;
		score_right = 0;
	}
}
