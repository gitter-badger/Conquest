package tk.hes.conquest.game;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.graphics.Art;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Player {

    private String name;
	private int gold;
	private int charge = 100;
	private int chargeThreshold = 100;
    private Race race;
    private Origin origin;

	private long lastDeployTime = System.currentTimeMillis();
	private final LinkedHashMap<ActorType, Actor> actorBuffer = new LinkedHashMap<>();
	private final ArrayList<ActorType> actorsOwned = new ArrayList<>();
	private int actorSelectIndex = 0;

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
		ry = 128 + deployLane * GameBoard.LANE_SIZE;
        c.render(cursor, rx, ry);

	}

    public void update() {
		//TODO temporary
        if (Input.getKeyPressed(KeyEvent.VK_UP)) {
            if (deployLane > 0)
                deployLane--;
        } else if (Input.getKeyPressed(KeyEvent.VK_DOWN)) {
            if (deployLane < board.getLaneCount() - 1)
                deployLane++;
        }
		if (Input.getKeyPressed(KeyEvent.VK_LEFT)) {
			if(actorSelectIndex > 0)
				actorSelectIndex--;
			else
				actorSelectIndex = actorsOwned.size() - 1;
		} else if (Input.getKeyPressed(KeyEvent.VK_RIGHT)) {
			if(actorSelectIndex < actorsOwned.size() - 1)
				actorSelectIndex++;
			else
				actorSelectIndex = 0;
		}


        if (Input.getKeyPressed(KeyEvent.VK_SPACE)) {
            deployActor(getSelectedActor());
        }

		if(Input.getKeyPressed(KeyEvent.VK_C)) {
			deployCharge();
		}
    }

	private void deployActor(ActorType actorType) {
		if(canDeployActor(actorType)) {
			board.addActor(ActorFactory.make(this, race, actorType), deployLane);
			lastDeployTime = System.currentTimeMillis();
		}
	}

	public boolean canDeployActor(ActorType type) {
		return System.currentTimeMillis() - lastDeployTime >= actorBuffer.get(type).getAttributes().deployDelay;
	}

	/*
	 * Gets the cooldown time as a percentage between 0f and 1f
	 * with 1f being 100% cool down (ready for deploy).
	 *
	 * This method will return -1 if the given actorType is not
	 * owned by the player
	 */
	public float getActorCooldown(ActorType actorType) {
		if(!actorsOwned.contains(actorType) || actorBuffer.get(actorType) == null)
			return -1;

		Actor sample = actorBuffer.get(actorType);
		float percentage = ((System.currentTimeMillis() - lastDeployTime) / (float) sample.getAttributes().deployDelay);
		if(percentage > 1f) percentage = 1f;
		return percentage;
	}

	//Sends a row of units
	public void deployCharge() {
		if(charge >= chargeThreshold) {
			charge = 0;
			chargeThreshold += 20;
			board.sendChargeWave(this, race, getSelectedActor());
		}
	}

	public void updateActorBuffer(ActorType actorType) {
		updateActorBuffer(actorType, race);
	}

	public LinkedHashMap<ActorType, Actor> getActorBuffer() {
		return actorBuffer;
	}

	public ArrayList<ActorType> getActorsOwned() {
		return actorsOwned;
	}

	private void updateActorBuffer(ActorType actorType, Race race) {
		actorBuffer.put(actorType, ActorFactory.make(this, race, actorType));
		if(!actorsOwned.contains(actorType)) {
			actorsOwned.add(actorType);
		}
	}

	public void removeActorBuffer(ActorType actorType) {
		actorBuffer.remove(actorType);
		actorsOwned.remove(actorType);
	}

	public ActorType getSelectedActor() {
		return actorsOwned.get(actorSelectIndex);
	}

	public int getSelectedActorIndex() {
		return actorSelectIndex;
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
