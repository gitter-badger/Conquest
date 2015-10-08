package tk.hes.conquest.game.scene;

import me.nibby.pix.Bitmap;
import me.nibby.pix.PixColor;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;

/**
 * A static/animated set of background aesthetics to be displayed either as backdrops
 * in-game as well as during menu screen.
 */
public abstract class Scene {

	private Bitmap backdrop;
	private Vector2f offset = new Vector2f(0, 0);
	private float backdropAlpha = 1.0f;
	private int backdropTint = PixColor.toPixelInt(0, 0, 0, 0);

	/** Invoked prior to the backdrop image render */
	public abstract void preRender(RenderContext c);

	/** Invoked after the backdrop image render */
	public abstract void postRender(RenderContext c);
	public abstract void update(double delta);

	public Scene(Bitmap backdrop) {
		this.backdrop = backdrop;
	}

	public void render(RenderContext c) {
		preRender(c);
		if(backdrop != null) {
			c.renderBitmap(backdrop, (int) offset.getX(), (int) offset.getY(), backdropAlpha, backdropTint);
		}
		postRender(c);
	}

	public Bitmap getBackdrop() {
		return backdrop;
	}

	public void setBackdrop(Bitmap backdrop) {
		this.backdrop = backdrop;
	}

	public Vector2f getOffset() {
		return offset;
	}

	public void setOffset(Vector2f offset) {
		this.offset = offset;
	}

	public float getBackdropAlpha() {
		return backdropAlpha;
	}

	public void setBackdropAlpha(float backdropAlpha) {
		this.backdropAlpha = backdropAlpha;
	}

	public int getBackdropTint() {
		return backdropTint;
	}

	public void setBackdropTint(int backdropTint) {
		this.backdropTint = backdropTint;
	}
}
