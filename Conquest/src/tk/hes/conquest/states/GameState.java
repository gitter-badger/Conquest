package tk.hes.conquest.states;

import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.game.*;
import tk.hes.conquest.gui.game.GGameOverlay;

/**
 * GameState where the primary gameplay will take place
 *
 * @author Kevin Yang
 */
public class GameState extends PixelState {

    private GameBoard board;
    private GGameOverlay overlay;

    private GameManager manager;

    public GameState(TinyPixelStateBasedGame game) {
        super(game);
    }

    @Override
    public void init(RenderContext c) {
        overlay = new GGameOverlay(new Vector2f(0, 0));
        overlay.init(c);

        Player player1 = new Player("Kevin", Race.HUMAN, Origin.WEST, 100);
        Player player2 = new Player("Dumhead", Race.HUMAN, Origin.EAST, 100);
        board = new GameBoard(player1, player2, 6, 50, 600000);

        manager = new GameManager(overlay, player1, board);
    }

    @Override
    public void update() {
        board.update();
        overlay.update();
        manager.sync();
    }

    @Override
    public void render(RenderContext c) {
        board.render(c);
        overlay.render(c);
    }

    @Override
    public int getID() {
        return StateID.GAME_STATE.getID();
    }
}
