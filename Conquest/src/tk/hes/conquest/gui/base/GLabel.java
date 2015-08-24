package tk.hes.conquest.gui.base;

import me.deathjockey.tinypixel.graphics.BitFont;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.font.Font;

/**
 * A basic GUI label with color customization and capable of acting as a child
 *
 * @author James Roberts
 */
public class GLabel extends GComponent {

    private String fontType;
    private String text;
    private int color;

    public GLabel(String text) {
        this(text, new Vector2f(0, 0), Colors.PURE_WHITE);
    }

    public GLabel(String text, Vector2f position) {
        this(text, position, Colors.PURE_WHITE, null);
    }

    public GLabel(String text, Vector2f position, int color) {
        this(text, position, color, null);
    }

    public GLabel(String text, Vector2f position, GComponent parent) {
        this(text, position, Colors.PURE_WHITE, parent);
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
        fontType = Font.NORMAL;
    }

    @Override
    public void init(RenderContext c) {
        this.size.width = BitFont.widthOf(text, c.getFont(fontType));
    }

    @Override
    public void render(RenderContext c) {
        c.getFont(fontType).render(text, (int) position.getX(), (int) position.getY(), color);
    }

    @Override
    public void update() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
