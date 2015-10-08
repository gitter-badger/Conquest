package tk.hes.conquest.particle;

import me.nibby.pix.PixColor;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.ConquestGameDesktopLauncher;

/**
 * An instance of a visual effect which serves no particular function.
 * The particle described here is non-textured.
 *
 * @author Kevin Yang
 */
public abstract class Particle {

	protected Vector2f position;
	protected Vector2f velocity;
	protected float size;
	protected int[] tint = { 0, 0, 0, 0 }; //rgb tinting
	protected boolean remove = false;
	public abstract void onSpawn();

	protected Particle(Vector2f pos, Vector2f v) {
		this(pos, v, 0);
	}

	public Particle(Vector2f pos, Vector2f v, int size) {
		this.position = pos;
		this.velocity = v;
		this.size = size;
	}

	public void render(RenderContext c) {
		if(size > 0f) {
			c.renderFilledRectangle((int) position.getX(),
					(int) position.getY(),
					(int) size, (int) size,
					PixColor.toPixelInt(tint));
		}
	}

	public void update(double delta) {
		this.position.setX(position.getX() + velocity.getX() * (float) delta);
		this.position.setY(position.getY() + velocity.getY() * (float) delta);

		int x = (int) this.position.getX();
		if(x < -16 - size || x > ConquestGameDesktopLauncher.INIT_WIDTH / ConquestGameDesktopLauncher.SCALE) {
			remove();
		}
	}

	public void setColor(float r, float g, float b, float a) {
		setColor((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
	}

	public void setColor(int r, int g, int b, int a) {
		tint[0] = r;
		tint[1] = g;
		tint[2] = b;
		tint[3] = a;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public void remove() {
		remove = true;
	}

	public boolean isRemoved() {
		return remove;
	}
}
