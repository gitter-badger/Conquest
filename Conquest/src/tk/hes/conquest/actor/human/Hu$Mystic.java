package tk.hes.conquest.actor.human;

import me.nibby.pix.Animation;
import me.nibby.pix.Bitmap;
import me.nibby.pix.PixColor;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.actor.*;
import tk.hes.conquest.game.Origin;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.particle.LinearProjectile;
import tk.hes.conquest.particle.Particle;
import tk.hes.conquest.particle.ParticleManager;

import java.util.ArrayList;

public class Hu$Mystic extends Actor {

	private long lastFireTime = System.currentTimeMillis();
	private int fireCooldown = 1000; //ms
	private long lastParticleSpawn = System.currentTimeMillis();
	private boolean channeling = false;
	private boolean attacking = false;

	public Hu$Mystic(Player owner) {
		super(owner);
	}

	@Override
	protected void initializeAttributes(AttributeTuple tuple, BB bb, ActionSet actions) {

		tuple.health = 90;
		tuple.healthMax = 90;
		tuple.mana = 50;
		tuple.manaMax = 50;
		tuple.attackPhysical = 0;
		tuple.attackRandomPhysical = 0;
		tuple.defense = 4;
		tuple.critChance = 0;
		tuple.evasion = 0;
		tuple.heroExpReward = 5;
		tuple.blindRange = 70;
		tuple.range = 270;
		tuple.magicDefense = 0;
		tuple.speed = 0.17f;
		tuple.attackMagic = 22;
		tuple.attackRandomMagical = 16;
		tuple.leaveCorpse = true;
		tuple.goldReward = 13;
		tuple.chargeReward = 5;
		tuple.dominanceReward = 2;
		tuple.knockback = 1;
		tuple.knockbackResistance = 1;

		tuple.exp = 0;
		tuple.expMax = 0;
		tuple.level = 1;

		tuple.name = "Mystic";
		tuple.lore = "She's mysterious";
		tuple.deployDelay = 6000;
		tuple.hasShadow = true;
		tuple.shadowType = 0;


		int w = Art.UNIT_HUMAN_MYSTIC.getCellSize().x;
		int h = Art.UNIT_HUMAN_MYSTIC.getCellSize().y;

		actions.set(ActionType.STATIC, new Action()
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(0, 0), 500, 0, 0));

