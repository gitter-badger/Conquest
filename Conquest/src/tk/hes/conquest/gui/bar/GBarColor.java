package tk.hes.conquest.gui.bar;

import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
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
    public void render(RenderContext c) {
        int aW = (int) (getSize().getWidth() - amountFilled);
        int aH = (int) (getSize().getHeight() - amountFilled);
        c.renderFilledRectangle(
                (int) position.getX() + (isFlipped && isHorizontal() ? aW : 0),
                (int) position.getY() + (isFlipped && !isHorizontal() ? aH : 0),
                (int) ((isHorizontal() ? amountFilled : getSize().getWidth()) + (isFlipped && isHorizontal() ? aW : 0)),
                (int) ((!isHorizontal() ? amountFilled : getSize().getHeight())) + (isFlipped && !isHorizontal() ? aH : 0),
                fillColor);
    }

    @Override
    public void update(Input input) {
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }
}
