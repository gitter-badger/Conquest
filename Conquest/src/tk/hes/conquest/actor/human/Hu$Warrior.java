package tk.hes.conquest.actor.human;

import tk.hes.conquest.actor.*;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.graphics.Art;

import java.awt.*;
import java.util.ArrayList;

public class Hu$Warrior extends Actor  {

	public Hu$Warrior(Player owner) {
		super(owner);
	}

	@Override
	protected void initializeAttributes(AttributeTuple tuple, BB bb, ActionSet actions) {
		tuple.health = 25;
		tuple.healthMax = 25;
		tuple.mana = 25;
		tuple.manaMax = 25;
		tuple.attackPhysical = 6;
		tuple.attackRandomPhysical = 4;
		tuple.defense = 2;
		tuple.critChance = 0;
		tuple.evasion = 0;
		tuple.expReward = 5;
		tuple.blindRange = 0;
		tuple.range = 16;
		tuple.magicDefense = 0;
		tuple.speed = 0.2f;
		tuple.attackMagic = 0;
		tuple.attackRandomMagical = 0;
		tuple.leaveCorpse = true;

		tuple.exp = 0;
		tuple.expMax = 0;
		tuple.level = 1;

		int w = Art.UNIT_HUMAN_MELEE.getCellSize().width;
		int h = Art.UNIT_HUMAN_MELEE.getCellSize().height;

		actions.set(ActionType.MOVE, new Action()
				.addFrame(Art.UNIT_HUMAN_MELEE.getSprite(0, 0), 500, 0, 0)
				.addFrame(Art.UNIT_HUMAN_MELEE.getSprite(1, 0), 500, 0, 0));

		actions.set(ActionType.ATTACK1, new Action(this)
				.addFrame(Art.UNIT_HUMAN_MELEE.getSprite(0, 1), 250, 0, 0)
				.addFrame(Art.UNIT_HUMAN_MELEE.getBitmapRegion(1 * w, 1 * h, (1 + 2) * w, (1 + 1) * h), 100, -9 * Actor.SPRITE_SCALE, 0)
				.addFrame(Art.UNIT_HUMAN_MELEE.getBitmapRegion(1 * w, 1 * h, (1 + 2) * w, (1 + 1) * h), 400, -9 * Actor.SPRITE_SCALE, 0, "swing-hit"));

		actions.set(ActionType.DEATH, new Action()
				.addFrame(Art.UNIT_HUMAN_MELEE.getSprite(2, 0), 10000, 0, 0));

		bb.rx = 1;
		bb.ry = 0;
		bb.w = (w - (1 * Actor.SPRITE_SCALE));
		bb.h = h;
	}

	@Override
	public void keyFrameReached(String key) {
		if(key.equals("swing-hit")) {
			onAttack();
		}
	}

	@Override
	public void preAttack() {
		currentAction = ActionType.ATTACK1;
	}

	@Override
	public void onAttack() {
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
	public void onDeath() {
	}

}
