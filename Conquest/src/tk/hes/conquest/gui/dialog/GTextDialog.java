package tk.hes.conquest.gui.dialog;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GLabel;
import tk.hes.conquest.gui.base.enums.GDialogType;

import java.awt.*;

/**
 * @author James
 */
public class GTextDialog extends GTitleDialog {

    public GLabel textLabel;
    private String text;

    public GTextDialog(String title, Vector2f pos, Dimension size) {
        this(title, pos, size, GDialogType.INFORMATION);
    }

    public GTextDialog(String title, Vector2f position, Dimension size, GDialogType type) {
        super(title, position, size, type);
    }

    @Override
    public void init(RenderContext c) {
        super.init(c);
        textLabel = new GLabel(text, new Vector2f(5, 16), this);
    }

    @Override
    public void render(RenderContext c) {
        super.render(c);
        textLabel.render(c);
    }

    @Override
    public void update() {
        super.update();
        textLabel.update();
    }

    public void setMessage(String text) {
        this.text = text;
    }

}
