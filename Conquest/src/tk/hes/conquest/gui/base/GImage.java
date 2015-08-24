package tk.hes.conquest.gui.base;

import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;

/**
 * @author James
 */

public class GImage extends GComponent {

    private Bitmap image;

    public GImage(Bitmap image, Vector2f position) {
        this(image, position, null);

    }

    public GImage(Bitmap image, Vector2f position, GComponent parent) {
        super(position, parent);
        this.image = image;
        this.setSize(image.getWidth(), image.getHeight());
    }

    @Override
    public void init(RenderContext c) {
    }

    @Override
    public void render(RenderContext c) {
        c.render(image, (int) position.getX(), (int) position.getY());
    }

    @Override
    public void update() {
    }

    public Bitmap getImage() {
        return image;
    }
}
