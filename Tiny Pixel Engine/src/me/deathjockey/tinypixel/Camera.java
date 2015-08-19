package me.deathjockey.tinypixel;

/**
 * Game view offset manager.
 *
 * @author Kevin Yang
 */
public class Camera {

	/** X and Y displacement of certain game entities */
	private int offsetX, offsetY;

	public Camera() {
		this(0, 0);
	}

	public Camera(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public void setOffset(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
}
