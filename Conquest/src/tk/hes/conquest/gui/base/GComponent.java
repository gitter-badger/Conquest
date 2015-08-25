package tk.hes.conquest.gui.base;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;

import java.awt.*;

/**
 * {@code GComponent} is the base GUI class. Every GUI element extends this class.
 *
 * @author James
 */
public abstract class GComponent {

    protected GComponent parent = null;
    protected Vector2f position;
    protected Dimension size;
    private boolean isChild = false;

    public GComponent(Vector2f position) {
        this(position, null);
    }

    public GComponent(GComponent parent) {
        this(new Vector2f(0, 0), parent);
    }

    public GComponent(Vector2f position, GComponent parent) {
        if (parent != null) {
            this.parent = parent;
            this.isChild = true;
        }
        this.position = calculatePosition(position);
        this.size = new Dimension(0, 0);
    }

    public abstract void init(RenderContext c);

    public abstract void render(RenderContext c);

    public abstract void update();

    public GComponent getParent() {
        return parent;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = calculatePosition(position);
    }

    public void setPosition(float x, float y) {
        this.setPosition(new Vector2f(x, y));
    }

    private Vector2f calculatePosition(Vector2f position) {
        if (!isChild)
            return position;
        else
            return new Vector2f(position.getX() + parent.getPosition().getX(), position.getY() + parent.getPosition().getY());
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public void setSize(int width, int height) {
        this.size.setSize(width, height);
    }

    public boolean isChild() {
        return isChild;
    }
}
