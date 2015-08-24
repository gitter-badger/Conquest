package tk.hes.conquest.game;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.ConquestGameDesktopLauncher;
import tk.hes.conquest.graphics.Art;

import java.awt.event.KeyEvent;

public class Player {

    private String name;
	private int gold;
	private int charge = 0;
	private int chargeThreshold;
    private Race race;
    private Origin origin;

    private int deployLane = 0;
    private GameBoard board;

    public Player(String name, Race race, Origin origin, int gold) {
        this.name = name;
        this.race = race;
        this.origin = origin;
		this.gold = gold;
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
		//TODO temporary
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

	//Sends a row of units
	public void deployCharge() {
		if(charge >= chargeThreshold) {
			charge = 0;
			chargeThreshold += 25;

			//TODO verify unit selection, hero units cannot be deployed as charge
			//TODO get currently selected 'unit' for charge and send them to lane
		}
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public void addCharge(int amount) {
		this.charge += amount;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public void addGold(int amount) {
		gold += amount;
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
