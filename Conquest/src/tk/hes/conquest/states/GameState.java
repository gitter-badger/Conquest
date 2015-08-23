package tk.hes.conquest.states;

import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;
import tk.hes.conquest.font.Font;
import tk.hes.conquest.game.GameBoard;
import tk.hes.conquest.game.Origin;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.game.Race;
import tk.hes.conquest.graphics.Art;

/**
 * GameState where the primary gameplay will take place
 *
 * @author Kevin Yang
 */
public class GameState extends PixelState {

    int r = 0, g = 0, b = 0;
    Bitmap bitmap;
    private GameBoard board;

    public GameState(TinyPixelStateBasedGame game) {
        super(game);
    }

    @Override
    public void init(RenderContext c) {
        Player player1 = new Player("Kevin", Race.HUMAN, Origin.WEST);
        Player player2 = new Player("Dumhead", Race.HUMAN, Origin.EAST);

        board = new GameBoard(player1, player2, 6, 50, 600000);

        //Test
        bitmap = Art.UNIT_HUMAN_MELEE.getSprite(0, 0);
    }

    @Override
    public void update() {
        board.update();
    }

    @Override
    public void render(RenderContext c) {
        board.render(c);
        c.render(bitmap, 20, 20, 1.0f, Colors.PURE_RED);
        r++;
//        if (r > 255) {
//            r = 0;
//            g++;
//        }
//        if (g > 255) {
//            g = 0;
//            b++;
//        }
//        if (b > 255) {
//            r = 0;
//            g = 0;
//            b = 0;
//        }
        c.render(Art.BUTTONS, 100, 100);

        c.getFont(Font.NORMAL).render("Use up & down arrow keys to toggle deploy lane\\" +
                "Press [SPACE] to drop warriors. \\\\Grab some popcorn and watch them fight.", 30, 30, Colors.toInt(r, g, b, 255));
    }

    @Override
    public int getID() {
        return StateID.GAME_STATE.getID();
    }
}
