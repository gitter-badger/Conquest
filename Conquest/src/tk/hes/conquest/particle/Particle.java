package tk.hes.conquest.particle;

import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;

/**
 * An instance of a visual effect which serves no particular function.
 * The particle described here is non-textured.
 *
 * @author Kevin Yang
 */
public abstract class Particle {

	protected Vector2f position;
	protected Vector2f velocity;
	protected int size;
	protected int[] tint = { 0, 0, 0, 0 }; //rgb tinting
	protected long spawnTime;
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
			c.fillRegion((int) position.getX(),
					(int) position.getY(),
					(int) position.getX() + size,
					(int) position.getY() + size,
					Colors.toInt(tint));
		}
	}

	public void update() {

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

	public void remove() {
		remove = true;
	}

	public boolean isRemoved() {
		return remove;
	}
}
