package me.deathjockey.tinypixel.test;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;

import java.awt.event.KeyEvent;

/**
 * Created by James on 8/21/2015.
 */
public class StateOne extends PixelState {

    public StateOne(TinyPixelStateBasedGame game) {
        super(game);
    }

    @Override
    public void init() {
    }

    @Override
    public void update() {
        if (Input.getMouseClicked(Input.MOUSE_LEFT))
            System.out.println("MOUSE ELFT");
        if (Input.getKeyDown(KeyEvent.VK_W)) {
            System.out.println("W KEY");
        }
    }

    @Override
    public void render(RenderContext c) {
        c.fillRegion(0, 0, c.getWidth(), c.getHeight(), Colors.PURE_RED);
    }


    @Override
    public int getID() {
        return StateID.STATE_ONE.getID();
    }
}
