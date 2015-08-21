package me.deathjockey.tinypixel;

import me.deathjockey.tinypixel.graphics.RenderContext;

public abstract class GameState {

	public abstract void update(TinyPixelGameWrapper wrapper);
	public abstract void render(RenderContext c);
	public abstract int getStateID();

	public void onEnter() {

	}

	public void onExit() {

	}
}
