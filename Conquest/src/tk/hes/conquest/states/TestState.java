package tk.hes.conquest.states;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.game.GGameOverlay;

import java.awt.event.KeyEvent;

public class TestState extends PixelState {

    public GGameOverlay gameOverlay;

    public TestState(TinyPixelStateBasedGame game) {
        super(game);
    }

    @Override
    public void init(RenderContext c) {
        gameOverlay = new GGameOverlay(new Vector2f(0, 0));
        gameOverlay.init(c);
    }


    @Override
    public void update() {
        if (Input.getKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);
        gameOverlay.update();
    }

    @Override
    public void render(RenderContext c) {
        gameOverlay.render(c);
    }

    @Override
    public int getID() {
        return StateID.TEST_STATE.getID();
    }

}
