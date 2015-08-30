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

	private int enemySearchRange = 1, allySearchRange = 1;
	private boolean collideAlly = false, collideEnemy = true;
	private boolean collideEnemyCorpse = false, collideAllyCorpse = false;

	public abstract void onCollideWithAlly(ArrayList<Actor> actors);
	public abstract void onCollideWithEnemy(ArrayList<Actor> actors);

	public LinearProjectile(Actor owner, BB bb, Animation anim, Vector2f pos, float vh) {
		super(owner, bb, anim, pos, new Vector2f((owner.getOwner().getOrigin().equals(Origin.WEST) ? Math.abs(vh) : -Math.abs(vh)), 0));
	}

	public int getEnemySearchRange() {
		return enemySearchRange;
	}

	public void setEnemySearchRange(int enemySearchRange) {
		this.enemySearchRange = enemySearchRange;
	}

	public int getAllySearchRange() {
		return allySearchRange;
	}

	public void setAllySearchRange(int allySearchRange) {
		this.allySearchRange = allySearchRange;
	}

	public boolean isCollidingAlly() {
		return collideAlly;
	}

	public boolean isCollidingEnemy() {
		return collideEnemy;
	}

	public void setCollideEnemyCorpse(boolean collideEnemyCorpse) {
		this.collideEnemyCorpse = collideEnemyCorpse;
	}

	public void setCollideAllyCorpse(boolean collideAllyCorpse) {
		this.collideAllyCorpse = collideAllyCorpse;
	}

	public boolean isCollidingEnemyCorpse() {
		return collideEnemyCorpse;
	}

	public boolean isCollidingAllyCorpse() {
		return collideAllyCorpse;
	}

	public void setCollideAlly(boolean collideAlly) {
		this.collideAlly = collideAlly;
	}

	public void setCollideEnemy(boolean collideEnemy) {
		this.collideEnemy = collideEnemy;
	}

	@Override
	protected void updateVelocity() {
	}

	@Override
	protected void updateCollision() {
		float vh = getVelocity().getX();

		int bx = (int) getPosition().getX();
		int by = (int) getPosition().getY();
		int bw = (int) getBB().getWidth();
		int bh = (int) getBB().getHeight();
		Rectangle bounds = new Rectangle(bx, by, bw, bh);
		ArrayList<Actor> actors;
		ArrayList<Actor> collisions = new ArrayList<>();

		if(isCollidingEnemy()) {
			actors = owner.getGameBoard().getNearbyActorsInLane(owner.getCurrentLane(), this.getPosition(), enemySearchRange, vh < 0f, vh > 0f);
			for (Actor actor : actors) {
				if (actor.equals(owner) || actor.getOwner().equals(owner.getOwner())) continue;
				if (!collideEnemyCorpse && actor.isDead()) continue;
				Rectangle actorBounds = actor.getBounds();
				if (actorBounds.intersects(bounds)) {
					collisions.addAll(actors);
					if (!collisions.contains(actor)) {
						collisions.add(actor);
					}
					onCollideWithEnemy(collisions);
				}
			}
		}

		if(isCollidingAlly()) {
			collisions.clear();
			actors = owner.getGameBoard().getNearbyActorsInLane(owner.getCurrentLane(), getPosition(), allySearchRange, vh < 0f, vh > 0f);
			for (Actor actor : actors) {
				if (actor.equals(owner) && !actor.getOwner().equals(owner.getOwner())) continue;
				if (!collideAllyCorpse && actor.isDead()) continue;
				Rectangle actorBounds = actor.getBounds();
				if (actorBounds.intersects(bounds)) {
					collisions.addAll(actors);
					if (!collisions.contains(actor)) {
						collisions.add(actor);
					}
					onCollideWithAlly(collisions);
				}
			}
		}
	}
}
