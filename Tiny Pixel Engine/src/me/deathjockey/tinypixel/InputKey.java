package me.deathjockey.tinypixel;

/**
 * A representation of a keyboard key adapted from CatacombSnatch by Mojang.
 *
 * @author Kevin Yang
 */
public class InputKey {

	private boolean wasDown = false;
	private boolean isDown = false;
	private boolean nextState = false;

	private int[] keyCodes;
	public InputKey(int... keyCodes) {
		this.keyCodes = keyCodes;
	}

	public void update() {
		wasDown = isDown;
		isDown = nextState;
	}

	public void setPressed(boolean press) {
		nextState = press;
	}

	public boolean isPressed() {
		return !wasDown && isDown;
	}

	public boolean hasAssignedKeyCode(int code) {
		for(int kc : keyCodes) {
			if(kc == code) return true;
		}
		return false;
	}

	public boolean isHeldDown() {
		return isDown && nextState;
	}
}
