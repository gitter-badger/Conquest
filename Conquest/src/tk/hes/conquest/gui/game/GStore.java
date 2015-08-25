package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.dialog.GTitleDialog;


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
        System.out.println(size);
    }

    @Override
    public void init(RenderContext c) {
        super.init(c);
        this.closeButton.setPosition((int) getSize().getWidth() - 66, 3);
        System.out.println(titleLabel.getPosition().toString());
        this.titleLabel.setPosition(27, 2);
    }

    @Override
    public void render(RenderContext c) {
        backgroundImage.render(c);
        super.render(c);
    }

    @Override
    public void update() {
        super.update();
    }
}
