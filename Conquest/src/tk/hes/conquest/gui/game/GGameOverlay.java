package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.BitFont;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.font.Font;
import tk.hes.conquest.game.ActorType;
import tk.hes.conquest.game.GameBoard;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.gui.base.GComponent;

import java.util.LinkedHashMap;

/**
 * The game overlay which will be rendered on top of "tk.hes.conquest.game" gameplay.
 *
 * @author James Roberts
 */
public class GGameOverlay extends GComponent {

	private GameBoard board;

	//UI
    private GDominanceBar dominanceBar;
    private GPlayerInfo playerInfo;
    private GHeroInfo heroInfo;
	private Player player; //Player with details displayed

    public GGameOverlay(Vector2f position, GameBoard board, Player player) {
        super(position);
		this.board = board;
		this.player = player;
    }

    @Override
    public void init(RenderContext c) {
        dominanceBar = new GDominanceBar(new Vector2f(0, 0));
        dominanceBar.init(c);
        dominanceBar.setFilledPercent(board.getDominanceValue());

        playerInfo = new GPlayerInfo(new Vector2f(0, (int) dominanceBar.getSize().getHeight() - 4));
        playerInfo.init(c);

        heroInfo = new GHeroInfo(new Vector2f(64, 16), this);
        heroInfo.init(c);
    }

    @Override
    public void update() {
		dominanceBar.setFilledPercent(board.getDominanceValue());
		playerInfo.setChargePercentage((float) player.getCharge() / (float) player.getChargeThreshold() * 100);
		playerInfo.setMoneyAmount(player.getGold());

		//TODO hero info from player

		dominanceBar.update();
        playerInfo.update();
        heroInfo.update();
    }

    @Override
    public void render(RenderContext c) {
        dominanceBar.render(c);
        playerInfo.render(c);
        heroInfo.render(c);

		//TODO temporary unit selection / buffer code
		ActorType selected = player.getActorSelection();
		LinkedHashMap<ActorType, Actor> actorBuffer = player.getActorBuffer();
		//Drawing only the one that is currently highlighted,
		//in the actual version a row of actors will be displayed also
		Actor sample = actorBuffer.get(selected);
		sample.setPosition(170, 30);
		sample.render(c);

		c.getFont(Font.NORMAL).render("Use < > arrow keys to\ntoggle actor\nCheck 'Player' & 'GGameOverlay'", 210, 30);

		c.getFont(Font.NORMAL).render(board.getPlayer1().getName(), 10, 3, Colors.PURE_YELLOW);
		c.getFont(Font.NORMAL).render(board.getPlayer2().getName(), c.getWidth() -
				BitFont.widthOf(board.getPlayer2().getName(), c.getFont(Font.NORMAL)) - 10, 3, Colors.PURE_YELLOW);
    }

	public void setPlayer(Player player) {
		this.player = player;
	}
}
