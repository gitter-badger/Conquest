package tk.hes.conquest.gui.base.dialog;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GLabel;
import tk.hes.conquest.gui.button.GAbstractButton;
import tk.hes.conquest.gui.button.GDialogButton;
import tk.hes.conquest.gui.listener.GButtonActionListener;

import java.awt.*;

/**
 * @author James Roberts
 */
public class GTitleDialog extends GDialog implements GButtonActionListener {

    private GLabel titleLabel;
    private String title;
    private GDialogButton closeButton;
    private boolean isVisible;

    public GTitleDialog(String title, Vector2f pos, Dimension size) {
        this(title, pos, size, GDialogType.INFORMATION);
    }

    public GTitleDialog(String title, Vector2f position, Dimension size, GDialogType type) {
        super(position, size, type);
        this.title = title;
        this.isVisible = true;
    }

    @Override
    public void init(RenderContext c) {
        bitmapParts = new GDialogPart(type, true);
        closeButton = new GDialogButton(new Vector2f((int) size.getWidth() - 9, 3), GDialogButtonType.CROSS, this);
        closeButton.init(c);
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
    public void update() {
        super.update();
        closeButton.update();
        titleLabel.update();
    }

    public void setCloseButtonType(GDialogButtonType type) {
        closeButton.setType(type);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void actionPreformed(GAbstractButton button) {
        System.out.println("CLOSE BUTTON");
    }
}
