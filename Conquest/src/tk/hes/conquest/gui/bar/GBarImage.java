package tk.hes.conquest.gui.bar;

import me.nibby.pix.Bitmap;
import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
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
    public void render(RenderContext c) {
        int actualWidth = (int) (getSize().getWidth() - amountFilled);
        int actualHeight = (int) (getSize().getHeight() - amountFilled);

        int trimX = (isFlipped && isHorizontal() ? actualWidth : 0);
        int trimY = (isFlipped && !isHorizontal() ? actualHeight : 0);
        int trimWidth = (int) (isHorizontal() ? amountFilled : getSize().getWidth()) + (isFlipped && isHorizontal() ? actualWidth : 0);
        int trimHeight = (int) (!isHorizontal() ? amountFilled : getSize().getHeight()) + (isFlipped && !isHorizontal() ? actualHeight : 0);
        if(trimWidth > trimX && trimHeight > trimY) {
            c.renderBitmap(image.getImage().getRegionAsBitmap(trimX, trimY, trimWidth, trimHeight),
                    (int) position.getX(), (int) position.getY());
        }
    }

    @Override
    public void update(Input input) {

    }
}
