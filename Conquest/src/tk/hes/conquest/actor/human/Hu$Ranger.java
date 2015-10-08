package tk.hes.conquest.actor.human;

import me.nibby.pix.Animation;
import me.nibby.pix.Bitmap;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.actor.*;
import tk.hes.conquest.game.Origin;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.particle.LinearProjectile;
import tk.hes.conquest.particle.ParticleManager;

import java.util.ArrayList;

/**
 *
 * @author Kevin Yang
 */
public class Hu$Ranger extends Actor {

	private long lastFireTime = System.currentTimeMillis();
	private int fireCooldown = 2500; //ms

	public Hu$Ranger(Player owner) {
		super(owner);
	}

	@Override
	protected void initializeAttributes(AttributeTuple tuple, BB bb, ActionSet actions) {
		tuple.health = 65;
		tuple.healthMax = 65;
		tuple.mana = 0;
		tuple.manaMax = 0;
		tuple.attackPhysical = 13;
		tuple.attackRandomPhysical = 4;
		tuple.defense = 2;
		tuple.critChance = 10;
		tuple.evasion = 0;
		tuple.heroExpReward = 4;
		tuple.blindRange = 90;
		tuple.range = 280;
		tuple.magicDefense = 0;
		tuple.speed = 0.15f;
		tuple.attackMagic = 0;
		tuple.attackRandomMagical = 0;
		tuple.leaveCorpse = true;
		tuple.goldReward = 6;
		tuple.chargeReward = 3;

		tuple.exp = 0;
		tuple.expMax = 0;
		tuple.level = 1;

		tuple.name = "Archer";
		tuple.lore = "Pew pew pew";
		tuple.deployDelay = 3500;
		tuple.purchaseCost = 350;
		tuple.hasShadow = true;
		tuple.shadowType = 0;

		int w = Art.UNIT_HUMAN_ARCHER.getCellSize().x;
		int h = Art.UNIT_HUMAN_ARCHER.getCellSize().y;

		actions.set(ActionType.STATIC, new Action()
				.addFrame(Art.UNIT_HUMAN_ARCHER.getSprite(0, 0), 500, 0, 0));

		actions.set(ActionType.MOVE, new Action()
				.addFrame(Art.UNIT_HUMAN_ARCHER.getSprite(0, 0), 500, 0, 0)
				.addFrame(Art.UNIT_HUMAN_ARCHER.getSprite(1, 0), 500, 0, 0));

		actions.set(ActionType.ATTACK1, new Action(this)
				.addFrame(Art.UNIT_HUMAN_ARCHER.getSprite(0, 1), 250, 0, 0)
				.addFrame(Art.UNIT_HUMAN_ARCHER.getSprite(1, 1), 100, 0, 0)
				.addFrame(Art.UNIT_HUMAN_ARCHER.getSprite(1, 1), 400, 0, 0, "release")
				.addFrame(Art.UNIT_HUMAN_ARCHER.getSprite(0, 0), 350, 0, 0, "$RANDOM_DELAY 0 450"));

		actions.set(ActionType.DEATH, new Action()
				.addFrame(Art.UNIT_HUMAN_ARCHER.getSprite(2, 0), 500, 0, 0));

		bb.rx = 1;
		bb.ry = 0;
		bb.w = (w - (1 * Actor.SPRITE_SCALE));
		bb.h = h;
	}

	@Override
	protected void randomizeAttributes(AttributeTuple tuple) {

	}

	@Override
	public void update(double delta) {
		super.update(delta);

		if(System.currentTimeMillis() - lastFireTime > fireCooldown) {
			shouldAttack = true;
		}
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
		if(key.equals("release")) {
			BasicArrow arrow = new BasicArrow(this, new BB(2, 4, 6, 1));
			ParticleManager.get().spawn(arrow);
			lastFireTime = System.currentTimeMillis();
			shouldAttack = false;
		}
	}

	private class BasicArrow extends LinearProjectile {

		public BasicArrow(Actor owner, BB bb) {
			super(owner, bb, null, null, 1.8f);
			Animation anim = new Animation(new Bitmap[] { Art.PARTICLE_PROJECTILE_ARROW.getSprite(0, 0) }, 1000);

			Vector2f pos = new Vector2f(owner.getPosition().getX(), owner.getPosition().getY());
			if(owner.getOwner().getOrigin().equals(Origin.EAST)) {
				anim.setFlipped(false, true);
			}
			this.animation = anim;
			this.position = pos;
			setEnemySearchRange(1);
			setCollideAlly(false);
			setCollideEnemy(true);
		}

		@Override
		public void render(RenderContext c) {
			super.render(c);
		}

		@Override
		public void onSpawn() {
		}

		@Override
		public void onCollideWithAlly(ArrayList<Actor> actors) {

		}

		@Override
		public void onCollideWithEnemy(ArrayList<Actor> actors) {
			for(Actor actor : actors) {
				if(actor.isDead()) continue;
				if(actor.getOwner().equals(owner.getOwner())) continue;
				actor.hurt(owner);
				BasicArrow.this.remove();
			}
		}
	}
}
