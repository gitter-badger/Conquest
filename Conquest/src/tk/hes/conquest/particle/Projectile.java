package tk.hes.conquest.particle;

import me.deathjockey.tinypixel.graphics.Animation;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.actor.BB;

import java.util.ArrayList;

/**
 *
 * @author Kevin Yang
 */
public abstract class Projectile extends Particle {

	protected BB bb;
	protected Actor owner;
	protected Animation animation;
	protected float alpha;

	protected abstract void updateVelocity();
	protected abstract void updateCollision();

	public Projectile(Actor owner, BB bb, Animation anim, Vector2f pos, Vector2f velo) {
		super(pos, velo);
		this.animation = anim;
		this.bb = bb;
		this.owner = owner;
	}

	public void render(RenderContext c) {
		c.render(animation, (int) position.getX(), (int) position.getY());
	}

	public void update() {
		updateVelocity();
		position.set(position.getX() + velocity.getX(), position.getY() + velocity.getY());
		updateCollision();
	}

	public BB getBB() {
		return bb;
	}

	public Actor getOwner() {
		return owner;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getVelocity() {
		return velocity;
	}
}
