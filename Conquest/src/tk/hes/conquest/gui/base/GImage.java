package tk.hes.conquest.gui.base;

import me.nibby.pix.Bitmap;
import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;

/**
 * A GImage class for easily drawing images.
 *
 * @author James Roberts
 */

public class GImage extends GComponent {

    private Bitmap image;
	private boolean visible = true;

	public GImage(Bitmap image, Vector2f position) {
        this(image, position, null);

    }

    public GImage(Bitmap image, GComponent parent) {
        this(image, new Vector2f(0, 0), parent);
    }

    public GImage(Bitmap image, Vector2f position, GComponent parent) {
        super(position, parent);
        this.image = image;
        this.setSize(image.getWidth(), image.getHeight());
    }

    @Override
    public void render(RenderContext c) {
		if(!visible) return;

        c.renderBitmap(image, (int) position.getX(), (int) position.getY());
    }

    @Override
    public void update(Input input) {
    }

    public Bitmap getImage() {
        return image;
    }

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
