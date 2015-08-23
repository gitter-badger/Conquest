package tk.hes.conquest;

import me.deathjockey.tinypixel.TinyPixelGame;
import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.font.Font;
import tk.hes.conquest.states.GameState;
import tk.hes.conquest.states.StateID;
import tk.hes.conquest.states.TestState;

/**
 * The main ConquestGame instance.
 *
 * @author James Roberts, Kevin Yang
 */
public class ConquestGame extends TinyPixelStateBasedGame {

    protected static ConquestGame instance;

    public ConquestGame(String title, int width, int height) {
        super(title, width, height);

    }

    public static ConquestGame instance() {
        return instance;
    }

    @Override
    public void indexStates() {
        addState(new GameState(this));
        addState(new TestState(this));
        enterState(StateID.TEST_STATE.getID());
    }

    @Override
    protected void init(TinyPixelGame game, RenderContext renderContext) {
        Font font = new Font();
        font.initFonts();
        font.addFonts(renderContext);
        super.init(game, renderContext);
    }
}
