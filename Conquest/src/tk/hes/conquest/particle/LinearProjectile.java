package tk.hes.conquest.particle;

import me.deathjockey.tinypixel.graphics.Animation;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.actor.BB;
import tk.hes.conquest.game.Origin;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Kevin Yang
 */
public abstract class LinearProjectile extends Projectile {

	public LinearProjectile(Actor owner, BB bb, Animation anim, Vector2f pos, float vh) {
		super(owner, bb, anim, pos, new Vector2f((owner.getOwner().getOrigin().equals(Origin.WEST) ? Math.abs(vh) : -Math.abs(vh)), 0));
	}

	@Override
	protected void updateVelocity() {
	}

	@Override
	protected void updateCollision() {
		ArrayList<Actor> actors = owner.getGameBoard().getActorsInLane( owner.getCurrentLane());
		Rectangle bounds = new Rectangle((int) position.getX() + (int) bb.getRx(),
				(int) position.getY() + (int) bb.getRy(),
				(int) bb.getWidth(), (int) bb.getHeight());
		ArrayList<Actor> collisions = new ArrayList<>();
		for(Actor actor : actors) {
			if(actor.equals(owner)) continue;
			Rectangle actorBounds = new Rectangle((int) (actor.getPosition().getX() + actor.getBB().getRx()),
					(int) (actor.getPosition().getY() + actor.getBB().getRy()),
					(int) actor.getBB().getWidth(), (int) actor.getBB().getHeight());
			if(actorBounds.intersects(bounds)) {
				collisions.add(actor);
			}
		}

		collideWith(collisions);
	}
}
