package pong.main.util;

import java.util.ArrayList;

public final class CoordGenerator {

	private static double X;
	private static double Y;
	private static int W;
	private static int H;
	private static byte lW;

	static {
		X = 0;
		Y = 0;
		W = 100;
		H = 100;
		lW = 10;
	}

	private CoordGenerator() {
	}

	private enum Quads {
		BOTTOM_L, BOTTOM_M, BOTTOM_S, BOTTOM_M_LEFT, BOTTOM_M_MIDDLE, BOTTOM_M_RIGHT, BOTTOM_S_LEFT, BOTTOM_S_MIDDLE, BOTTOM_S_RIGHT, //
		TOP_L, TOP_M, TOP_S, TOP_M_LEFT, TOP_M_MIDDLE, TOP_M_RIGHT, TOP_S_LEFT, TOP_S_MIDDLE, TOP_S_RIGHT, //
		LEFT_L, LEFT_M, LEFT_S, LEFT_M_TOP, LEFT_M_MIDDLE, LEFT_M_BOTTOM, LEFT_S_TOP, LEFT_S_MIDDLE, LEFT_S_BOTTOM, //
		RIGHT_L, RIGHT_M, RIGHT_S, RIGHT_M_TOP, RIGHT_M_MIDDLE, RIGHT_M_BOTTOM, RIGHT_S_TOP, RIGHT_S_MIDDLE, RIGHT_S_BOTTOM, ///
		CENTER_V_L, CENTER_V_M, CENTER_V_S, CENTER_V_M_TOP, CENTER_V_M_MIDDLE, CENTER_V_M_BOTTOM, CENTER_V_S_TOP, CENTER_V_S_MIDDLE, CENTER_V_S_BOTTOM, //
		CENTER_H_L, CENTER_H_M, CENTER_H_S, CENTER_H_M_LEFT, CENTER_H_M_MIDDLE, CENTER_H_M_RIGHT, CENTER_H_S_LEFT, CENTER_H_S_MIDDLE, CENTER_H_S_RIGHT, //
		SLASH_T2B_L, SLASH_T2B_M, SLASH_T2B_S, SLASH_T2B_M_TOP, SLASH_T2B_M_MIDDLE, SLASH_T2B_M_BOTTOM, SLASH_T2B_S_TOP, SLASH_T2B_S_MIDDLE, SLASH_T2B_S_BOTTOM, //
		SLASH_B2T_L, SLASH_B2T_M, SLASH_B2T_S, SLASH_B2T_M_TOP, SLASH_B2T_M_MIDDLE, SLASH_B2T_M_BOTTOM, SLASH_B2T_S_TOP, SLASH_B2T_S_MIDDLE, SLASH_B2T_S_BOTTOM, //
		V_CUSTOM_QUAD_LEFT, V_CUSTOM_QUAD_RIGHT;
	}

	public static Coords genCoords(Object input) {
		return genCoords(input, lW);
	}

	public static Coords genCoords(Object input, byte line_width) {
		return genCoords(input, line_width, X, Y);
	}

	public static Coords genCoords(Object input, byte line_width, double xPos, double yPos) {
		return genCoords(input, line_width, xPos, yPos, W, H);
	}

	public static Coords genCoords(Object input, byte line_width, double xPos, double yPos, int width, int height) {
		Coords item = null;
		lW = line_width;
		X = xPos;
		Y = yPos;
		W = width;
		H = height;
		if (input instanceof Integer)
			item = getCoordsForInt((int) input);
		else if (input instanceof String)
			item = getCoordsForString(((String) input).toUpperCase());
		else
			throw new IllegalArgumentException("Object type is not supported.");
		return item;
	}

	private static Coords getCoordsForInt(int input) {
		return getCoordsForString(String.valueOf(input));
	}

	private static Coords getCoordsForString(String input) {
		ArrayList<ArrayList<MultiMap<Double, Double>>> lists = new ArrayList<>();
		for (char c : input.toCharArray())
			lists.add(getCoordsForChar(c));
		return new CoordGenerator().new Coords(lists);
	}

