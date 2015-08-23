package me.deathjockey.tinypixel;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;

import java.util.HashMap;

public abstract class TinyPixelStateBasedGame extends TinyPixelGame {

    /**
     * Game state registrar
     */
    private HashMap<Integer, PixelState> gameStates = new HashMap<>();
    private PixelState currentState;
    private PixelState nextState;

    /**
     * Creates an instance of the game with given title, width and height (for context only)
     *
     * @param title  Title of the game (displayed on wrapper if applicable)
     * @param width  Width of the render context
     * @param height Height of the render context
     */
    public TinyPixelStateBasedGame(String title, int width, int height) {
        super(title, width, height);

        currentState = new PixelState(this) {
            @Override
            public void init(RenderContext c) {

            }

            @Override
            public void update() {

            }

            @Override
            public void render(RenderContext c) {

            }

            @Override
            public int getID() {
                return -1;
            }
        };
        indexStates();
    }

    /**
     * Method to be implemented by extending class to add each state to the engine.
     */
    public abstract void indexStates();


    /**
     * Initialization method used to init current game state
     *
     * @param game          Reference to main engine game class
     * @param renderContext Reference to RenderContext (screen)
     */
    @Override
    protected void init(TinyPixelGame game, RenderContext renderContext) {
        if (currentState != null)
            currentState.init(renderContext);
    }

    /**
     * Render method used to render current game state
     *
     * @param renderContext Reference to RenderContext (screen)
     */
    @Override
    protected void gameRender(RenderContext renderContext) {
        if (currentState != null)
            currentState.render(renderContext);
    }

    /**
     * Update method used to update current game state
     */
    @Override
    protected void gameUpdate() {
        if (currentState != null)
            currentState.update();
    }

    /**
     * Used to switch between each states added to the game.
     *
     * @param id The ID to switch to.
     */
    public void enterState(int id) {
        nextState = getState(id);
        if (nextState == null) throw new RuntimeException("No game state registered with the ID: " + id);
        this.currentState = nextState;

    }

    public void addState(PixelState state) {
        gameStates.put(state.getID(), state);
        if (currentState.getID() == -1) this.currentState = state;
    }

    public PixelState getState(int id) {
        return gameStates.get(id);
    }

    public PixelState getCurrentState() {
        return currentState;
    }
}
