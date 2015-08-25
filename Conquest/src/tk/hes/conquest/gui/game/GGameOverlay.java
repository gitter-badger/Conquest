package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.game.GameBoard;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GDialog;
import tk.hes.conquest.gui.dialog.GTitleDialog;

import java.util.ArrayList;

/**
 * The game overlay which will be rendered on top of "tk.hes.conquest.game" gameplay.
 *
 * @author James Roberts
 */
public class GGameOverlay extends GComponent {

    private ArrayList<GTitleDialog> dialogBoxes;

    private GDominanceBar dominanceBar;
    private GPlayerInfo playerInfo;
	private GHeroInfo heroInfo;
	private GameBoard board;
	private Player player;

    public GGameOverlay(GameBoard board, Player player) {
        super(new Vector2f(0, 0));
		this.board = board;
		this.player = player;
    }

    @Override
    public void init(RenderContext c) {
        dialogBoxes = new ArrayList<>();

        dominanceBar = new GDominanceBar(new Vector2f(0, 0));
        dominanceBar.init(c);
		dominanceBar.setFilledPercent(board.getDominanceValue());

        dialogBoxes = new ArrayList<>();

        playerInfo = new GPlayerInfo(new Vector2f(0, (int) dominanceBar.getSize().getHeight() - 4));
        playerInfo.init(c);

        heroInfo = new GHeroInfo(new Vector2f(64, 16), this);
        heroInfo.init(c);
    }

    @Override
    public void update() {
//		dominanceBar.setFilledPercent(board.getDominanceValue());
//		playerInfo.setChargePercentage((float) player.getCharge() / (float) player.getChargeThreshold() * 100);
//		playerInfo.setMoneyAmount(player.getGold());

		//TODO hero info from player

		dominanceBar.update();
        playerInfo.update();
        heroInfo.update();

        for (int i = 0; i < dialogBoxes.size(); i++) {
            GTitleDialog d = dialogBoxes.get(i);
            if (d.shouldRemove()) dialogBoxes.remove(i);
            d.update();
        }
    }

    @Override
    public void render(RenderContext c) {
        dominanceBar.render(c);
        playerInfo.render(c);
        heroInfo.render(c);
        for (GDialog d : dialogBoxes)
			d.render(c);
    }

    public void addDialogBox(GTitleDialog dialog) {
        this.dialogBoxes.add(dialog);
    }

    public void setGold(int amount) {
        this.playerInfo.setMoneyAmount(amount);
    }

    public void setCharge(float amount, float maxCharge) {
        this.playerInfo.setChargePercentage((amount / maxCharge) * 100);
    }

	public void setPlayer(Player player) {
		this.player = player;
	}
}
