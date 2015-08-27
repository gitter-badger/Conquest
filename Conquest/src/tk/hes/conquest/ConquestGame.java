package tk.hes.conquest;

import me.deathjockey.tinypixel.TinyPixelGame;
import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.font.Font;
import tk.hes.conquest.states.GameState;
import tk.hes.conquest.states.StateID;

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

    public void indexStates() {
        addState(new GameState(this));
        enterState(StateID.GAME_STATE.getID());
    }

    @Override
    protected void init(TinyPixelGame game, RenderContext renderContext) {
        renderContext.setClearColor(Colors.toInt(0, 0, 0, 255));
        Font font = new Font();
        font.initFonts();
        font.addFonts(renderContext);
        super.init(game, renderContext);
    }
}
