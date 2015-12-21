package tk.hes.conquest.gui.dialog;

import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.gui.base.GDialog;
import tk.hes.conquest.gui.base.GLabel;
import tk.hes.conquest.gui.base.enums.GDialogButtonType;
import tk.hes.conquest.gui.base.enums.GDialogType;
import tk.hes.conquest.gui.button.GAbstractButton;
import tk.hes.conquest.gui.button.GDialogButton;
import tk.hes.conquest.gui.listener.GButtonActionListener;

import java.awt.*;

/**
 * A Dialog box which uses different images that have titles.
 *
 * @author James Roberts
 */
public class GTitleDialog extends GDialog implements GButtonActionListener {

    private String title;
    protected GLabel titleLabel;
    protected GDialogButton closeButton;

    private boolean shouldRemove;

    public GTitleDialog(String title, Vector2f position) {
        super(position);
        this.title = title;
        this.size = null;
        this.shouldRemove = false;

        closeButton = new GDialogButton(new Vector2f(0,0), GDialogButtonType.CROSS, this);
        closeButton.addActionListener(this);

        titleLabel = new GLabel(title, new Vector2f(14, 1), this);
    }

    public GTitleDialog(String title, Vector2f pos, Dimension size) {
        this(title, pos, size, GDialogType.INFORMATION);
    }

    public GTitleDialog(String title, Vector2f position, Dimension size, GDialogType type) {
        super(position, size, type);
        this.title = title;
        this.shouldRemove = false;
        bitmapParts = new GDialogPart(type, true);

        closeButton = new GDialogButton(new Vector2f((float) size.getWidth() - 9, 3), GDialogButtonType.CROSS, this);
        closeButton.addActionListener(this);

        titleLabel = new GLabel(title, new Vector2f(14, 1), this);
    }

    @Override
    public void render(RenderContext c) {
        super.render(c);
        closeButton.render(c);
        titleLabel.render(c);
    }

    @Override
    public void update(Input input) {
        super.update(input);
        closeButton.update(input);
        titleLabel.update(input);
    }

    public void setCloseButtonType(GDialogButtonType type) {
        closeButton.setType(type);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean shouldRemove() {
        return shouldRemove;
    }

    @Override
    public void actionPreformed(GAbstractButton button) {
        shouldRemove = true;
    }
}
