package tk.hes.conquest.states;


import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.game.*;
import tk.hes.conquest.gui.dialog.GTextDialog;
import tk.hes.conquest.gui.game.GGameOverlay;
import tk.hes.conquest.particle.ParticleManager;

import java.awt.*;

/**
 * GameState where the primary gameplay will take place
 *
 * @author Kevin Yang
 */
public class GameState extends PixelState {

    private GameBoard board;
    private GGameOverlay overlay;

    public GameState(TinyPixelStateBasedGame game) {
        super(game);
    }

    @Override
    public void init(RenderContext c) {
        Player player1 = new Player("Kevin", Race.HUMAN, Origin.WEST, 100);
		player1.updateBufferActor(ActorType.MELEE);
		player1.updateBufferActor(ActorType.RANGER);

        Player player2 = new Player("Dumhead", Race.HUMAN, Origin.EAST, 100);

        board = new GameBoard(player1, player2, 6, 50, 600000);
		player2.updateBufferActor(ActorType.RANGER);

        board = new GameBoard(player1, player2, 6, 50, 600000);
		overlay = new GGameOverlay(board, player1);
		overlay.init(c);

        String name = System.getProperty("user.name");
        GTextDialog dialog = new GTextDialog("Hello, " + name + "!", new Vector2f(2, 80), new Dimension(100, 50));
        dialog.setMessage("Try closing me!\nIt works!");
        dialog.init(c);
        overlay.addDialogBox(dialog);
    }

    @Override
    public void update() {
        board.update();
		ParticleManager.get().update();
        overlay.update();
    }

    @Override
    public void render(RenderContext c) {
        board.render(c);
		ParticleManager.get().render(c);
        overlay.render(c);
    }

    @Override
    public int getID() {
        return StateID.GAME_STATE.getID();
    }
}
