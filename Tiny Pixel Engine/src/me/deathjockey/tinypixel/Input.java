package me.deathjockey.tinypixel;

import me.deathjockey.tinypixel.util.Vector2f;

import java.awt.event.*;

/**
 * An Input class which an instance is required to pick up on keyboard
 * and mouse events, although all data should be accessed statically.
 *
 * @author James Roberts
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener {

    public static final int MOUSE_LEFT = 1;
    public static final int MOUSE_MIDDLE = 2;
    public static final int MOUSE_RIGHT = 3;

    private static final int NUM_MOUSE = 256;
    private static final int NUM_KEYCODES = 65565;

    private static boolean[] currentKeys = new boolean[NUM_KEYCODES];
    private static boolean[] lastKeys = new boolean[NUM_KEYCODES];

    private static Vector2f cursorPosition = new Vector2f(0, 0);
    private static boolean[] currentMouse = new boolean[NUM_MOUSE];
    private static boolean[] clickedMouse = new boolean[NUM_MOUSE];

    public static boolean getMouseClicked(int mouseButton) {
        return clickedMouse[mouseButton];
    }

    public static boolean getMouseUp(int mouseButton) {
        return !currentMouse[mouseButton];
    }

    public static boolean getMouseDown(int mouseButton) {
        return currentMouse[mouseButton];
    }

    public static boolean getKeyUp(int keyCode) {
        return !getKeyDown(keyCode);
    }

    public static boolean getKeyDown(int keyCode) {
        return currentKeys[keyCode];
    }

    public static boolean getKeyPressed(int keyCode) {
        return !lastKeys[keyCode] && getKeyDown(keyCode);
    }

    public static Vector2f getCursorPosition() {
        return new Vector2f(cursorPosition.getX() / 2, cursorPosition.getY() / 2);
    }

    public void update() {
        for (int i = 0; i < NUM_KEYCODES; i++) lastKeys[i] = getKeyDown(i);
        for (int i = 0; i < NUM_MOUSE; i++) clickedMouse[i] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentKeys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentKeys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        lastKeys[e.getKeyCode()] = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentMouse[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentMouse[e.getButton()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clickedMouse[e.getButton()] = true;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        cursorPosition.set(e.getX(), e.getY());
    }

    // Below are unused
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

}
