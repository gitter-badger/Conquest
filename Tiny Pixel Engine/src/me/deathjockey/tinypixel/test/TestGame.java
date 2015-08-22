package me.deathjockey.tinypixel.test;

import me.deathjockey.tinypixel.TinyPixelStateBasedGame;

/**
 * Created by James on 8/21/2015.
 */
public class TestGame extends TinyPixelStateBasedGame {

    public TestGame(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    public void indexStates() {
        addState(new StateOne(this));
        addState(new StateTwo(this));
        addState(new StateThree(this));
        enterState(StateID.STATE_ONE.getID());
    }


}
