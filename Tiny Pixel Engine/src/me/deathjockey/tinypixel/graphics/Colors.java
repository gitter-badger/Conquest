package me.deathjockey.tinypixel.graphics;

import java.awt.*;

/**
 * A pixel integer converter that converts color models (r, g, b, a) to/from a packed
 * 32-bit integer. 32-bit <strong>DataBufferInt</strong> are used for pixel data model
 * in the render context.
 *
 * @author Kevin Yang
 */
public class Colors {

	public static final int PURE_RED = Colors.toInt(255, 0, 0, 255);
	public static final int PURE_GREEN = Colors.toInt(0, 255, 0, 255);
	public static final int PURE_BLUE = Colors.toInt(0, 0, 255, 255);
	public static final int PURE_YELLOW = Colors.toInt(255, 255, 0, 255);
	public static final int PURE_CYAN = Colors.toInt(0, 255, 255, 255);
	public static final int PURE_MAGENTA = Colors.toInt(255, 0, 255, 255);
	public static final int PURE_WHITE = Colors.toInt(255, 255, 255, 255);
	public static final int PURE_BLACK = Colors.toInt(0, 0, 0, 255);


	private Colors() { }

	/**
	 * Generates an integer representation for an aRGB color.
	 *
	 * @param r Red
	 * @param g Green
	 * @param b Blue
	 * @param a Alpha
	 * @return Integer representation of the given aRGB color
	 */
	public static int toInt(int r, int g, int b, int a) {
		return  ((a & 0xff) << 24) |
				((r & 0xff) << 16) |
				((g & 0xff) << 8)  |
				(b & 0xff);
	}

	/**
	 * Converts a java.awt.Color object to a color integer.
	 *
	 * @param col Color object
	 * @return Color integer
	 */
	public static int toInt(Color col) {
		return toInt(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha());
	}

	/**
	 * Converts an int array of rgba format into a color integer.
	 *
	 * @param rgba Array of rgba format ranging between 0 - 255
	 * @return Color integer
	 */
	public static int toInt(int[] rgba) {
		return toInt(rgba[0], rgba[1], rgba[2], rgba[3]);
	}

	/**
	 * Converts an integer pixel-color representation to
	 * rgb colors. The array length is 4, representing
	 * r, g, b, a with indexes 0, 1, 2 and 3 respectively.
	 *
	 * @param col Pixel color integer
	 * @return An array of RGB information from 0 to 255,
	 * 		   representing r, g, b, a with indexes 0, 1, 2 and 3
	 * 		   respectively.
	 */
	public static int[] fromInt(int col) {
		return new int[] { (col >> 16) & 0xFF, (col >> 8) & 0XFF, col & 0xFF, (col >> 24) & 0xFF};
	}
}
