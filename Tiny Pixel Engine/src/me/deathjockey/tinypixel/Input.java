package me.deathjockey.tinypixel;

import com.sun.istack.internal.Nullable;

import java.awt.event.*;
import java.util.HashMap;

/**
 * Main input handler class for the game engine. Keyboards keys are mapped into a list
 * which will listen for events.
 *
 * TODO: Implement mouse controls
 *
 * @author Kevin Yang
 */
public class Input implements KeyListener, MouseMotionListener, MouseListener {

	private HashMap<String, InputKey> keys = new HashMap<>();

	private int mouseX, mouseY;
	private int mousePresses, mouseAbsorbs;
	private boolean mousePress, mouseDown;

	public Input(TinyPixelGame game) {


		game.addKeyListener(this);
		game.addMouseListener(this);
		game.addMouseListener(this);
	}

	public void update() {
		for (String kk : keys.keySet()) {
			InputKey k = keys.get(kk);
			k.update();
		}
	}

	public void registerKey(String id, InputKey key) {
		keys.put(id, key);
	}

	@Nullable
	public InputKey getKey(String id) { return keys.get(id); }

	public boolean isKeyPressed(String id) {
		InputKey key = getKey(id);
		if(key != null) return key.isPressed();
		else return false;
	}

	public boolean isKeyDown(String id) {
		InputKey key = getKey(id);
		if(key != null) return key.isHeldDown();
		else return false;
	}

	public void releaseAllKeys() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).setPressed(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();
		for(String kk : keys.keySet()) {
			InputKey k = keys.get(kk);
			if(k.hasAssignedKeyCode(kc)) {
				k.setPressed(true);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int kc = e.getKeyCode();

		for (String kk : keys.keySet()) {
			InputKey k = keys.get(kk);
			if(k.hasAssignedKeyCode(kc)) {
				k.setPressed(false);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}
