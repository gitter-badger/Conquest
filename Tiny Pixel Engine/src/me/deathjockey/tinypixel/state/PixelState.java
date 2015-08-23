package me.deathjockey.tinypixel.state;

import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.RenderContext;

public abstract class PixelState {

    protected TinyPixelStateBasedGame game;

    public PixelState(TinyPixelStateBasedGame game) {
        this.game = game;
    }

    public abstract void init(RenderContext c);

    public abstract void update();

    public abstract void render(RenderContext c);

    public abstract int getID();

    public void onEnter() {

    }

    public void onExit() {

    }

}
