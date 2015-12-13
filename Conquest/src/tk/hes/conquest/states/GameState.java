package tk.hes.conquest.states;

import me.nibby.pix.Input;
import me.nibby.pix.PixGameState;
import me.nibby.pix.RenderContext;
import tk.hes.conquest.actor.ActorRace;
import tk.hes.conquest.actor.ActorType;
import tk.hes.conquest.game.*;
import tk.hes.conquest.game.scene.DungeonScene;
import tk.hes.conquest.gui.game.GGameOverlay;
import tk.hes.conquest.particle.ParticleManager;

import java.awt.event.KeyEvent;

/**
 * GameState where the primary gameplay will take place
 *
 * @author Kevin Yang
 */
public class GameState extends PixGameState {

	private GameBoard board;
    private GGameOverlay overlay;

    @Override
    public void gameInitialize() {
        Player player1 = new Player("Kevin", ActorRace.HUMAN, Origin.WEST, 100);
		player1.updateActorBuffer(ActorType.MELEE);
		player1.updateActorBuffer(ActorType.RANGER);
		player1.updateActorBuffer(ActorType.CASTER);
		player1.updateActorBuffer(ActorType.SCOUT);
		player1.updateActorBuffer(ActorType.CASTER2);
		player1.updateActorBuffer(ActorType.SPECIAL);
		player1.updateActorBuffer(ActorType.SPECIAL2);
		player1.updateActorBuffer(ActorType.SPECIAL3);

        Player player2 = new Player("Dumhead", ActorRace.HUMAN, Origin.EAST, 100);
		player2.updateActorBuffer(ActorType.MELEE);
		player2.updateActorBuffer(ActorType.RANGER);
		player2.updateActorBuffer(ActorType.CASTER);
		player2.updateActorBuffer(ActorType.SCOUT);
		player2.updateActorBuffer(ActorType.CASTER2);
		player2.updateActorBuffer(ActorType.SPECIAL);
		player2.updateActorBuffer(ActorType.SPECIAL2);
		player2.updateActorBuffer(ActorType.SPECIAL3);

		board = new GameBoard(new DungeonScene(), player1, player2, 8, 50, 600000);

		overlay = new GGameOverlay(board, player1);
    }

    @Override
    public void gameUpdate(Input input, double delta) {
        if (input.isKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);
        board.update(input, delta);
        ParticleManager.get().update(delta);
        overlay.update(input);

    }


    @Override
    public void gameRender(RenderContext c) {
        board.render(c);
        ParticleManager.get().render(c);
        overlay.render(c);
    }

    @Override
    public int getStateID() {
        return StateID.GAME_STATE.getID();
    }
}
