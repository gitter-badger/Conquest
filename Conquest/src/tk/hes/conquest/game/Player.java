package tk.hes.conquest.game;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.ConquestGameDesktopLauncher;
import tk.hes.conquest.graphics.Art;

import java.awt.event.KeyEvent;
import java.security.Key;

public class Player {

    private String name;
    private Race race;
    private Origin origin;

    private int deployLane = 0;
    private GameBoard board;

    public Player(String name, Race race, Origin origin) {
        this.name = name;
        this.race = race;
        this.origin = origin;
    }

    public void render(RenderContext c) {
        int rx = -1, ry = -1;
        Bitmap cursor = Art.UI_UNIT_DEPLOY_CURSOR;
        switch (origin) {
            case WEST:
                rx = 10;
                break;
            case EAST:
                rx = c.getWidth() - 10 - cursor.getWidth();
                cursor = cursor.getFlipped(false, true);
                break;
        }
        ry = ConquestGameDesktopLauncher.INIT_HEIGHT / 3 - deployLane * 25;
        c.render(cursor, rx, ry);
    }

    public void update() {
        if (Input.getKeyPressed(KeyEvent.VK_UP)) {
            if (deployLane < 5)
                deployLane++;
        } else if (Input.getKeyPressed(KeyEvent.VK_DOWN)) {
            if (deployLane > 0)
                deployLane--;
        }
        if (Input.getKeyPressed(KeyEvent.VK_SPACE)) {
            if (this.race.equals(Race.HUMAN))
                board.addActor(ActorFactory.make(this, race, ActorType.MELEE), deployLane);
        }
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public String getName() {
        return name;
    }

    public Race getRace() {
        return race;
    }

    public Origin getOrigin() {
        return origin;
    }
}
