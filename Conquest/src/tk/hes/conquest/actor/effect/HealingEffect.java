package tk.hes.conquest.actor.effect;

import me.nibby.pix.Bitmap;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.particle.Particle;
import tk.hes.conquest.particle.ParticleManager;

public class HealingEffect extends StatusEffect {

	private int healPerTick;
	private int tickInterval;
	private long tickTime;
	private boolean ticked = false;

	public HealingEffect(Actor actor, int timeMS, int tickInterval, int healPerTick) {
		super(StatusEffectType.HEALING, actor, timeMS);
		this.tickInterval = tickInterval;
		this.healPerTick = healPerTick;
	}

	@Override
	protected void initialize() {
		tickTime = System.currentTimeMillis();
	}

	@Override
	public void render(RenderContext c) {
		super.render(c);
		if(ticked) {
			Vector2f pos = new Vector2f(
					(float) (actor.getPosition().getX() + actor.getBB().getRx() + (Math.random() * actor.getBB().getWidth())),
					(float) (actor.getPosition().getY() + actor.getBB().getRy() + (Math.random() * actor.getBB().getHeight() / 2)));
			HealingParticle particle = new HealingParticle(pos);
			ParticleManager.get().spawn(particle);
			ticked = false;
		}
	}

	@Override
	public void update() {
		super.update();

		if(System.currentTimeMillis() - tickTime > tickInterval) {
			ticked = true;
			if(actor.getAttributes().health >= actor.getAttributes().healthMax)
				ticked = false;

			actor.getAttributes().health += healPerTick;
			if(actor.getAttributes().health > actor.getAttributes().healthMax) {
				actor.getAttributes().health = actor.getAttributes().healthMax;
			}
			tickTime += tickInterval;
		}
	}

	private class HealingParticle extends Particle {

		private Bitmap sprite;
		private int alpha = 200;

		protected HealingParticle(Vector2f pos) {
			super(pos, new Vector2f(0, (float) (Math.random() * (-0.3f) - 0.8f)));
			sprite = Art.PARTICLE_STATUS_EFFECTS.getSprite(0, 0);
		}

		@Override
		public void render(RenderContext c) {
			super.render(c);
			c.renderBitmap(sprite, (int) position.getX(), (int) position.getY(), alpha / 255f);

			if(alpha <= 0)
				remove();
		}

		@Override
		public void update(double delta) {
			alpha -= 5 * delta;
			this.position.set(position.getX(), (int) (position.getY() - 0.05f * delta));
		}

		@Override
		public void onSpawn() {

		}
	}
}
