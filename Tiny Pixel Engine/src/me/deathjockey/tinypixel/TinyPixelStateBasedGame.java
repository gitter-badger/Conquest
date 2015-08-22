package me.deathjockey.tinypixel;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;
import me.deathjockey.tinypixel.state.transition.EmptyTransition;
import me.deathjockey.tinypixel.state.transition.ITransition;

import java.util.HashMap;

public abstract class TinyPixelStateBasedGame extends TinyPixelGame {

    /**
     * Game state registrar
     */
    private HashMap<Integer, PixelState> gameStates = new HashMap<>();
    private PixelState currentState;
    private PixelState nextState;

    private ITransition enterTransition;
    private ITransition leaveTransition;

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
            public void init() {

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

    public abstract void indexStates();

    @Override
    protected void init(TinyPixelGame game, RenderContext renderContext) {
        if (currentState != null)
            currentState.init();
    }

    @Override
    protected void gameRender(RenderContext renderContext) {
        if (currentState != null)
            currentState.render(renderContext);
    }

    @Override
    protected void gameUpdate() {
        if (currentState != null)
            currentState.update();
    }

    public void enterState(int id) {
        enterState(id, new EmptyTransition(), new EmptyTransition());
    }

    public void enterState(int id, ITransition leave, ITransition enter) {
        if (leave == null) {
            leave = new EmptyTransition();
        }
        if (enter == null) {
            enter = new EmptyTransition();
        }
        leaveTransition = leave;
        enterTransition = enter;

        nextState = getState(id);
        if (nextState == null) {
            throw new RuntimeException("No game state registered with the ID: " + id);
        }

        leaveTransition.init(currentState, nextState);
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
