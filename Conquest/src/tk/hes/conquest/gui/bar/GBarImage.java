package tk.hes.conquest.gui.bar;

import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;

/**
 * @author James
 */
public class GBarImage extends GBar {

    private GImage image;

    public GBarImage(Bitmap image, Vector2f position, GComponent parent) {
        super(position, parent);
        this.image = new GImage(image, new Vector2f(0, 0), this);
        this.setSize(this.image.getSize());
    }

    @Override
    public void init(RenderContext c) {
        super.init(c);

    }

    @Override
    public void render(RenderContext c) {
        int aW = (int) (getSize().getWidth() - amountFilled);
        int aH = (int) (getSize().getHeight() - amountFilled);
        c.render(image.getImage(),
                (isFlipped && isHorizontal() ? aW : 0),
                (isFlipped && !isHorizontal() ? aH : 0),
                (int) (isHorizontal() ? amountFilled : getSize().getWidth()) + (isFlipped && isHorizontal() ? aW : 0),
                (int) (!isHorizontal() ? amountFilled : getSize().getHeight()) + (isFlipped && !isHorizontal() ? aH : 0),
                (int) position.getX(), (int) position.getY());

    }

    @Override
    public void update() {

    }
}
