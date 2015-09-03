package tk.hes.conquest.actor.human;

import me.deathjockey.tinypixel.Time;
import me.deathjockey.tinypixel.graphics.Animation;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.actor.*;
import tk.hes.conquest.actor.effect.HealingEffect;
import tk.hes.conquest.game.Origin;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.particle.LinearProjectile;
import tk.hes.conquest.particle.Particle;
import tk.hes.conquest.particle.ParticleManager;

import java.util.ArrayList;

public class Hu$Priest extends Actor {

	private boolean channeling = false;
	private long lastParticleSpawn = System.currentTimeMillis();
	private long lastFireTime;
	private int fireCooldown = 1500;
	private boolean channelSpawn = false;

	public Hu$Priest(Player owner) {
		super(owner);
	}

	@Override
	protected void initializeAttributes(AttributeTuple tuple, BB bb, ActionSet actions) {
		tuple.health = 145;
		tuple.healthMax = 45;
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
		tuple.attackMagic = 16;
		tuple.attackRandomMagical = 6;
		tuple.leaveCorpse = true;
		tuple.goldReward = 7;
		tuple.chargeReward = 5;
		tuple.dominanceReward = 2;
		tuple.knockback = 1;
		tuple.knockbackResistance = 1;

		tuple.exp = 0;
		tuple.expMax = 0;
		tuple.level = 1;

		tuple.name = "Priest";
		tuple.lore = "Heals for days";
		tuple.deployDelay = 4000;
		tuple.hasShadow = true;
		tuple.shadowType = 0;

		int w = Art.UNIT_HUMAN_PRIEST.getCellSize().width;
		int h = Art.UNIT_HUMAN_PRIEST.getCellSize().height;

		actions.set(ActionType.STATIC, new Action()
				.addFrame(Art.UNIT_HUMAN_PRIEST.getSprite(0, 0), 500, 0, 0));

		actions.set(ActionType.MOVE, new Action()
				.addFrame(Art.UNIT_HUMAN_PRIEST.getSprite(0, 0), 500, 0, 0)
				.addFrame(Art.UNIT_HUMAN_PRIEST.getSprite(1, 0), 500, 0, 0));

		actions.set(ActionType.ATTACK1, new Action(this)
				.addFrame(Art.UNIT_HUMAN_PRIEST.getSprite(0, 1), 10, 0, 0, "")
				.addFrame(Art.UNIT_HUMAN_PRIEST.getSprite(0, 1), 1500, 0, 0, "channel")
				.addFrame(Art.UNIT_HUMAN_PRIEST.getSprite(1, 1), 500, 0, 0, "shoot")
				.addFrame(Art.UNIT_HUMAN_PRIEST.getSprite(1, 1), 500, 0, 0, "end-shoot"));

		actions.set(ActionType.DEATH, new Action()
				.addFrame(Art.UNIT_HUMAN_PRIEST.getSprite(2, 0), 10000, 0, 0));

		bb.rx = 1;
		bb.ry = 0;
		bb.w = (w - (1 * Actor.SPRITE_SCALE));
		bb.h = h;
	}

	@Override
	public void render(RenderContext c) {
		super.render(c);
	}

