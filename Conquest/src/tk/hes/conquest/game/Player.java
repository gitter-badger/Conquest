package tk.hes.conquest.game;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.BitFont;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.ConquestGameDesktopLauncher;
import tk.hes.conquest.font.Font;
import tk.hes.conquest.graphics.Art;

import java.awt.event.KeyEvent;

public class Player {

    private String name;
	private int gold;
	private int charge = 100;
	private int chargeThreshold = 100;
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

		//TODO temporary
		String goldString = "Gold: " + gold;
		c.getFont(Font.NORMAL).render(goldString, (origin.equals(Origin.WEST) ? 10 : c.getWidth() - 30 - BitFont.widthOf(goldString, c.getFont(Font.NORMAL))),
				10, Colors.PURE_YELLOW);
		String chargeString = "Charge: " + charge + "/" + chargeThreshold;
		c.getFont(Font.NORMAL).render(chargeString, (origin.equals(Origin.WEST) ? 10 : c.getWidth() - 30 - BitFont.widthOf(chargeString, c.getFont(Font.NORMAL))),
				20, Colors.PURE_CYAN);

		String dominanceString = "Dominance: " + (origin.equals(Origin.WEST) ? board.getDominanceValue() : 100 - board.getDominanceValue());
		c.getFont(Font.NORMAL).render(dominanceString, (origin.equals(Origin.WEST) ? 10 : c.getWidth() - 30 - BitFont.widthOf(chargeString, c.getFont(Font.NORMAL))),
				40, Colors.PURE_WHITE);

		if(charge == chargeThreshold) {
			String chargeReadyString = "Charge ready! Press [C]";
			c.getFont(Font.NORMAL).render(chargeReadyString, (origin.equals(Origin.WEST) ? 10 : c.getWidth() - 30 - BitFont.widthOf(chargeReadyString, c.getFont(Font.NORMAL))),
					30, Colors.PURE_CYAN);
		}

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

		if(Input.getKeyPressed(KeyEvent.VK_C)) {
			deployCharge();
		}
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
