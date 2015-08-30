package tk.hes.conquest.states;


import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;
import tk.hes.conquest.game.*;
import tk.hes.conquest.game.scene.OutpostScene;
import tk.hes.conquest.gui.game.GGameOverlay;
import tk.hes.conquest.particle.ParticleManager;

import java.awt.event.KeyEvent;

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
		player1.updateActorBuffer(ActorType.MELEE);
		player1.updateActorBuffer(ActorType.RANGER);
		player1.updateActorBuffer(ActorType.CASTER);
		player1.updateActorBuffer(ActorType.SCOUT);
		player1.updateActorBuffer(ActorType.CASTER2);

        Player player2 = new Player("Dumhead", Race.HUMAN, Origin.EAST, 100);
		player2.updateActorBuffer(ActorType.MELEE);

		board = new GameBoard(new OutpostScene(), player1, player2, 8, 50, 600000);

		overlay = new GGameOverlay(board, player1);
		overlay.init(c);


    }

    @Override
    public void update() {
        if (Input.getKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);
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
