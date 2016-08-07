package pong.main;

public class Util {

	public static int b2bit(boolean flag) {
		return (byte) (flag ? 1 : 0);
	}

	public static float i2float(int i) {
		return (float) i;
	}

	public static int f2int(float f) {
		return (int) f;
	}

	public static double i2double(int i) {
		return (double) i;
	}

	public static double getAngle(double distance) {
		if (distance == 0)
			return 0;
		else
			return (double) /* Max distance from center. */(80.0 / 119.0) * distance;
	}
}
