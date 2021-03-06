package tk.hes.conquest.gui.game;

import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.base.enums.GDialogButtonType;
import tk.hes.conquest.gui.button.GDialogButton;
import tk.hes.conquest.gui.dialog.GTitleDialog;

import java.awt.*;

/**
 * The UI element responsible for the Store page.
 *
 * @author James Roberts
 */
public class GStore extends GTitleDialog {

    private GImage backgroundImage;

    public GStore(Vector2f position) {
        super("Fighter Store", position);
        this.backgroundImage = new GImage(Art.UI_STORE, this);
        this.size = backgroundImage.getSize();
        this.closeButton.setPosition((int) getSize().getWidth() - 66, 3);
        this.titleLabel.setPosition(27, 2);
    }

    @Override
    public void render(RenderContext c) {
        backgroundImage.render(c);
        super.render(c);
    }

    @Override
    public void update(Input input) {
        super.update(input);
    }
}
