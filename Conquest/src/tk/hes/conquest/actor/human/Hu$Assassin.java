package tk.hes.conquest.actor.human;

import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.actor.*;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.graphics.Art;

import java.awt.*;
import java.util.ArrayList;

public class Hu$Assassin extends Actor {

	public Hu$Assassin(Player owner) {
		super(owner);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(RenderContext c) {
		super.render(c);
	}

	@Override
	protected void initializeAttributes(AttributeTuple tuple, BB bb, ActionSet actions) {
		tuple.health = 65;
		tuple.healthMax = 65;
		tuple.mana = 0;
		tuple.manaMax = 0;
		tuple.attackPhysical = 7;
		tuple.attackRandomPhysical = 22;
		tuple.defense = 3;
		tuple.critChance = 10;
		tuple.evasion = 25;
		tuple.heroExpReward = 6;
		tuple.blindRange = 0;
		tuple.range = 5;
		tuple.magicDefense = 1;
		tuple.speed = 0.42f;
		tuple.attackMagic = 0;
		tuple.attackRandomMagical = 0;
		tuple.leaveCorpse = true;
		tuple.goldReward = 6;
		tuple.chargeReward = 5;
		tuple.knockback = 3;
		tuple.knockbackResistance = 1;

		tuple.exp = 0;
		tuple.expMax = 0;
		tuple.level = 1;

		tuple.name = "Assassin";
		tuple.lore = "He is very quick :D";
		tuple.deployDelay = 4000;
		tuple.hasShadow = true;
		tuple.shadowType = 0;

		int w = Art.UNIT_HUMAN_ARCHER.getCellSize().width;
		int h = Art.UNIT_HUMAN_ARCHER.getCellSize().height;

		actions.set(ActionType.STATIC, new Action()
				.addFrame(Art.UNIT_HUMAN_ASSASSIN.getSprite(0, 0), 500, 0, 0));

		actions.set(ActionType.MOVE, new Action()
				.addFrame(Art.UNIT_HUMAN_ASSASSIN.getSprite(0, 0), 500, 0, 0)
				.addFrame(Art.UNIT_HUMAN_ASSASSIN.getSprite(1, 0), 500, 0, 0));

		actions.set(ActionType.ATTACK1, new Action(this)
				.addFrame(Art.UNIT_HUMAN_ASSASSIN.getSprite(0, 1), 250, 0, 0)
				.addFrame(Art.UNIT_HUMAN_ASSASSIN.getBitmapRegion(w, 1 * h, w * 2 + 2 * Actor.SPRITE_SCALE, 2 * h), 250, 0, -2 * Actor.SPRITE_SCALE, "hit")
				.addFrame(Art.UNIT_HUMAN_ASSASSIN.getSprite(0, 1), 350, 0, 0, "$RANDOM_DELAY 20 100"));

		actions.set(ActionType.DEATH, new Action()
				.addFrame(Art.UNIT_HUMAN_ASSASSIN.getSprite(2, 0), 10000, 0, 0));

		bb.rx = 1;
		bb.ry = 0;
		bb.w = (w - (1 * Actor.SPRITE_SCALE));
		bb.h = h;
	}

	@Override
	protected void randomizeAttributes(AttributeTuple tuple) {

	}

	@Override
	public void onAttack() {
		currentAction = ActionType.ATTACK1;
	}


	@Override
	public void onDeath() {

	}

	@Override
	public void keyFrameReached(String key) {
		if(key.equals("hit")) {
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
}
