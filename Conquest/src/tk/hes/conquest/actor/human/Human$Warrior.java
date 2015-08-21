package tk.hes.conquest.actor.human;

import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.actor.AttributeTuple;
import tk.hes.conquest.actor.BB;
import tk.hes.conquest.game.Player;

public class Human$Warrior extends Actor {

	public Human$Warrior(Player owner, long actorUID) {
		super(owner, actorUID);
	}

	@Override
	protected void initializeAttributes(AttributeTuple tuple, BB bb) {
		tuple.health = 25;
		tuple.healthMax = 25;
		tuple.mana = 25;
		tuple.manaMax = 25;
		tuple.attack  = 6;
		tuple.attackRandom = 4;
		tuple.defense = 2;
		tuple.critChance = 0;
		tuple.evasion = 0;
		tuple.expReward = 5;
		tuple.blindRange = 0;
		tuple.range = 10;
		tuple.speed = 0.35f;

		tuple.exp = 0;
		tuple.expMax = 0;
		tuple.level = 1;

		//TODO initialize BB based on sprite
	}
}
