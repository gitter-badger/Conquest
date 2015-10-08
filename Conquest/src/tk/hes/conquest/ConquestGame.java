package tk.hes.conquest;

import me.nibby.pix.PixMultiStateGame;
import me.nibby.pix.RenderContext;
import tk.hes.conquest.states.GameState;
import tk.hes.conquest.states.StateID;

/**
 * The main ConquestGame instance.
 *
 * @author James Roberts, Kevin Yang
 */
public class ConquestGame extends PixMultiStateGame {

    protected static ConquestGame instance;

    public ConquestGame(String title, int width, int height, int gfxScale) {
        super(title, width, height, gfxScale);

    }

    @Override
    public void initializeGameStates() {
        registerState(new GameState());
        enterState(StateID.GAME_STATE.getID());
    }

    public static ConquestGame instance() {
        return instance;
    }
}
