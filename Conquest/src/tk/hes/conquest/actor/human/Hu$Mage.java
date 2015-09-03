package tk.hes.conquest.actor.human;

import me.deathjockey.tinypixel.Time;
import me.deathjockey.tinypixel.graphics.Animation;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.actor.*;
import tk.hes.conquest.game.Origin;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.particle.LinearProjectile;
import tk.hes.conquest.particle.Particle;
import tk.hes.conquest.particle.ParticleManager;

import java.util.ArrayList;

/**
 *
 * @author Kevin Yang
 */
public class Hu$Mage extends Actor {

	private long lastFireTime = System.currentTimeMillis();
	private int fireCooldown = 500; //ms
	private long lastParticleSpawn = System.currentTimeMillis();
	private boolean channeling = false;

	public Hu$Mage(Player owner) {
		super(owner);
	}

	@Override
	protected void initializeAttributes(AttributeTuple tuple, BB bb, ActionSet actions) {
		tuple.health = 50;
		tuple.healthMax = 50;
		tuple.mana = 30;
		tuple.manaMax = 30;
		tuple.attackPhysical = 0;
		tuple.attackRandomPhysical = 0;
		tuple.defense = 1;
		tuple.critChance = 0;
		tuple.evasion = 0;
		tuple.heroExpReward = 5;
		tuple.blindRange = 70;
		tuple.range = 220;
		tuple.magicDefense = 0;
		tuple.speed = 0.15f;
		tuple.attackMagic = 26;
		tuple.attackRandomMagical = 13;
		tuple.leaveCorpse = true;
		tuple.goldReward = 7;
		tuple.chargeReward = 5;
		tuple.dominanceReward = 2;
		tuple.knockback = 1;
		tuple.knockbackResistance = 1;

		tuple.exp = 0;
		tuple.expMax = 0;
		tuple.level = 1;

		tuple.name = "Mage";
		tuple.lore = "Magic spells allahu akbar";
		tuple.deployDelay = 4000;
		tuple.hasShadow = true;
		tuple.shadowType = 0;

		int w = Art.UNIT_HUMAN_WARRIOR.getCellSize().width;
		int h = Art.UNIT_HUMAN_WARRIOR.getCellSize().height;

		actions.set(ActionType.STATIC, new Action()
				.addFrame(Art.UNIT_HUMAN_MAGE.getSprite(0, 0), 500, 0, 0));

		actions.set(ActionType.MOVE, new Action()
				.addFrame(Art.UNIT_HUMAN_MAGE.getSprite(0, 0), 500, 0, 0)
				.addFrame(Art.UNIT_HUMAN_MAGE.getSprite(1, 0), 500, 0, 0));

		actions.set(ActionType.ATTACK1, new Action(this)
				.addFrame(Art.UNIT_HUMAN_MAGE.getSprite(0, 1), 10, 0, 0, "")
				.addFrame(Art.UNIT_HUMAN_MAGE.getSprite(0, 1), 1500, 0, 0, "channel")
				.addFrame(Art.UNIT_HUMAN_MAGE.getSprite(1, 1), 500, 0, 0, "shoot")
				.addFrame(Art.UNIT_HUMAN_MAGE.getSprite(1, 1), 500, 0, 0, "end-shoot"));

		actions.set(ActionType.DEATH, new Action()
				.addFrame(Art.UNIT_HUMAN_MAGE.getSprite(2, 0), 10000, 0, 0));

		bb.rx = 1;
		bb.ry = 0;
		bb.w = (w - (1 * Actor.SPRITE_SCALE));
		bb.h = h;
	}

	@Override
	public void update() {
		super.update();

		if(channeling) {
			if(System.currentTimeMillis() - lastParticleSpawn > 40) {
				int px, py;
				px = (int) (getPosition().getX() - 4 * Actor.SPRITE_SCALE + (int) (Math.random() * (getBB().getWidth() + 8 * Actor.SPRITE_SCALE)));
				py = (int) (getPosition().getY() - 4 * Actor.SPRITE_SCALE + (int) (Math.random() * (getBB().getHeight() + 8 * Actor.SPRITE_SCALE)));
				ChannelParticle particle = new ChannelParticle(new Vector2f(px, py));
				ParticleManager.get().spawn(particle);
				lastParticleSpawn = System.currentTimeMillis();
			}
		}

		if(System.currentTimeMillis() - lastFireTime > fireCooldown) {
			shouldAttack = true;
		}

		if(dead || currentAction.equals(ActionType.MOVE))
			channeling = false;
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
		if(key.equals("shoot")) {
			BasicBolt bolt = new BasicBolt(this);
			ParticleManager.get().spawn(bolt);
		}

		if(key.equals("end-shoot")) {
			shouldAttack = false;
			lastFireTime = System.currentTimeMillis();
		}

		channeling = key.equals("channel") && currentAction.equals(ActionType.ATTACK1);
	}

	private class BasicBolt extends LinearProjectile {

		private Bitmap sprite;

		public BasicBolt(Actor owner) {
			super(owner, new BB(2, 2, 6, 5), null, null, 1.8f);
			sprite = Art.PARTICLE_PROJECTILE_BOLT.getSprite(0, 0);
			Animation anim = new Animation(new Bitmap[] { sprite }, 1000);

			Vector2f pos = new Vector2f(owner.getPosition().getX(), owner.getPosition().getY());
			if(owner.getOwner().getOrigin().equals(Origin.EAST)) {
				anim.setFlipped(false, true);
			}
			this.animation = anim;
			this.position = pos;
			setEnemySearchRange(25);
			setCollideEnemy(true);
			setCollideAlly(false);
		}