		actions.set(ActionType.MOVE, new Action()
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(0, 0), 500, 0, 0)
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(1, 0), 500, 0, 0));

		actions.set(ActionType.ATTACK1, new Action(this)
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(0, 1), 10, 0, 0, "")
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(0, 1), 750, 0, 0, "channel")
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getRegionAsBitmap(2 * w, h, 4 * w, 2 * h), 500, -8 * Actor.SPRITE_SCALE, 0, "shoot")
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(0, 1), 500, 0, 0, "end-attack"));

		actions.set(ActionType.ATTACK2, new Action(this)
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(0, 1), 10, 0, 0, "")
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(0, 1), 250, 0, 0, "channel-2")
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(1, 1), 250, 0, 0)
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(0, 1), 250, 0, 0)
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(1, 1), 250, 0, 0)
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(0, 1), 250, 0, 0)
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(1, 1), 250, 0, 0)
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(1, 1), 500, 0, 0, "cast-teleport")
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(1, 1), 500, 0, 0, "end-attack"));

		actions.set(ActionType.DEATH, new Action()
				.addFrame(Art.UNIT_HUMAN_MYSTIC.getSprite(2, 0), 10000, 0, 0));

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
	public void update(double delta) {
		super.update(delta);

		if(channeling) {
			if(System.currentTimeMillis() - lastParticleSpawn > 40) {
				int px, py;
				px = (int) (getPosition().getX() - 4 * Actor.SPRITE_SCALE + (int) (Math.random() * (getBB().getWidth() + 8 * Actor.SPRITE_SCALE)));
				py = (int) (getPosition().getY() + getBB().getRy() + getBB().getHeight() + (int) (Math.random() * 4 + 1));
				ChannelShootParticle particle = new ChannelShootParticle(new Vector2f(px, py));
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
		attacking = currentAction.equals(ActionType.ATTACK1) ||
				currentAction.equals(ActionType.ATTACK2);
		if(attacking) return;
		currentAction = ActionType.ATTACK1;
		attacking = true;
	}

	@Override
	public void onDeath() {

	}

	@Override
	public void keyFrameReached(String key) {
		channeling = key.equals("channel") && currentAction.equals(ActionType.ATTACK1);

		if(key.equals("shoot")) {
			BasicBolt bolt = new BasicBolt(this);
			ParticleManager.get().spawn(bolt);
		}

		if(key.equals("end-attack")) {
			shouldAttack = false;
			lastFireTime = System.currentTimeMillis();
		}
	}


	private class BasicBolt extends LinearProjectile {

		private Bitmap sprite;

		public BasicBolt(Actor owner) {
			super(owner, new BB(2, 2, 6, 5), null, null, 1.8f);
			sprite = Art.PARTICLE_PROJECTILE_BOLT.getSprite(0, 2);
			Animation anim = new Animation(new Bitmap[] {
					Art.PARTICLE_PROJECTILE_BOLT.getSprite(0, 2),
					Art.PARTICLE_PROJECTILE_BOLT.getSprite(1, 2),
			}, 250);
			anim.setLooping(true);

			Vector2f pos = new Vector2f(owner.getPosition().getX(), owner.getPosition().getY());
			if(owner.getOwner().getOrigin().equals(Origin.EAST)) {
				anim.setFlipped(false, true);
			}
			this.animation = anim;
			this.position = pos;
			setEnemySearchRange(15);
			setCollideEnemy(true);
			setCollideAlly(false);
		}

		@Override
		public void update(double delta) {
			super.update(delta);

			if(System.currentTimeMillis() - lastParticleSpawn > 10) {
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
			Hu$Mystic.this.channeling = false;
		}

		@Override
		public void onCollideWithAlly(ArrayList<Actor> actors) {

		}

		@Override
		public void onCollideWithEnemy(ArrayList<Actor> actors) {
			for(Actor actor : actors) {
				actor.hurt(owner);
			}

			int particles = (int) (Math.random() * 15 + 85);
			for(int i = 0; i < particles; i++) {
				Vector2f pos = new Vector2f(position.getX() + 2 * Actor.SPRITE_SCALE + (float) (Math.random() * 3),
						position.getY() + this.getBB().getRy() + this.getBB().getHeight() / 2
								+ (float) (Math.random() * (sprite.getHeight()) / 2f));
				BoltCollisionParticle particle = new BoltCollisionParticle(pos, this.velocity, sprite);
				ParticleManager.get().spawn(particle);
			}
			BasicBolt.this.remove();
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
			int[] rgba = PixColor.fromPixelInt(projectile.getPixels()[(int) (Math.random() * projectile.getPixels().length)]);
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
		public void update(double delta) {
			super.update(delta);
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
			int[] pixel = PixColor.fromPixelInt(projectile.getPixels()[(int) (Math.random() * projectile.getPixels().length)]);
			r = pixel[0];
			g = pixel[1];
			b = pixel[2];
			if(r == 0 && g == 0 && b == 0 || pixel[3] == 0)
				remove();

			setColor(r, g, b, 255);
			setSize(2);
		}

		@Override
		public void update(double delta) {
			super.update(delta);
			alpha -= 6 * delta;
			setColor(r, g, b, alpha);
			if(alpha <= 0)
				remove();
		}

		@Override
		public void onSpawn() {
		}
	}

	private class ChannelShootParticle extends Particle {

		private int r, g, b, a;
		private final int tr = 255, tg = 235, tb = 59, ta = 255;
		private long spawnTime;
		private final int lifeTime = 1500;

		protected ChannelShootParticle(Vector2f pos) {
			super(pos, new Vector2f(0f, 0f));
			r = 225; g = 115; b = 0; a = 255;
			setColor(r, g, b, a);
			setSize(3);
		}

		@Override
		public void update(double delta) {
			super.update(delta);
			if(r < tr) r += 6f * delta;
			if(g < tg) g += 6f * delta;
			if(b > tb) b -= 6f * delta;
			a -= 6 * delta;
			setColor(r, g, b, a);

			Vector2f destination = Hu$Mystic.this.getPosition();
			float y = position.getY();
			float dy = destination.getY() - (int) (Math.random() * 5 * Actor.SPRITE_SCALE + 12 * Actor.SPRITE_SCALE);
			if(y < dy) velocity.setY(0.2f);
			if(y > dy) velocity.setY(-0.2f);
			if(a <= 0)
				remove();
		}

		@Override
		public void onSpawn() {
			spawnTime = System.currentTimeMillis();
		}
	}

	private class ChannelTeleportParticle extends Particle {

		protected ChannelTeleportParticle(Vector2f pos, Vector2f v) {
			super(pos, v);
		}

		@Override
		public void onSpawn() {

		}
	}
}
