package tk.hes.conquest.states;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.GPlayerInfo;

import java.awt.event.KeyEvent;

public class TestState extends PixelState {

    private GPlayerInfo playerInfo;

    public TestState(TinyPixelStateBasedGame game) {
        super(game);
    }

    @Override
    public void init(RenderContext c) {
        playerInfo = new GPlayerInfo(new Vector2f(0, 0));
        playerInfo.init(c);
    }


    @Override
    public void update() {
        if (Input.getKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);
        playerInfo.update();
    }

    @Override
    public void render(RenderContext c) {
        playerInfo.render(c);
    }

    @Override
    public int getID() {
        return StateID.TEST_STATE.getID();
    }

}
