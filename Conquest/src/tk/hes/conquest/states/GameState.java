package tk.hes.conquest.states;


import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.game.*;
import tk.hes.conquest.gui.bar.GBarColor;
import tk.hes.conquest.gui.dialog.GTextDialog;
import tk.hes.conquest.gui.game.GGameOverlay;
import tk.hes.conquest.particle.ParticleManager;

import java.awt.*;
import java.util.LinkedHashMap;

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

        board = new GameBoard(player1, player2, 8, 50, 600000);
		player2.updateActorBuffer(ActorType.RANGER);

		overlay = new GGameOverlay(board, player1);
		overlay.init(c);

        String name = System.getProperty("user.name");
        GTextDialog dialog = new GTextDialog("Hello, " + name + "!", new Vector2f(2, 80), new Dimension(100, 50));
        dialog.setMessage("Try closing me!\nIt works!");
        dialog.init(c);
        overlay.addDialogBox(dialog);

		//TODO temporary, remove
		timerBar = new GBarColor(new Vector2f(160, 65), new Dimension(100, 8), Colors.PURE_WHITE);
		timerBar.init(c);
    }

    @Override
    public void update() {
        board.update();
		ParticleManager.get().update();
        overlay.update();

		//TODO temporary remove
		timerBar.update();
    }

	//TODO temporary, remove
	private GBarColor timerBar;

    @Override
    public void render(RenderContext c) {
        board.render(c);
		ParticleManager.get().render(c);
        overlay.render(c);

		//TODO remove this, and integrate it into the actor selection UI
		//TEMPORARY demonstration code for retrieving player info
		Player player1 = board.getPlayer1();

		//Actor buffer contains a 'sample' of each actor type owned by the player
		//this sample can be used to render on buttons etc.
		LinkedHashMap<ActorType, Actor> actorBuffer = player1.getActorBuffer();
		//Here I'm dumping it on the screen horizontally
		int i = 0;
		for(ActorType actorType : actorBuffer.keySet()) {
			Actor sample = actorBuffer.get(actorType);
			sample.setPosition(170 + i * 30, 30);
			sample.render(c);
			i++;
		}

		//To get the player's current 'selection'
		ActorType selectedType = player1.getSelectedActor();
		//Draw the selected actor's information
		Actor sample = actorBuffer.get(selectedType);
		String text = "Selection: " + sample.getAttributes().name;
		c.getFont(tk.hes.conquest.font.Font.NORMAL).render(text, 160, 45, Colors.PURE_YELLOW);
		String description = sample.getAttributes().lore;
		c.getFont(tk.hes.conquest.font.Font.NORMAL).render(description, 160, 55, Colors.PURE_WHITE);
		timerBar.render(c);
		timerBar.setFilledPercent(100f - (player1.getActorCooldown(selectedType)) * 100f);
    }

    @Override
    public int getID() {
        return StateID.GAME_STATE.getID();
    }
}
