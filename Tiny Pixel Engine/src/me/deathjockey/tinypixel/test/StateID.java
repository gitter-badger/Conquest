package me.deathjockey.tinypixel.test;

/**
 * Created by James on 8/21/2015.
 */
public enum StateID {
    STATE_ONE(1),
    STATE_TWO(2),
    STATE_THREE(3);

    int id;

    StateID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
