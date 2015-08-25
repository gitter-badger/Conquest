package tk.hes.conquest.gui.button;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.enums.GState;
import tk.hes.conquest.gui.listener.GButtonActionListener;

import java.util.ArrayList;

/**
 * This {@code GAbstractButton} class is the parent class of any GUI button. It includes the necessary
 * boundary checking code.
 *
 * @author James Roberts
 */
public abstract class GAbstractButton extends GComponent {

    public ArrayList<GButtonActionListener> listeners;
    protected Bitmap buttonNormal = null;
    protected Bitmap buttonPressed = null;
    protected GState currentState;

    public GAbstractButton(Vector2f position) {
        super(position);

    }

    public GAbstractButton(Vector2f position, GComponent parent) {
        super(position, parent);
    }

    @Override
    public void init(RenderContext c) {
        currentState = GState.NORMAL;
        listeners = new ArrayList<>();
        if (buttonNormal == null)
            throw new IllegalArgumentException("buttonNormal cannot be null!");

        this.setSize(buttonNormal.getWidth(), buttonNormal.getHeight());
    }

    @Override
    public void render(RenderContext c) {
        if (currentState == GState.NORMAL || currentState == GState.HOVERED)
            c.render(buttonNormal, (int) position.getX(), (int) position.getY());
        else if (currentState == GState.PRESSED)
            c.render(buttonPressed, (int) position.getX(), (int) position.getY());
    }

    @Override
    public void update() {
        Vector2f mouse = Input.getCursorPosition();
        if (mouse.getX() > position.getX() && mouse.getX() < position.getX() + size.getWidth() && mouse.getY() > position.getY() && mouse.getY() < position.getY() + size.getHeight()) {
            currentState = GState.PRESSED;
            if (Input.getMouseClicked(Input.MOUSE_LEFT)) {
                for (GButtonActionListener l : listeners) l.actionPreformed(this);
            }
        } else {
            currentState = GState.NORMAL;
        }
    }

    public void addActionListener(GButtonActionListener listener) {
        this.listeners.add(listener);
    }

    public ArrayList<GButtonActionListener> getActionListeners() {
        return listeners;
    }

    public GState getCurrentState() {
        return currentState;
    }
}
