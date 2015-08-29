package tk.hes.conquest.game.scene;

import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;

/**
 * A static/animated set of background aesthetics to be displayed either as backdrops
 * in-game as well as during menu screen.
 */
public abstract class Scene {

	private Bitmap backdrop;
	private Vector2f offset = new Vector2f(0, 0);
	private float backdropAlpha = 1.0f;
	private int backdropTint = Colors.toInt(0, 0, 0, 0);

	/** Invoked prior to the backdrop image render */
	public abstract void preRender(RenderContext c);

	/** Invoked after the backdrop image render */
	public abstract void postRender(RenderContext c);
	public abstract void update();

	public Scene(Bitmap backdrop) {
		this.backdrop = backdrop;
	}

	public void render(RenderContext c) {
		preRender(c);
		if(backdrop != null) {
			c.render(backdrop, (int) offset.getX(), (int) offset.getY(), backdropAlpha, backdropTint);
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
