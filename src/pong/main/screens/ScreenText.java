package pong.main.screens;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;

import pong.main.game_objects.BaseScreenObject;

public class ScreenText extends BaseScreenObject {

	private String text;
	private double xPos;
	private double yPos;

	private TrueTypeFont ttf;

	private boolean selected;
	private boolean changed = false;

	public ScreenText(String value) {
		this(value, 0, 0);
	}

	public ScreenText(String value, double x, double y) {
		text = value;
		xPos = x;
		yPos = y;
		generateFont();
	}

	@Override
	public void render() {
		ttf.drawString((float) xPos, (float) yPos, text);
	}

	@Override
	public void update() {
		if (changed) {
			generateFont();
			changed = false;
		}
	}

	public void setSelected(boolean flag) {
		if (selected != flag) {
			selected = flag;
			changed = true;
		}
	}

	public boolean getSelected() {
		return selected;
	}

	public String getValue() {
		return text;
	}

	private void generateFont() {
		Font awtFont = new Font("Terminal", selected ? Font.BOLD : Font.PLAIN, 24);
		ttf = new TrueTypeFont(awtFont, false);
	}

	public int getLineSize() {
		if (ttf != null)
			return ttf.getLineHeight();
		return -1;
	}

}
