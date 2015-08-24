package tk.hes.conquest.gui.bar;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GBar;
import tk.hes.conquest.gui.base.GComponent;

import java.awt.*;

/**
 * A GBar with made for making color bars.
 *
 * @author James Roberts
 */
public class GBarColor extends GBar {

    private int fillColor;

    public GBarColor(Vector2f position, Dimension size, int fillColor) {
        this(position, size, fillColor, null);
    }

    public GBarColor(Vector2f position, Dimension size, int fillColor, GComponent parent) {
        super(position, parent);
        this.size = size;
        this.fillColor = fillColor;
    }

    @Override
    public void init(RenderContext c) {

    }

    @Override
    public void render(RenderContext c) {
        c.fillRegion((int) position.getX(), (int) position.getY(), (int) (position.getX() + amountFilled), (int) (position.getY() + getSize().getHeight()), fillColor);
    }

    @Override
    public void update() {

    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }
}
