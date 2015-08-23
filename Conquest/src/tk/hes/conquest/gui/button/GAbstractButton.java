package tk.hes.conquest.gui.button;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GState;
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
    protected GState currentState;

    public GAbstractButton(Vector2f position, GComponent parent) {
        super(position, parent);
    }

    public GAbstractButton(Vector2f position) {
        super(position);

    }

    @Override
    public void init(RenderContext c) {
        currentState = GState.NORMAL;
        listeners = new ArrayList<>();
    }

    @Override
    public void render(RenderContext c) {
    }

    @Override
    public void update() {
        Vector2f mouse = Input.getCursorPosition();
        if (mouse.getX() > position.getX() && mouse.getX() < position.getX() + size.getWidth() && mouse.getY() > position.getY() && mouse.getY() < position.getY() + size.getHeight()) {
            currentState = Input.getMouseDown(Input.MOUSE_LEFT) ? GState.PRESSED : GState.NORMAL;
            if (Input.getMouseClicked(Input.MOUSE_LEFT))
                for (GButtonActionListener l : listeners) l.actionPreformed(this);

        }
    }

    public void addActionListener(GButtonActionListener listener) {
        this.listeners.add(listener);
    }
}
