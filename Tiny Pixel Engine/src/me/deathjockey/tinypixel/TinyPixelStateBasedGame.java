package me.deathjockey.tinypixel;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import me.deathjockey.tinypixel.graphics.RenderContext;

import java.util.HashMap;

public abstract class TinyPixelStateBasedGame extends TinyPixelGame {

	/** Game state registrar */
	private HashMap<Integer, GameState> gameStates = new HashMap<>();
	private GameState currentState;

	/**
	 * Creates an instance of the game with given title, width and height (for context only)
	 *
	 * @param title  Title of the game (displayed on wrapper if applicable)
	 * @param width  Width of the render context
	 * @param height Height of the render context
	 */
	public TinyPixelStateBasedGame(String title, int width, int height) {
		super(title, width, height);
	}

	public abstract void indexStates();

	@Override
	protected void init(TinyPixelGame game, RenderContext renderContext) {}

	@Override
	protected void gameRender(RenderContext renderContext) {

	}

	@Override
	protected void gameUpdate(Input input) {

	}

	public void addState(@NotNull GameState state) {
		gameStates.put(state.getStateID(), state);
	}

	@Nullable
	public GameState getCurrentState() {
		return currentState;
	}
}
