package tk.hes.conquest.states;

import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.game.GameBoard;
import tk.hes.conquest.game.Origin;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.game.Race;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.dialog.GDialogType;
import tk.hes.conquest.gui.base.dialog.GTextDialog;

import java.awt.*;

/**
 * GameState where the primary gameplay will take place
 *
 * @author Kevin Yang
 */
public class GameState extends PixelState {

    int r = 0, g = 0, b = 0;
    Bitmap bitmap;
    private GameBoard board;
    private GTextDialog dialog;

    public GameState(TinyPixelStateBasedGame game) {
        super(game);
    }

    @Override
    public void init(RenderContext c) {
        Player player1 = new Player("Kevin", Race.HUMAN, Origin.WEST, 100);
        Player player2 = new Player("Dumhead", Race.HUMAN, Origin.EAST, 100);

        board = new GameBoard(player1, player2, 6, 50, 600000);

        //Test
        bitmap = Art.UNIT_HUMAN_MELEE.getSprite(0, 0);
        dialog = new GTextDialog("Message!", new Vector2f(10, 10), new Dimension(300, 85), GDialogType.INFORMATION);
        dialog.setPosition(dialog.getPosition().getX(), (int) (c.getHeight() - dialog.getSize().getHeight() - 10));
        dialog.setMessage("Use up & down arrow keys to toggle deploy lane\n" +
                "Press [SPACE] to drop warriors. \n\nGrab some popcorn and watch them fight.");
        dialog.init(c);
    }

    @Override
    public void update() {
        board.update();
        dialog.update();
    }

    @Override
    public void render(RenderContext c) {
        board.render(c);
        dialog.render(c);
    }

    @Override
    public int getID() {
        return StateID.GAME_STATE.getID();
    }
}
