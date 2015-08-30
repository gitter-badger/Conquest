package tk.hes.conquest.gui.bar;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;

import java.awt.*;

/**
 * @author James
 */
public class GBarColor extends GBar {

    private int fillColor;

    public GBarColor(Vector2f position, Dimension size, int fillColor, GComponent parent) {
        super(position, parent);
        this.size = size;
        this.fillColor = fillColor;
    }

    @Override
    public void init(RenderContext c) {
        super.init(c);
    }

    @Override
    public void render(RenderContext c) {
        int aW = (int) (getSize().getWidth() - amountFilled);
        int aH = (int) (getSize().getHeight() - amountFilled);
        c.fillRegion(
                (int) position.getX() + (isFlipped && isHorizontal() ? aW : 0),
                (int) position.getY() + (isFlipped && !isHorizontal() ? aH : 0),
                (int) (position.getX() + (isHorizontal() ? amountFilled : getSize().getWidth()) + (isFlipped && isHorizontal() ? aW : 0)),
                (int) (position.getY() + (!isHorizontal() ? amountFilled : getSize().getHeight())) + (isFlipped && !isHorizontal() ? aH : 0),
                fillColor);
    }

    @Override
    public void update() {
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }
}
