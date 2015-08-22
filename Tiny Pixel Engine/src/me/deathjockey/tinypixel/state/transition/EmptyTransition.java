package me.deathjockey.tinypixel.state.transition;

import me.deathjockey.tinypixel.state.PixelState;

/**
 * Created by James on 8/21/2015.
 */
public class EmptyTransition implements ITransition {
    @Override
    public void init(PixelState firstState, PixelState secondState) {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public boolean isComplete() {
        return true;
    }
}
