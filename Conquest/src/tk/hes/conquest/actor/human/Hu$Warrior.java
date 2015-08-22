package tk.hes.conquest.actor.human;

import tk.hes.conquest.actor.*;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.graphics.Art;

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
		tuple.range = 10;
		tuple.magicDefense = 0;
		tuple.speed = 1.35f;
		tuple.attackMagic = 0;
		tuple.attackRandomMagical = 0;
		tuple.leaveCorpse = true;

		tuple.exp = 0;
		tuple.expMax = 0;
		tuple.level = 1;

		float scale = Art.UNIT_HUMAN_MELEE.getScale();
		int w = Art.UNIT_HUMAN_MELEE.getCellSize().width;
		int h = Art.UNIT_HUMAN_MELEE.getCellSize().height;

		actions.set(ActionType.MOVE, new Action()
				.addFrame(Art.UNIT_HUMAN_MELEE.getSprite(0, 0), 500, 0, 0)
				.addFrame(Art.UNIT_HUMAN_MELEE.getSprite(1, 0), 500, 0, 0));

		actions.set(ActionType.ATTACK1, new Action(this)
				.addFrame(Art.UNIT_HUMAN_MELEE.getSprite(0, 1), 250, 0, 0)
				.addFrame(Art.UNIT_HUMAN_MELEE.getBitmapRegion(1 * w, 1 * h, (1 + 2) * w, (1 + 1) * h), 500, -27, 0, "swing-hit"));

		actions.set(ActionType.DEATH, new Action()
				.addFrame(Art.UNIT_HUMAN_MELEE.getSprite(2, 0), 10000, 0, 0));

		bb.rx = 0;
		bb.ry = 0;
		bb.w = (w - (1 * scale));
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

		for(Actor actor : actors) {
			if(actor.getOwner().equals(this.owner)) continue;
			if(actor.equals(this)) continue;
			if(actor.isDead()) continue;

			actor.hurt(this);
		}
	}

	@Override
	public void onDeath() {

	}
}