		@Override
		public void update() {
			super.update();

			if(System.currentTimeMillis() - lastParticleSpawn > 50) {
				Vector2f pPos = new Vector2f(position.getX() + sprite.getWidth() / 2,
						position.getY() + sprite.getHeight() / 2);
				float vh = velocity.getX() / (3 + (int) (Math.random() * 2));
				BoltTrailParticle particle = new BoltTrailParticle(pPos, sprite, vh);
				ParticleManager.get().spawn(particle);
				lastParticleSpawn = System.currentTimeMillis();
			}
		}

		@Override
		public void onSpawn() {
			Hu$Mage.this.channeling = false;
		}

		@Override
		public void onCollideWithAlly(ArrayList<Actor> actors) {

		}

		@Override
		public void onCollideWithEnemy(ArrayList<Actor> actors) {
			for(Actor actor : actors) {
				if(actor.isDead()) continue;
				actor.hurt(owner);

				BasicBolt.this.remove();
			}

			int particles = (int) (Math.random() * 15 + 85);
			for(int i = 0; i < particles; i++) {
				Vector2f pos = new Vector2f(position.getX() + 2 * Actor.SPRITE_SCALE + (float) (Math.random() * 3),
						position.getY() + this.getBB().getRy() + this.getBB().getHeight() / 2
								+ (float) (Math.random() * (sprite.getHeight()) / 2f));
				BoltCollisionParticle particle = new BoltCollisionParticle(pos, this.velocity, sprite);
				ParticleManager.get().spawn(particle);
			}
		}
	}

	private class BoltCollisionParticle extends Particle {

		private int r, g, b;
		private float alpha = 125f;
		private long spawnTime;
		private int lifeTime = 1000;

		protected BoltCollisionParticle(Vector2f pos, Vector2f projectileVelocity, Bitmap projectile) {
			super(pos, new Vector2f(0, 0));
			setSize((int) (Math.random() * 2 + 1));
			int[] rgba = Colors.fromInt(projectile.getPixels()[(int) (Math.random() * projectile.getPixels().length)]);
			r = rgba[0];
			g = rgba[1];
			b = rgba[2];
			if(r == 0 && g == 0 && b == 0 || rgba[3] == 0)
				remove();
			setColor(r, g, b, alpha);

			float vx = projectileVelocity.getX();
			float v = (float) (Math.random() * 0.45f + 0.65f);
			velocity = new Vector2f((vx > 0) ? v : -v, (float) (Math.random() * 1f - 0.5f));
		}

		@Override
		public void update() {
			super.update();
			alpha -= 255f / (float) lifeTime;
			setColor(r / 255f, g / 255f, b / 255f, alpha / 255f);
			if(System.currentTimeMillis() - spawnTime > lifeTime || alpha <= 0)
				remove();
		}

		@Override
		public void onSpawn() {
			spawnTime = System.currentTimeMillis();
		}
	}

	private class BoltTrailParticle extends Particle {

		private int r, g, b;
		private int alpha = 255;

		protected BoltTrailParticle(Vector2f pos, Bitmap projectile, float vh) {
			super(pos, new Vector2f(vh, (float) (Math.random() * 0.3f - 0.15f)));
			int[] pixel = Colors.fromInt(projectile.getPixels()[(int) (Math.random() * projectile.getPixels().length)]);
			r = pixel[0];
			g = pixel[1];
			b = pixel[2];
			if(r == 0 && g == 0 && b == 0 || pixel[3] == 0)
				remove();

			setColor(r, g, b, 255);
			setSize((int) (Math.random() * 1 + 1));
		}

		@Override
		public void update() {
			super.update();
			alpha -= 6 * Time.delta;
			setColor(r, g, b, alpha);
			if(alpha <= 0)
				remove();
		}

		@Override
		public void onSpawn() {
		}
	}

	private class ChannelParticle extends Particle {

		private int r, g, b, a;
		private final int tr = 242, tg = 250, tb = 0, ta = 255;
		private long spawnTime;
		private final int lifeTime = 1500;

		protected ChannelParticle(Vector2f pos) {
			super(pos, new Vector2f(0f, 0f));
			r = 99; g = 155; b = 255; a = 255;
			setColor(r, g, b, a);
			setSize(2);
		}

		@Override
		public void update() {
			super.update();
			if(r < tr) r += 6f * Time.delta;
			if(g < tg) g += 6f * Time.delta;
			if(b > tb) b -= 6f * Time.delta;
			a -= 6 * Time.delta;
			setColor(r, g, b, a);

			Vector2f destination = Hu$Mage.this.getPosition();
			BB bb = Hu$Mage.this.getBB();
			float x = position.getX(), y = position.getY();
			float dx = destination.getX() + bb.getRx() + bb.getWidth() / 2,
					dy = destination.getY() + bb.getRy() + bb.getHeight() / 4;
			if(x < dx) velocity.setX(0.2f);
			if(y < dy) velocity.setY(0.2f);
			if(x > dx) velocity.setX(-0.2f);
			if(y > dy) velocity.setY(-0.2f);
			if(a <= 0)
				remove();
		}

		@Override
		public void onSpawn() {
			spawnTime = System.currentTimeMillis();
		}
	}
}