	private static ArrayList<MultiMap<Double, Double>> getCoordsForChar(char c) {
		ArrayList<MultiMap<Double, Double>> coordsForChar = new ArrayList<>();
		ArrayList<Quads> quads = new ArrayList<>();
		switch (c) {
		case '0':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.SLASH_B2T_L);
			break;
		case '1':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.CENTER_V_L);
			quads.add(Quads.TOP_M_LEFT);
			break;
		case '2':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.LEFT_M_BOTTOM);
			quads.add(Quads.CENTER_H_L);
			quads.add(Quads.RIGHT_M_TOP);
			break;
		case '3':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.CENTER_H_L);
			break;
		case '4':
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.LEFT_M_TOP);
			quads.add(Quads.CENTER_H_L);
			break;
		case '5':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.RIGHT_M_BOTTOM);
			quads.add(Quads.CENTER_H_L);
			quads.add(Quads.LEFT_M_TOP);
			break;
		case '6':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.CENTER_H_L);
			quads.add(Quads.RIGHT_M_BOTTOM);
			break;
		case '7':
			quads.add(Quads.TOP_L);
			quads.add(Quads.RIGHT_L);
			break;
		case '8':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.CENTER_H_L);
			break;
		case '9':
			quads.add(Quads.TOP_L);
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.LEFT_M_TOP);
			quads.add(Quads.CENTER_H_L);
			break;
		case 'A':
			quads.add(Quads.TOP_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.CENTER_H_L);
			break;
		case 'B':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_M_LEFT);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.CENTER_H_L);
			quads.add(Quads.RIGHT_M_BOTTOM);
			quads.add(Quads.CENTER_V_M_TOP);
			break;
		case 'C':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.LEFT_L);
			break;
		case 'D':
			quads.add(Quads.LEFT_L);
			quads.add(Quads.SLASH_T2B_M_TOP);
			quads.add(Quads.SLASH_B2T_M_BOTTOM);
			break;
		case 'E':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.CENTER_H_L);
			break;
		case 'F':
			quads.add(Quads.LEFT_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.CENTER_H_L);
			break;
		case 'G':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.RIGHT_M_BOTTOM);
			quads.add(Quads.CENTER_H_M_RIGHT);
			break;
		case 'H':
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.CENTER_H_L);
			break;
		case 'I':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.CENTER_V_L);
			break;
		case 'J':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.LEFT_M_BOTTOM);
			break;
		case 'K':
			quads.add(Quads.CENTER_V_L);
			quads.add(Quads.SLASH_T2B_M_BOTTOM);
			quads.add(Quads.SLASH_B2T_M_TOP);
			break;
		case 'L':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.LEFT_L);
			break;
		case 'M':
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.SLASH_T2B_M_TOP);
			quads.add(Quads.SLASH_B2T_M_TOP);
			break;
		case 'N':
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.SLASH_T2B_L);
			break;
		case 'O':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.RIGHT_L);
			break;
		case 'P':
			quads.add(Quads.TOP_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.RIGHT_M_TOP);
			quads.add(Quads.CENTER_H_L);
			break;
		case 'Q':
			quads.add(Quads.TOP_L);
			quads.add(Quads.CENTER_H_L);
			quads.add(Quads.LEFT_M_TOP);
			quads.add(Quads.RIGHT_M_TOP);
			quads.add(Quads.SLASH_T2B_M_BOTTOM);
			break;
		case 'R':
			quads.add(Quads.LEFT_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.RIGHT_M_TOP);
			quads.add(Quads.CENTER_H_L);
			quads.add(Quads.SLASH_T2B_M_BOTTOM);
			break;
		case 'S':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.CENTER_H_L);
			quads.add(Quads.RIGHT_M_BOTTOM);
			quads.add(Quads.LEFT_M_TOP);
			break;
		case 'T':
			quads.add(Quads.TOP_L);
			quads.add(Quads.CENTER_V_L);
			break;
		case 'U':
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.BOTTOM_L);
			break;
		case 'V':
			quads.add(Quads.V_CUSTOM_QUAD_LEFT);
			quads.add(Quads.V_CUSTOM_QUAD_RIGHT);
			break;
		case 'W':
			quads.add(Quads.RIGHT_L);
			quads.add(Quads.LEFT_L);
			quads.add(Quads.SLASH_T2B_M_BOTTOM);
			quads.add(Quads.SLASH_B2T_M_BOTTOM);
			break;
		case 'X':
			quads.add(Quads.SLASH_T2B_L);
			quads.add(Quads.SLASH_B2T_L);
			break;
		case 'Y':
			quads.add(Quads.CENTER_V_M_BOTTOM);
			quads.add(Quads.SLASH_B2T_M_TOP);
			quads.add(Quads.SLASH_T2B_M_TOP);
			break;
		case 'Z':
			quads.add(Quads.BOTTOM_L);
			quads.add(Quads.TOP_L);
			quads.add(Quads.SLASH_B2T_L);
			break;
		}
		for (Quads quad : quads)
			coordsForChar.add(getCoordsForQuad(quad));
		return coordsForChar;
	}

	private static MultiMap<Double, Double> getCoordsForQuad(Quads quad) {
		MultiMap<Double, Double> quadCoords = new MultiMap<Double, Double>(4);

		double xL = X;
		double yL = Y;

		int wSize = W;
		int hSize = H;
		byte lineSize = lW;

		double xH = xL + wSize;
		double yH = yL + hSize;

		double xM = xL + ((double) wSize / 2.0);
		double yM = yL + ((double) hSize / 2.0);

		double xH_NoWidth = xH - lineSize;
		double yH_NoWidth = yH - lineSize;

		double xL_WithWidth = xL + lineSize;
		double yL_WithWidth = yL + lineSize;

		double xM_NoWidth = xM - (lineSize / (byte) 2);
		double xM_WithWidth = xM + (lineSize / (byte) 2);

		double yM_NoWidth = yM - (lineSize / (byte) 2);
		double yM_WithWidth = yM + (lineSize / (byte) 2);

		double xL_P_1$4 = xL + ((double) wSize / 4.0);
		double yL_P_1$4 = yL + ((double) hSize / 4.0);

		double xH_M_1$4 = xH - ((double) wSize / 4.0);
		double yH_M_1$4 = yH - ((double) hSize / 4.0);

		double xL_P_1$8 = xL + ((double) wSize / 8.0);
		double yL_P_1$8 = yL + ((double) hSize / 8.0);

		double xH_M_1$8 = xH - ((double) wSize / 8.0);
		double yH_M_1$8 = yH - ((double) hSize / 8.0);

		double xL_P_3$8 = xL + (3 * ((double) wSize / 8.0));
		double yL_P_3$8 = yL + (3 * ((double) hSize / 8.0));

		double xH_M_3$8 = xH - (3 * ((double) wSize / 8.0));
		double yH_M_3$8 = yH - (3 * ((double) hSize / 8.0));

		double slashSize = Math.sqrt(Math.pow(lineSize, 2) / 2);

		double xL_P_S_T2B = xL + slashSize;
		double yL_P_S_T2B = yL + slashSize;

		double xH_M_S_T2B = xH - slashSize;
		double yH_M_S_T2B = yH - slashSize;

		double xL_S_T2B_1$4 = xL_P_1$4 + slashSize;
		double yL_S_T2B_1$4 = yL_P_1$4 + slashSize;

		double xL_S_T2B_1$8 = xL_P_1$8 + slashSize;
		double yL_S_T2B_1$8 = yL_P_1$8 + slashSize;

		double xL_S_T2B_3$8 = xL_P_3$8 + slashSize;
		double yL_S_T2B_3$8 = yL_P_3$8 + slashSize;

		double xH_S_T2B_1$4 = xH_M_1$4 + slashSize;
		double yH_S_T2B_1$4 = yH_M_1$4 + slashSize;

		double xH_S_T2B_1$8 = xH_M_1$8 + slashSize;
		double yH_S_T2B_1$8 = yH_M_1$8 + slashSize;

		double xH_S_T2B_3$8 = xH_M_3$8 + slashSize;
		double yH_S_T2B_3$8 = yH_M_3$8 + slashSize;

		double xL_P_S_B2T = xL + slashSize;
		double yL_P_S_B2T = yL + slashSize;

		double xH_M_S_B2T = xH - slashSize;
		double yH_M_S_B2T = yH - slashSize;

		double xL_S_B2T_1$4 = xL_P_1$4 - slashSize;
		double yL_S_B2T_1$4 = yL_P_1$4 + slashSize;

		double xL_S_B2T_1$8 = xL_P_1$8 - slashSize;
		double yL_S_B2T_1$8 = yL_P_1$8 + slashSize;

		double xL_S_B2T_3$8 = xL_P_3$8 - slashSize;
		double yL_S_B2T_3$8 = yL_P_3$8 + slashSize;

		double xH_S_B2T_1$4 = xH_M_1$4 - slashSize;
		double yH_S_B2T_1$4 = yH_M_1$4 + slashSize;

		double xH_S_B2T_1$8 = xH_M_1$8 - slashSize;
		double yH_S_B2T_1$8 = yH_M_1$8 + slashSize;

		double xH_S_B2T_3$8 = xH_M_3$8 - slashSize;
		double yH_S_B2T_3$8 = yH_M_3$8 + slashSize;

		double centerPointX = xL + ((xH - xL) / 2);
		double centerPointY = yL + ((yH - yL) / 2);

		switch (quad) {
		case BOTTOM_L:
			quadCoords.put(xL, yL);
			quadCoords.put(xL, yL_WithWidth);
			quadCoords.put(xH, yL_WithWidth);
			quadCoords.put(xH, yL);
			break;
		case BOTTOM_M:
			quadCoords.put(xL_P_1$4, yL);
			quadCoords.put(xL_P_1$4, yL_WithWidth);
			quadCoords.put(xH_M_1$4, yL_WithWidth);
			quadCoords.put(xH_M_1$4, yL);
			break;
		case BOTTOM_S:
			quadCoords.put(xL_P_3$8, yL);
			quadCoords.put(xL_P_3$8, yL_WithWidth);
			quadCoords.put(xH_M_3$8, yL_WithWidth);
			quadCoords.put(xH_M_3$8, yL);
			break;
		case BOTTOM_M_LEFT:
			quadCoords.put(xL, yL);
			quadCoords.put(xL, yL_WithWidth);
			quadCoords.put(xM_WithWidth, yL_WithWidth);
			quadCoords.put(xM_WithWidth, yL);
			break;
		case BOTTOM_M_MIDDLE:
			return getCoordsForQuad(Quads.BOTTOM_M_MIDDLE);
		case BOTTOM_M_RIGHT:
			quadCoords.put(xM_NoWidth, yL);
			quadCoords.put(xM_NoWidth, yL_WithWidth);
			quadCoords.put(xH, yL_WithWidth);
			quadCoords.put(xH, yL);
			break;
		case BOTTOM_S_LEFT:
			quadCoords.put(xL_P_1$8, yL);
			quadCoords.put(xL_P_1$8, yL_WithWidth);
			quadCoords.put(xL_P_3$8, yL_WithWidth);
			quadCoords.put(xL_P_3$8, yL);
			break;
		case BOTTOM_S_MIDDLE:
			return getCoordsForQuad(Quads.BOTTOM_S_MIDDLE);
		case BOTTOM_S_RIGHT:
			quadCoords.put(xH_M_1$8, yL);
			quadCoords.put(xH_M_1$8, yL_WithWidth);
			quadCoords.put(xH_M_3$8, yL_WithWidth);
			quadCoords.put(xH_M_3$8, yL);
			break;
		case TOP_L:
			quadCoords.put(xL, yH);
			quadCoords.put(xL, yH_NoWidth);
			quadCoords.put(xH, yH_NoWidth);
			quadCoords.put(xH, yH);
			break;
		case TOP_M:
			quadCoords.put(xL_P_1$4, yH);
			quadCoords.put(xL_P_1$4, yH_NoWidth);
			quadCoords.put(xH_M_1$4, yH_NoWidth);
			quadCoords.put(xH_M_1$4, yH);
			break;
		case TOP_S:
			quadCoords.put(xL_P_3$8, yH);
			quadCoords.put(xL_P_3$8, yH_NoWidth);
			quadCoords.put(xH_M_3$8, yH_NoWidth);
			quadCoords.put(xH_M_3$8, yH);
			break;
		case TOP_M_LEFT:
			quadCoords.put(xL, yH);
			quadCoords.put(xL, yH_NoWidth);
			quadCoords.put(xM_WithWidth, yH_NoWidth);
			quadCoords.put(xM_WithWidth, yH);
			break;
		case TOP_M_MIDDLE:
			return getCoordsForQuad(Quads.TOP_M_MIDDLE);
		case TOP_M_RIGHT:
			quadCoords.put(xM_NoWidth, yH);
			quadCoords.put(xM_NoWidth, yH_NoWidth);
			quadCoords.put(xH, yH_NoWidth);
			quadCoords.put(xH, yH);
			break;
		case TOP_S_LEFT:
			quadCoords.put(xL_P_1$8, yH);
			quadCoords.put(xL_P_1$8, yH_NoWidth);
			quadCoords.put(xL_P_3$8, yH_NoWidth);
			quadCoords.put(xL_P_3$8, yH);
			break;
		case TOP_S_MIDDLE:
			return getCoordsForQuad(Quads.TOP_S_MIDDLE);
		case TOP_S_RIGHT:
			quadCoords.put(xH_M_1$8, yH);
			quadCoords.put(xH_M_1$8, yH_NoWidth);
			quadCoords.put(xH_M_3$8, yH_NoWidth);
			quadCoords.put(xH_M_3$8, yH);
			break;
		case LEFT_L:
			quadCoords.put(xL, yL);
			quadCoords.put(xL, yH);
			quadCoords.put(xL_WithWidth, yH);
			quadCoords.put(xL_WithWidth, yL);
			break;
		case LEFT_M:
			quadCoords.put(xL, yL_P_1$4);
			quadCoords.put(xL, yH_M_1$4);
			quadCoords.put(xL_WithWidth, yH_M_1$4);
			quadCoords.put(xL_WithWidth, yL_P_1$4);
			break;
		case LEFT_S:
			quadCoords.put(xL, yL_P_3$8);
			quadCoords.put(xL, yH_M_3$8);
			quadCoords.put(xL_WithWidth, yH_M_3$8);
			quadCoords.put(xL_WithWidth, yL_P_3$8);
			break;
		case LEFT_M_TOP:
			quadCoords.put(xL, yM_NoWidth);
			quadCoords.put(xL, yH);
			quadCoords.put(xL_WithWidth, yH);
			quadCoords.put(xL_WithWidth, yM_NoWidth);
			break;
		case LEFT_M_MIDDLE:
			return getCoordsForQuad(Quads.LEFT_M);
		case LEFT_M_BOTTOM:
			quadCoords.put(xL, yL);
			quadCoords.put(xL, yM_WithWidth);
			quadCoords.put(xL_WithWidth, yM_WithWidth);
			quadCoords.put(xL_WithWidth, yL);
			break;
		case LEFT_S_TOP:
			quadCoords.put(xL, yH_M_3$8);
			quadCoords.put(xL, yH_M_1$8);
			quadCoords.put(xL_WithWidth, yH_M_1$8);
			quadCoords.put(xL_WithWidth, yH_M_3$8);
			break;
		case LEFT_S_MIDDLE:
			return getCoordsForQuad(Quads.LEFT_S);
		case LEFT_S_BOTTOM:
			quadCoords.put(xL, yL_P_1$8);
			quadCoords.put(xL, yL_P_3$8);
			quadCoords.put(xL_WithWidth, yL_P_3$8);
			quadCoords.put(xL_WithWidth, yL_P_1$8);
			break;
		case RIGHT_L:
			quadCoords.put(xH_NoWidth, yL);
			quadCoords.put(xH_NoWidth, yH);
			quadCoords.put(xH, yH);
			quadCoords.put(xH, yL);
			break;
		case RIGHT_M:
			quadCoords.put(xH_NoWidth, yL_P_1$4);
			quadCoords.put(xH_NoWidth, yH_M_1$4);
			quadCoords.put(xH, yH_M_1$4);
			quadCoords.put(xH, yL_P_1$4);
			break;
		case RIGHT_S:
			quadCoords.put(xH_NoWidth, yL_P_3$8);
			quadCoords.put(xH_NoWidth, yH_M_3$8);
			quadCoords.put(xH, yH_M_3$8);
			quadCoords.put(xH, yL_P_3$8);
			break;
		case RIGHT_M_TOP:
			quadCoords.put(xH_NoWidth, yM_NoWidth);
			quadCoords.put(xH_NoWidth, yH);
			quadCoords.put(xH, yH);
			quadCoords.put(xH, yM_NoWidth);
			break;
		case RIGHT_M_MIDDLE:
			return getCoordsForQuad(Quads.RIGHT_M);
		case RIGHT_M_BOTTOM:
			quadCoords.put(xH_NoWidth, yL);
			quadCoords.put(xH_NoWidth, yM_WithWidth);
			quadCoords.put(xH, yM_WithWidth);
			quadCoords.put(xH, yL);
			break;
		case RIGHT_S_TOP:
			quadCoords.put(xH_NoWidth, yH_M_3$8);
			quadCoords.put(xH_NoWidth, yH_M_1$8);
			quadCoords.put(xH, yH_M_1$8);
			quadCoords.put(xH, yH_M_3$8);
			break;
		case RIGHT_S_MIDDLE:
			return getCoordsForQuad(Quads.RIGHT_S);
		case RIGHT_S_BOTTOM:
			quadCoords.put(xH_NoWidth, yL_P_1$8);
			quadCoords.put(xH_NoWidth, yL_P_3$8);
			quadCoords.put(xH, yL_P_3$8);
			quadCoords.put(xH, yL_P_1$8);
			break;
		case CENTER_V_L:
			quadCoords.put(xM_NoWidth, yL);
			quadCoords.put(xM_NoWidth, yH);
			quadCoords.put(xM_WithWidth, yH);
			quadCoords.put(xM_WithWidth, yL);
			break;
		case CENTER_V_M:
			quadCoords.put(xM_NoWidth, yL_P_1$4);
			quadCoords.put(xM_NoWidth, yH_M_1$4);
			quadCoords.put(xM_WithWidth, yH_M_1$4);
			quadCoords.put(xM_WithWidth, yL_P_1$4);
			break;
		case CENTER_V_S:
			quadCoords.put(xM_NoWidth, yL_P_3$8);
			quadCoords.put(xM_NoWidth, yH_M_3$8);
			quadCoords.put(xM_WithWidth, yH_M_3$8);
			quadCoords.put(xM_WithWidth, yL_P_3$8);
			break;
		case CENTER_V_M_TOP:
			quadCoords.put(xM_NoWidth, yM_NoWidth);
			quadCoords.put(xM_NoWidth, yH);
			quadCoords.put(xM_WithWidth, yH);
			quadCoords.put(xM_WithWidth, yM_NoWidth);
			break;
		case CENTER_V_M_MIDDLE:
			return getCoordsForQuad(Quads.CENTER_V_M);
		case CENTER_V_M_BOTTOM:
			quadCoords.put(xM_NoWidth, yL);
			quadCoords.put(xM_NoWidth, yM_WithWidth);
			quadCoords.put(xM_WithWidth, yM_WithWidth);
			quadCoords.put(xM_WithWidth, yL);
			break;
		case CENTER_V_S_TOP:
			quadCoords.put(xM_NoWidth, yH_M_3$8);
			quadCoords.put(xM_NoWidth, yH_M_1$8);
			quadCoords.put(xM_WithWidth, yH_M_1$8);
			quadCoords.put(xM_WithWidth, yH_M_3$8);
			break;
		case CENTER_V_S_MIDDLE:
			return getCoordsForQuad(Quads.CENTER_V_S);
		case CENTER_V_S_BOTTOM:
			quadCoords.put(xM_NoWidth, yL_P_3$8);
			quadCoords.put(xM_NoWidth, yL_P_1$8);
			quadCoords.put(xM_WithWidth, yL_P_1$8);
			quadCoords.put(xM_WithWidth, yL_P_3$8);
			break;
		case CENTER_H_L:
			quadCoords.put(xL, yM_NoWidth);
			quadCoords.put(xL, yM_WithWidth);
			quadCoords.put(xH, yM_WithWidth);
			quadCoords.put(xH, yM_NoWidth);
			break;
		case CENTER_H_M:
			quadCoords.put(xL_P_1$4, yM_NoWidth);
			quadCoords.put(xL_P_1$4, yM_WithWidth);
			quadCoords.put(xH_M_1$4, yM_WithWidth);
			quadCoords.put(xH_M_1$4, yM_NoWidth);
			break;
		case CENTER_H_S:
			quadCoords.put(xL_P_3$8, yM_NoWidth);
			quadCoords.put(xL_P_3$8, yM_WithWidth);
			quadCoords.put(xH_M_3$8, yM_WithWidth);
			quadCoords.put(xH_M_3$8, yM_NoWidth);
			break;
		case CENTER_H_M_LEFT:
			quadCoords.put(xL, yM_NoWidth);
			quadCoords.put(xL, yM_WithWidth);
			quadCoords.put(xM_WithWidth, yM_WithWidth);
			quadCoords.put(xM_WithWidth, yM_NoWidth);
			break;
		case CENTER_H_M_MIDDLE:
			return getCoordsForQuad(Quads.CENTER_H_M);
		case CENTER_H_M_RIGHT:
			quadCoords.put(xH, yM_NoWidth);
			quadCoords.put(xH, yM_WithWidth);
			quadCoords.put(xM_NoWidth, yM_WithWidth);
			quadCoords.put(xM_NoWidth, yM_NoWidth);
			break;
		case CENTER_H_S_LEFT:
			quadCoords.put(xL_P_1$8, yM_NoWidth);
			quadCoords.put(xL_P_1$8, yM_WithWidth);
			quadCoords.put(xL_P_3$8, yM_WithWidth);
			quadCoords.put(xL_P_3$8, yM_NoWidth);
			break;
		case CENTER_H_S_MIDDLE:
			return getCoordsForQuad(Quads.CENTER_H_S);
		case CENTER_H_S_RIGHT:
			quadCoords.put(xH_M_3$8, yM_NoWidth);
			quadCoords.put(xH_M_3$8, yM_WithWidth);
			quadCoords.put(xH_M_1$8, yM_WithWidth);
			quadCoords.put(xH_M_3$8, yM_NoWidth);
			break;
		case SLASH_T2B_L:
			quadCoords.put(xL, yH_M_S_T2B);
			quadCoords.put(xL_P_S_T2B, yH);
			quadCoords.put(xH, yL_P_S_T2B);
			quadCoords.put(xH_M_S_T2B, yL);
			break;
		case SLASH_T2B_M:
			quadCoords.put(xL_P_1$4, yH_M_1$4);
			quadCoords.put(xL_S_T2B_1$4, yH_S_T2B_1$4);
			quadCoords.put(xH_S_T2B_1$4, yL_S_T2B_1$4);
			quadCoords.put(xH_M_1$4, yL_P_1$4);
			break;
		case SLASH_T2B_S:
			quadCoords.put(xL_P_3$8, yH_M_3$8);
			quadCoords.put(xL_S_T2B_3$8, yH_S_T2B_3$8);
			quadCoords.put(xH_S_T2B_3$8, yL_S_T2B_3$8);
			quadCoords.put(xH_M_3$8, yL_P_3$8);
			break;
		case SLASH_T2B_M_TOP:
			quadCoords.put(xL, yH_M_S_T2B);
			quadCoords.put(xL_P_S_T2B, yH);
			quadCoords.put(centerPointX + (slashSize / 2.0), centerPointY + (slashSize / 2.0));
			quadCoords.put(centerPointX - (slashSize / 2.0), centerPointY - (slashSize / 2.0));
			break;
		case SLASH_T2B_M_MIDDLE:
			return getCoordsForQuad(Quads.SLASH_T2B_M);
		case SLASH_T2B_M_BOTTOM:
			quadCoords.put(centerPointX - (slashSize / 2.0), centerPointY - (slashSize / 2.0));
			quadCoords.put(centerPointX + (slashSize / 2.0), centerPointY + (slashSize / 2.0));
			quadCoords.put(xH, yL_P_S_T2B);
			quadCoords.put(xH_M_S_T2B, yL);
			break;
		case SLASH_T2B_S_TOP:
			quadCoords.put(xL_P_1$8, yH_M_1$8);
			quadCoords.put(xL_S_T2B_1$8, yH_S_T2B_1$8);
			quadCoords.put(xL_S_T2B_3$8, yH_S_T2B_3$8);
			quadCoords.put(xL_P_3$8, yH_M_3$8);
			break;
		case SLASH_T2B_S_MIDDLE:
			return getCoordsForQuad(Quads.SLASH_T2B_S);
		case SLASH_T2B_S_BOTTOM:
			quadCoords.put(xH_M_3$8, yL_P_3$8);
			quadCoords.put(xH_S_T2B_3$8, yL_S_T2B_3$8);
			quadCoords.put(xH_S_T2B_1$8, yL_S_T2B_1$8);
			quadCoords.put(xH_M_1$8, yL_P_1$8);
			break;
		case SLASH_B2T_L:
			quadCoords.put(xL_P_S_B2T, yL);
			quadCoords.put(xL, yL_P_S_B2T);
			quadCoords.put(xH_M_S_B2T, yH);
			quadCoords.put(xH, yH_M_S_B2T);
			break;
		case SLASH_B2T_M:
			quadCoords.put(xL_P_1$4, yL_P_1$4);
			quadCoords.put(xL_S_B2T_1$4, yL_S_B2T_1$4);
			quadCoords.put(xH_S_B2T_1$4, yH_S_B2T_1$4);
			quadCoords.put(xH_M_1$4, yH_M_1$4);
			break;
		case SLASH_B2T_S:
			quadCoords.put(xL_P_3$8, yL_P_3$8);
			quadCoords.put(xL_S_B2T_3$8, yL_S_B2T_3$8);
			quadCoords.put(xH_S_B2T_3$8, yH_S_B2T_3$8);
			quadCoords.put(xH_M_3$8, yL_P_3$8);
			break;
		case SLASH_B2T_M_TOP:
			quadCoords.put(centerPointX + (slashSize / 2.0), centerPointY - (slashSize / 2.0));
			quadCoords.put(centerPointX - (slashSize / 2.0), centerPointY + (slashSize / 2.0));
			quadCoords.put(xH_M_S_B2T, yH);
			quadCoords.put(xH, yH_M_S_B2T);
			break;
		case SLASH_B2T_M_MIDDLE:
			return getCoordsForQuad(Quads.SLASH_B2T_M);
		case SLASH_B2T_M_BOTTOM:
			quadCoords.put(xL_P_S_B2T, yL);
			quadCoords.put(xL, yL_P_S_B2T);
			quadCoords.put(centerPointX - (slashSize / 2.0), centerPointY + (slashSize / 2.0));
			quadCoords.put(centerPointX + (slashSize / 2.0), centerPointY - (slashSize / 2.0));
			break;
		case SLASH_B2T_S_TOP:
			quadCoords.put(xH_M_3$8, yH_M_3$8);
			quadCoords.put(xH_S_B2T_3$8, yH_S_B2T_3$8);
			quadCoords.put(xH_S_B2T_1$8, yH_S_B2T_1$8);
			quadCoords.put(xH_M_1$8, yH_M_1$8);
			break;
		case SLASH_B2T_S_MIDDLE:
			return getCoordsForQuad(Quads.SLASH_B2T_S);
		case SLASH_B2T_S_BOTTOM:
			quadCoords.put(xL_P_1$8, yL_P_1$8);
			quadCoords.put(xL_S_B2T_1$8, yL_S_B2T_1$8);
			quadCoords.put(xL_S_B2T_3$8, yL_S_B2T_3$8);
			quadCoords.put(xL_P_3$8, yL_P_3$8);
			break;
		case V_CUSTOM_QUAD_LEFT:
			quadCoords.put(xL, yH_M_S_T2B);
			quadCoords.put(xL_P_S_T2B, yH);
			quadCoords.put(centerPointX + slashSize, slashSize + yL);
			quadCoords.put(centerPointX - slashSize, yL);
			break;
		case V_CUSTOM_QUAD_RIGHT:
			quadCoords.put(centerPointX + slashSize, yL);
			quadCoords.put(centerPointX - slashSize, slashSize + yL);
			quadCoords.put(xH_M_S_T2B, yH);
			quadCoords.put(xH, yH_M_S_T2B);
			break;
		}
		return quadCoords;
	}

	public class Coords {

		public ArrayList<ArrayList<MultiMap<Double, Double>>> coords;

		public Coords(ArrayList<ArrayList<MultiMap<Double, Double>>> lists) {
			coords = lists;
		}

		public ArrayList<MultiMap<Double, Double>> getListPerX(int x) {
			return coords.get(x);
		}

	}

}
