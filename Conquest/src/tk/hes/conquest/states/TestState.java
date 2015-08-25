package tk.hes.conquest.states;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;

import java.awt.event.KeyEvent;

public class TestState extends PixelState {

    public TestState(TinyPixelStateBasedGame game) {
        super(game);
    }

    @Override
    public void init(RenderContext c) {
    }


    @Override
    public void update() {
        if (Input.getKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);

    }

    @Override
    public void render(RenderContext c) {

    }

    @Override
    public int getID() {
        return StateID.TEST_STATE.getID();
    }

}
