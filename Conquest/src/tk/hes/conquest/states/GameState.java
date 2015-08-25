package tk.hes.conquest.states;

import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;
import tk.hes.conquest.game.GameBoard;
import tk.hes.conquest.game.Origin;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.game.Race;
import tk.hes.conquest.particle.ParticleManager;

/**
 * GameState where the primary gameplay will take place
 *
 * @author Kevin Yang
 */
public class GameState extends PixelState {

    private GameBoard board;

    public GameState(TinyPixelStateBasedGame game) {
        super(game);
    }

    @Override
    public void init(RenderContext c) {
        Player player1 = new Player("Kevin", Race.HUMAN, Origin.WEST, 100);
        Player player2 = new Player("Dumhead", Race.HUMAN, Origin.EAST, 100);

        board = new GameBoard(player1, player2, 6, 50, 600000);
    }

    @Override
    public void update() {
        board.update();
		ParticleManager.get().update();
    }

    @Override
    public void render(RenderContext c) {
        board.render(c);
		ParticleManager.get().render(c);
    }

    @Override
    public int getID() {
        return StateID.GAME_STATE.getID();
    }
}
