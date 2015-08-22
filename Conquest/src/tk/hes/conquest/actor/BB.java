package tk.hes.conquest.actor;

public class BB {

	//BB co-ordinates relative to actor's position
	public float rx, ry, w, h;


	public BB() {
		this(0, 0, 0, 0);
	}

	public BB(int rx, int ry, int w, int h) {
		this.rx = rx;
		this.ry = ry;
		this.w = w * Actor.SPRITE_SCALE;
		this.h = h * Actor.SPRITE_SCALE;
	}

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry;
	}

	public float getWidth() {
		return w;
	}

	public void setWidth(float w) {
		this.w = w;
	}

	public float getHeight() {
		return h;
	}

	public void setHeight(float h) {
		this.h = h;
	}
}
