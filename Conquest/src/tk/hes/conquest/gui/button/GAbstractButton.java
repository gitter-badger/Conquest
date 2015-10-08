package tk.hes.conquest.gui.button;

import me.nibby.pix.Bitmap;
import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Tuple2i;
import me.nibby.pix.util.Vector2f;
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

    public ArrayList<GButtonActionListener> listeners = new ArrayList<>();
    protected Bitmap buttonNormal = null;
    protected Bitmap buttonPressed = null;
    protected GState currentState;

    public GAbstractButton(Vector2f position) {
        super(position);

        currentState = GState.NORMAL;
        listeners = new ArrayList<>();
        if (buttonNormal == null)
            throw new IllegalArgumentException("buttonNormal cannot be null!");

        this.setSize(buttonNormal.getWidth(), buttonNormal.getHeight());
    }

    public GAbstractButton(Vector2f position, GComponent parent) {
        super(position, parent);
    }

    @Override
    public void render(RenderContext c) {
        if (currentState == GState.NORMAL || currentState == GState.HOVERED)
            c.renderBitmap(buttonNormal, (int) position.getX(), (int) position.getY());
        else if (currentState == GState.PRESSED)
            c.renderBitmap(buttonPressed, (int) position.getX(), (int) position.getY());
    }

    @Override
    public void update(Input input) {
        Tuple2i mouse = input.getCursorPosition();
        if (mouse.x > position.getX() && mouse.x < position.getX() + size.getWidth()
                && mouse.y > position.getY() && mouse.y < position.getY() + size.getHeight()) {
            currentState = GState.PRESSED;
            if (input.isMouseClicked(Input.MOUSE_LEFT)) {
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
