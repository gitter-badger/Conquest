package tk.hes.conquest.game;

import tk.hes.conquest.gui.game.GGameOverlay;

/**
 * Interface between game data and UI elements.
 *
 * @author James
 */
public class GameManager {

    private Player player;
    private GameBoard board;
    private GGameOverlay overlay;

    public GameManager(GGameOverlay overlay, Player player, GameBoard board) {
        this.overlay = overlay;
        this.player = player;
        this.board = board;
    }


    public void sync() {
        overlay.setGold(player.getGold());
        overlay.setCharge(player.getCharge(), player.getChargeThreshold());

        overlay.setDominance(board.getDominanceValue());
    }


}
