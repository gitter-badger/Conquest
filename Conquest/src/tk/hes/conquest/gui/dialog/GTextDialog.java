package tk.hes.conquest.gui.dialog;

import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.gui.base.GLabel;
import tk.hes.conquest.gui.base.enums.GDialogType;

import java.awt.*;

/**
 * Extension of GTitleDialog, except with the option of addng a message to the body.
 *
 * @author James Roberts
 */
public class GTextDialog extends GTitleDialog {

    public GLabel textLabel;
    private String message;

    public GTextDialog(String title, Vector2f pos, Dimension size) {
        this(title, pos, size, GDialogType.INFORMATION);
    }

    public GTextDialog(String title, Vector2f position, Dimension size, GDialogType type) {
        super(title, position, size, type);
        this.message = "";
        textLabel = new GLabel(message, new Vector2f(5, 16), this);
    }

    @Override
    public void render(RenderContext c) {
        super.render(c);
        textLabel.render(c);
    }

    @Override
    public void update(Input input) {
        super.update(input);
        textLabel.update(input);
    }

    public void setMessage(String text) {
        this.message = text;
    }
}