	@Override
	public void update() {
		super.update();

		if(channeling) {
			if(!channelSpawn) {
				double theta = 0d;
				for(int i = 0; i < 36; i++) {
					theta = theta + Math.toRadians(10);
					float px, py;
					px = (float) (getPosition().getX() + getBB().getWidth() / 2 + 8 * Actor.SPRITE_SCALE * Math.cos(theta));
					py = (float) (getPosition().getY() + getBB().getHeight() / 2 + 8 * Actor.SPRITE_SCALE * Math.sin(theta));
					ChannelParticle particle = new ChannelParticle(new Vector2f(px, py));
					ParticleManager.get().spawn(particle);
				}
				channelSpawn = true;
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
		channeling = key.equals("channel");
		if(key.equals("shoot")) {
			BasicBolt bolt = new BasicBolt(this);
			ParticleManager.get().spawn(bolt);
		}

		if(key.equals("end-shoot")) {
			shouldAttack = false;
			channelSpawn = false;
			lastFireTime = System.currentTimeMillis();
		}
	}

	private class BasicBolt extends LinearProjectile {

		private Bitmap sprite;
		private ArrayList<Actor> actorsHealed = new ArrayList<>();

		public BasicBolt(Actor owner) {
			super(owner, new BB(3, 2, 3, 5), null, null, 0.6f);
			sprite = Art.PARTICLE_PROJECTILE_BOLT.getSprite(0, 1);
			Animation anim = new Animation(new Bitmap[] { sprite }, 1000);

			Vector2f pos = new Vector2f(owner.getPosition().getX(), owner.getPosition().getY());
			if(owner.getOwner().getOrigin().equals(Origin.EAST)) {
				anim.setFlipped(false, true);
			}
			this.animation = anim;
			this.position = pos;
			setCollideAlly(true);
			setCollideEnemy(true);
		}

		@Override
		public void update() {
			super.update();

			if(System.currentTimeMillis() - lastParticleSpawn > 350) {
				Vector2f pPos = new Vector2f(position.getX(), position.getY());
				float vh = velocity.getX() / 3;
				BoltTrailParticle particle = new BoltTrailParticle(pPos, sprite, vh);
				ParticleManager.get().spawn(particle);
				lastParticleSpawn = System.currentTimeMillis();
			}
		}

		@Override
		public void onSpawn() {
			Hu$Priest.this.channeling = false;
		}

		@Override
		public void onCollideWithAlly(ArrayList<Actor> actors) {
			for(Actor actor : actors) {
				if(actor.isDead()) continue;

				if(actor.getOwner().equals(owner.getOwner())) {
					if(actorsHealed.contains(actor))
						continue;

					actor.addStatusEffect(new HealingEffect(actor, 3000, 500, (int) (Math.random() * 6 + 7)));
					actorsHealed.add(actor);
				}
			}
		}

		@Override
		public void onCollideWithEnemy(ArrayList<Actor> actors) {
			for(Actor actor : actors) {
				//TODO extra effects for undead units
				actor.hurt(owner);
			}

			int particles = (int) (Math.random() * 15 + 85);
			for (int i = 0; i < particles; i++) {
				Vector2f pos = new Vector2f(position.getX() + 2 * Actor.SPRITE_SCALE + (float) (Math.random() * 3),
						position.getY() + this.getBB().getRy() + this.getBB().getHeight() / 2
								+ (float) (Math.random() * (sprite.getHeight()) / 2f));
				BoltCollisionParticle particle = new BoltCollisionParticle(pos, this.velocity, sprite);
				ParticleManager.get().spawn(particle);
			}
			BasicBolt.this.remove();
		}
	}

	private class BoltTrailParticle extends Particle {

		private Bitmap projectile;
		private int alpha = 125;

		protected BoltTrailParticle(Vector2f pos, Bitmap projectile, float vh) {
			super(pos, new Vector2f(vh, 0));
			this.projectile = projectile;
		}

		@Override
		public void render(RenderContext c) {
			super.render(c);
			c.render(projectile, (int) position.getX(), (int) position.getY(), (float) alpha / 255f);
		}

		@Override
		public void update() {
			super.update();
			alpha -= 3 * Time.delta;
			if(alpha <= 0)
				remove();
		}

		@Override
		public void onSpawn() {
		}
	}

	private class BoltCollisionParticle extends Particle {

		private int r, g, b;
		private int alpha = 125;

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
			alpha -= 3 * Time.delta;
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
			a -= 2 * Time.delta;
			setColor(r, g, b, a);

			Vector2f destination = Hu$Priest.this.getPosition();
			BB bb = Hu$Priest.this.getBB();
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
