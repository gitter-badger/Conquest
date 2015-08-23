package tk.hes.conquest.states;

/**
 * StateID class to keep track of the stateIDs
 *
 * @author James Roberts
 */
public enum StateID {
    GAME_STATE(0),
    TEST_STATE(1);

    private int ID;

    StateID(int id) {
        this.ID = id;
    }

    public int getID() {
        return ID;
    }
}
