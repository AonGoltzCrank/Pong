package pong.main.screens;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.TrueTypeFont;

import pong.main.game_objects.BaseScreenObject;

public class ScreenTextGroup extends BaseScreenObject {

	private ArrayList<ScreenText> items = new ArrayList<>();

	public ScreenTextGroup(ScreenText... items) {
		for (ScreenText item : items)
			addItem(item);
	}

	public ScreenTextGroup(int size) {
	}

	public void addItem(ScreenText text) {
		items.add(text);
	}

	public void setSelected(int index) {
		if (index > items.size() - 1)
			throw new IndexOutOfBoundsException("setSelected index in ScreenTextGroup is larger than the group.");
		for (int i = 0; i < items.size(); i++) {
			if (i != index) {
				if (items.get(i).getSelected())
					items.get(i).setSelected(false);
			} else
				items.get(index).setSelected(true);
		}
	}

	public void remove(int index) {
		if (index > items.size() - 1)
			throw new IndexOutOfBoundsException("remove index in ScreenTextGroup is larger than the group.");
		items.remove(items.get(index));
	}

	@Override
	public void render() {
		for (ScreenText item : items)
			item.render();
	}

	public int calculateTotalHeightSize() {
		int sum = 0;
		for (ScreenText item : items) {
			int size = item.getLineSize();
			if (size == -1)
				throw new RuntimeException("Line size returned -1, ttf variable in a ScreenText with value "
						+ item.getValue() + " is null.");
			sum += size;
		}
		return sum;
	}

	public static int getTotalLineSize(int numOfLines) {
		int sizeBold = new TrueTypeFont(new Font("Terminal", Font.BOLD, 24), false).getLineHeight();
		int sizePlain = new TrueTypeFont(new Font("Terminal", Font.PLAIN, 24), false).getLineHeight()
				* (numOfLines - 1);
		return sizeBold + sizePlain;
	}

	public static int getStringSize(String text, boolean flag) {
		return flag ? new TrueTypeFont(new Font("Terminal", Font.BOLD, 24), false).getHeight(text)
				: new TrueTypeFont(new Font("Terminal", Font.BOLD, 24), false).getWidth(text);
	}

	public BaseScreenObject[] getScreentTexts() {
		return Arrays.copyOf(items.toArray(), items.size(), BaseScreenObject[].class);
	}
}
