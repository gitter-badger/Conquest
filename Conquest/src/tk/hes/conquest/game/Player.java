package tk.hes.conquest.game;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.ConquestGameDesktopLauncher;
import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.graphics.Art;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author Kevin Yang
 */
public class Player {

    private String name;
	private int gold;
	private int charge = 100;
	private int chargeThreshold = 100;
    private Race race;
    private Origin origin;

    private int deployLane = 0;
    private GameBoard board;

	//actor unit selection
	private final LinkedHashMap<ActorType, Actor> actorBuffer = new LinkedHashMap<>();
	private final ArrayList<ActorType> actorsOwned = new ArrayList<>();
	private int actorSelectCaret = 0;

    public Player(String name, Race race, Origin origin, int gold) {
        this.name = name;
        this.race = race;
        this.origin = origin;
		this.gold = gold;
    }

    public void render(RenderContext c) {
        int rx = -1, ry = -1;
        Bitmap cursor = Art.UI_CURSOR;
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
		//TODO temporary key codes
        if (Input.getKeyPressed(KeyEvent.VK_UP)) {
            if (deployLane < 5)
                deployLane++;
        } else if (Input.getKeyPressed(KeyEvent.VK_DOWN)) {
            if (deployLane > 0)
                deployLane--;
        }

		if (Input.getKeyPressed(KeyEvent.VK_LEFT)) {
			if (actorSelectCaret > 0)
				actorSelectCaret--;
			else
				actorSelectCaret = actorsOwned.size() - 1;
		} else if (Input.getKeyPressed(KeyEvent.VK_RIGHT)) {
			if (actorSelectCaret < actorsOwned.size() - 1)
				actorSelectCaret++;
			else
				actorSelectCaret = 0;
		}


        if (Input.getKeyPressed(KeyEvent.VK_SPACE)) {
            if (this.race.equals(Race.HUMAN))
                board.addActor(ActorFactory.make(this, race, getActorSelection()), deployLane);
        }

		if(Input.getKeyPressed(KeyEvent.VK_C)) {
			deployCharge();
		}
    }

	//Create a buffer actor / replacing existing sample
	public void updateBufferActor(ActorType actorType) {
		actorBuffer.put(actorType, ActorFactory.make(this, race, actorType));
		if(!actorsOwned.contains(actorType))
			actorsOwned.add(actorType);
	}

	public boolean isActorInBuffer(ActorType actorType) {
		return actorBuffer.get(actorType) != null && actorsOwned.contains(actorType);
	}

	public void removeActorFromBuffer(ActorType actorType) {
		actorBuffer.remove(actorType);
		actorsOwned.remove(actorType);
	}

	public LinkedHashMap<ActorType, Actor> getActorBuffer() {
		return actorBuffer;
	}

	public ArrayList<ActorType> getActorsOwned() {
		return actorsOwned;
	}

	public ActorType getActorSelection() {
		return actorsOwned.get(actorSelectCaret);
	}

	//Sends a row of units
	public void deployCharge() {
		if(charge >= chargeThreshold) {
			charge = 0;
			chargeThreshold += 25;

			//TODO verify unit selection, hero units cannot be deployed as charge
			//TODO get currently selected 'unit' for charge and send them to lane

			//TODO TEMPORARY!!
			board.sendChargeWave(this, race, ActorType.MELEE);
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
		if(charge > chargeThreshold) {
			charge = chargeThreshold;
		}
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

    public int getChargeThreshold() {
        return chargeThreshold;
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
