package tk.hes.conquest;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.InputKey;
import me.deathjockey.tinypixel.TinyPixelGame;
import me.deathjockey.tinypixel.graphics.BitFont;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.game.GameBoard;
import tk.hes.conquest.game.Origin;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.game.Race;
import tk.hes.conquest.graphics.Art;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by James on 8/19/2015.
 */
public class ConquestGame extends TinyPixelGame {

	protected static ConquestGame instance;
	private GameBoard board;
	private BitFont gameFont;

    public ConquestGame(String title, int width, int height) {
        super(title, width, height);

    }

    @Override
    protected void init(TinyPixelGame game, RenderContext renderContext) {
		gameFont = new BitFont(Art.FONT, "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
				"abcdefghijklmnopqrstuvwxyz" +
				"0123456789;'\"             " +
				"!@#$%^&*()-+_=~.,<>?/\\[]|:", 4, 26, new Dimension(6, 10));
		gameFont.addSpacingRule("sxItrf<>?", 5, 0);
		gameFont.addSpacingRule("^1*()+-;:/.,", 4, 0);
		gameFont.addSpacingRule("j", 4, 1);
		gameFont.addSpacingRule("li!", 3, 0);
		renderContext.installFont("normal", gameFont);

		//TODO temporary
		game.getInput().registerKey("up", new InputKey(KeyEvent.VK_UP));
		game.getInput().registerKey("down", new InputKey(KeyEvent.VK_DOWN));
		game.getInput().registerKey("deploy", new InputKey(KeyEvent.VK_SPACE));

		Player player1 = new Player("Kevin", Race.HUMAN, Origin.WEST);
		Player player2 = new Player("Dumhead", Race.HUMAN, Origin.EAST);

		board = new GameBoard(player1, player2, 6, 50, 600000);

		//Test
		bitmap = Art.UNIT_HUMAN_MELEE.getSprite(0, 0);
	}

	int r = 0, g = 0, b = 0;
	Bitmap bitmap;
    @Override
    protected void gameRender(RenderContext c) {
		board.render(c);
		c.render(bitmap, 20, 20, 1.0f, Colors.PURE_RED);
//		r++;
//		if(r > 255) {
//			r = 0; g++;
//		}
//		if(g > 255) {
//			g = 0;
//			b++;
//		}
//		if(b > 255) {
//			r = 0; g = 0; b = 0;
//		}

		c.render(Art.BUTTONS, 100, 100);

		c.getFont("normal").render("Use up & down arrow keys to toggle deploy lane\\" +
				"Press [SPACE] to drop warriors. \\\\Grab some popcorn and watch them fight.", 30, 30, Colors.toInt(r, g, b ,255));
	}

    @Override
    protected void gameUpdate(Input input) {
		board.update(input);
    }

	public ConquestGame instance() {
		return instance;
	}
}
