package pong.main;

import pong.main.ObjectInstantiator.GameObjects;
import pong.main.game_objects.BaseScreenObject;
import pong.main.screens.Screen;
import pong.main.screens.ScreenText;
import pong.main.screens.ScreenTextGroup;

public class ScreenManager extends BaseScreenObject {

	private static ScreenTextGroup currentGroup;

	public enum Screens {
		MAINMENU, PLAYONLINE, PLAYOFFLINE, PLAYONLINE_IS_HOST;
	}

	public void loadScreen(Screen screen) {
		// WorldManager.getInstance(null).generateNewObjectsOnScreen(screen);
	}

	public static Screen generateScreen(Screens screen) {
		int centerX = 400, centerY = 300;
		if (screen.equals(Screens.MAINMENU)) {
			int firstLineSizeX = ScreenTextGroup.getStringSize("Play Offline", false);
			int secLineSizeX = ScreenTextGroup.getStringSize("Play Online", false);
			int thirdLineSizeX = ScreenTextGroup.getStringSize("Exit", false);
			int secLineSizeY = ScreenTextGroup.getStringSize("Play Online", true);
			int thirdLineSizeY = ScreenTextGroup.getStringSize("Exit", true);
			currentGroup = new ScreenTextGroup(
					new ScreenText("Play Offline", centerX - (firstLineSizeX / 2), centerY + (secLineSizeY / 2)),
					new ScreenText("Play Online", centerX - (secLineSizeX / 2), centerY - (secLineSizeY / 2)),
					new ScreenText("Exit", centerX - (thirdLineSizeX / 2),
							centerY - (secLineSizeY / 2) - thirdLineSizeY));
			return new Screen(currentGroup.getScreentTexts());
		} else if (screen.equals(Screens.PLAYONLINE_IS_HOST)) {
			int firstLineSizeX = ScreenTextGroup.getStringSize("I am the host!", false);
			int secLineSizeX = ScreenTextGroup.getStringSize("I am not the host!", false);
			int thirdLineSizeX = ScreenTextGroup.getStringSize("Back", false);
			int secLineSizeY = ScreenTextGroup.getStringSize("I am no the host!", true);
			int thirdLineSizeY = ScreenTextGroup.getStringSize("Back", true);
			currentGroup = new ScreenTextGroup(
					new ScreenText("I am the host!", centerX - (firstLineSizeX / 2), centerY + (secLineSizeY / 2)),
					new ScreenText("I am not the host!", centerX - (secLineSizeX / 2), centerY - (secLineSizeY / 2)),
					new ScreenText("Back", centerX - (thirdLineSizeX / 2),
							centerY - (secLineSizeY / 2) - thirdLineSizeY));
			return new Screen(currentGroup.getScreentTexts());
		} else if (screen.equals(Screens.PLAYOFFLINE)) {
			return new Screen(GameObjects.PLAYER, GameObjects.BALL, GameObjects.AI);
		} else if (screen.equals(Screens.PLAYONLINE))
			return new Screen(GameObjects.PLAYER, GameObjects.BALL, GameObjects.ONLINE_PLAYER);
		else
			throw new IllegalArgumentException("The screen passed in is not valid.");
	}
}
