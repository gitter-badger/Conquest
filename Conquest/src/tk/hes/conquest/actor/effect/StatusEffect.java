package tk.hes.conquest.actor.effect;

import me.nibby.pix.RenderContext;
import tk.hes.conquest.actor.Actor;

public abstract class StatusEffect {

	private boolean firstUpdate = true;
	private long startTime;
	protected int time;
	protected Actor actor;
	protected StatusEffectType type;

	protected abstract void initialize();

	public StatusEffect(StatusEffectType type, Actor actor, int timeMS) {
		this.actor = actor;
		this.time = timeMS;
		this.type = type;
	}

	public void render(RenderContext c) {

	}

	public void update() {
		if(firstUpdate) {
			startTime = System.currentTimeMillis();
			initialize();
			firstUpdate = false;
		}
	}

	public boolean hasExpired() {
		return System.currentTimeMillis() - startTime >= time;
	}

	public StatusEffectType getType() {
		return type;
	}

	public Actor getActor() {
		return actor;
	}

	public int getTime() {
		return time;
	}

	public long getStartTime() {
		return startTime;
	}
}
