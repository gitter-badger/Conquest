package tk.hes.conquest.gui.base;

import me.nibby.pix.BitmapFont;
import me.nibby.pix.Input;
import me.nibby.pix.PixColor;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;

import java.awt.*;

/**
 * A basic GUI label with color customization and capable of acting as a child
 *
 * @author James Roberts
 */
public class GLabel extends GComponent {

    private String text;
    private int color;

    public GLabel(String text) {
        this(text, new Vector2f(0, 0), PixColor.WHITE);
    }

    public GLabel(String text, Vector2f position) {
        this(text, position, PixColor.WHITE, null);
    }

    public GLabel(String text, Vector2f position, int color) {
        this(text, position, color, null);
    }

    public GLabel(String text, Vector2f position, GComponent parent) {
        this(text, position, PixColor.WHITE, parent);
    }

    public GLabel(String text, Vector2f position, int color, GComponent parent) {
        super(position, parent);
        if (text == null)
            throw new IllegalArgumentException("String can not equal null!");
        create(text, color);
    }

    private void create(String text, int color) {
        this.text = text;
        this.color = color;
        this.size.width = BitmapFont.getDefaultFont().widthOf(text);
    }

    @Override
    public void render(RenderContext c) {
        c.renderText(text, (int) position.getX(), (int) position.getY(), color);
    }

    @Override
    public void update(Input input) {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
