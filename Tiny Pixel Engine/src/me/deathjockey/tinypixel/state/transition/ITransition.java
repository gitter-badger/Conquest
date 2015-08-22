package me.deathjockey.tinypixel.state.transition;

import me.deathjockey.tinypixel.state.PixelState;

/**
 * Created by James on 8/21/2015.
 */
public interface ITransition {


    void init(PixelState firstState, PixelState secondState);

    void update();

    void render();

    boolean isComplete();
}
