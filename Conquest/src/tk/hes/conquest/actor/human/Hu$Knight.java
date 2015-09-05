package tk.hes.conquest.actor.human;

import tk.hes.conquest.actor.*;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.graphics.Art;

import java.awt.*;
import java.util.ArrayList;

/*
 * TODO: Fix knock-back effect breaking attack speed
 *
 */
public class Hu$Knight extends Actor {

	public Hu$Knight(Player owner) {
		super(owner);
	}

	@Override
	protected void initializeAttributes(AttributeTuple tuple, BB bb, ActionSet actions) {
		tuple.health = 255;
		tuple.healthMax = 255;
		tuple.mana = 0;
		tuple.manaMax = 0;
		tuple.attackPhysical = 32;
		tuple.attackRandomPhysical = 7;
		tuple.defense = 9;
		tuple.critChance = 0;
		tuple.evasion = 0;
		tuple.heroExpReward = 5;
		tuple.blindRange = 0;
		tuple.range = 15;
		tuple.magicDefense = 0;
		tuple.speed = 0.16f;
		tuple.attackMagic = 0;
		tuple.attackRandomMagical = 0;
		tuple.leaveCorpse = true;
		tuple.goldReward = 15;
		tuple.chargeReward = 7;
		tuple.dominanceReward = 3;
		tuple.knockback = 6;
		tuple.knockbackResistance = 6;
		tuple.parry = 25;

		tuple.exp = 0;
		tuple.expMax = 0;
		tuple.level = 1;

		tuple.name = "Knight";
		tuple.lore = "Some description needed";
		tuple.deployDelay = 6500;
		tuple.hasShadow = true;
		tuple.shadowType = 0;

		int w = Art.UNIT_HUMAN_KNIGHT.getCellSize().width;
		int h = Art.UNIT_HUMAN_KNIGHT.getCellSize().height;

		actions.set(ActionType.STATIC, new Action()
				.addFrame(Art.UNIT_HUMAN_KNIGHT.getSprite(0, 0), 500, 0, 0));

		actions.set(ActionType.MOVE, new Action()
				.addFrame(Art.UNIT_HUMAN_KNIGHT.getSprite(0, 0), 500, 0, 0)
				.addFrame(Art.UNIT_HUMAN_KNIGHT.getSprite(1, 0), 500, 0, 0));

		actions.set(ActionType.ATTACK1, new Action(this)
				.addFrame(Art.UNIT_HUMAN_KNIGHT.getSprite(0, 1), 150, 0, 0)
				.addFrame(Art.UNIT_HUMAN_KNIGHT.getBitmapRegion(w, h, 3 * w, 2 * h), 100, -10 * Actor.SPRITE_SCALE, 0)
				.addFrame(Art.UNIT_HUMAN_KNIGHT.getBitmapRegion(w, h, 3 * w, 2 * h), 1, -10 * Actor.SPRITE_SCALE, 0, "swing-hit")
				.addFrame(Art.UNIT_HUMAN_KNIGHT.getSprite(0, 0), 950, 0, 0));

		actions.set(ActionType.DEFEND, new Action()
				.addFrame(Art.UNIT_HUMAN_KNIGHT.getSprite(0, 2), 500, 0, 0)
				.addFrame(Art.UNIT_HUMAN_KNIGHT.getSprite(1, 2), 500, 0, 0));

		actions.set(ActionType.DEATH, new Action()
				.addFrame(Art.UNIT_HUMAN_KNIGHT.getSprite(2, 0), 2000, 0, 0));

		bb.rx = 1;
		bb.ry = 0;
		bb.w = (w - (1 * Actor.SPRITE_SCALE));
		bb.h = h;
	}

	@Override
	protected void randomizeAttributes(AttributeTuple tuple) {

	}

	@Override
	public void keyFrameReached(String key) {

		if(key.equals("swing-hit")) {
			attack();
		}
	}

	private void attack() {
		ArrayList<Actor> actors = board.getActorsInLane(currentLane);
		Rectangle sword = null;
		switch(owner.getOrigin()) {
			case WEST:
				sword = new Rectangle((int) (getPosition().getX() + bb.getRx() + bb.getWidth()),
						(int) (getPosition().getY() + bb.getRy()), attributes.range, (int) bb.getHeight());
				break;
			case EAST:
				sword = new Rectangle((int) (getPosition().getX() + bb.getRx() - attributes.range),
						(int) (getPosition().getY() + bb.getRy()), attributes.range, (int) bb.getHeight());
				break;
		}
		for(Actor actor : actors) {
			if(actor.getOwner().equals(this.owner)) continue;
			if(actor.equals(this)) continue;
			if(actor.isDead()) continue;
			Rectangle hitbox = new Rectangle((int) actor.getPosition().getX() + (int) actor.getBB().getRx(),
					(int) actor.getPosition().getY() + (int) actor.getBB().getRy(),
					(int) actor.getBB().getWidth(), (int) actor.getBB().getHeight());

			if(sword.intersects(hitbox)) {
				actor.hurt(this);
			}
		}
	}

	@Override
	public void onAttack() {
		if(!currentAction.equals(ActionType.DEFEND))
			currentAction = ActionType.ATTACK1;
	}

	@Override
	public void onDeath() {
	}
}
