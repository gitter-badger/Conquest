package me.deathjockey.tinypixel.test;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;

/**
 * Created by James on 8/21/2015.
 */
public class StateThree extends PixelState {

    public StateThree(TinyPixelStateBasedGame game) {
        super(game);
    }

    @Override
    public void init() {
        System.out.println("3: INIT");
    }

    @Override
    public void update() {
        System.out.println("3: UPDATE");
    }

    @Override
    public void render(RenderContext c) {
        c.fillRegion(0, 0, c.getWidth(), c.getHeight(), Colors.PURE_GREEN);
    }

    @Override
    public int getID() {
        return StateID.STATE_THREE.getID();
    }
}
