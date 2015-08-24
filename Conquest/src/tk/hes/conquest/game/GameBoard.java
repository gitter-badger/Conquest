package tk.hes.conquest.game;

import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.ConquestGameDesktopLauncher;
import tk.hes.conquest.actor.Actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameBoard {

	public static final int LANE_SIZE = 25;

	private int time; //time remaining
	private int dominance; //50 start, 0 = p2 win, 100 = p1 win
	private Player player1, player2;
	private int laneSize;

	private final HashMap<Integer, ArrayList<Actor>> entityMap = new HashMap<>();

	public GameBoard(Player player1, Player player2, int maxLaneSize, int startDominance, int timeLimit) {
		this.player1 = player1;
		this.player2 = player2;
		this.laneSize = maxLaneSize;
		this.dominance = startDominance;
		this.time = timeLimit;

		for(int i = 0; i < maxLaneSize; i++) {
			entityMap.put(i, new ArrayList<Actor>());
		}
		player1.setBoard(this);
		player2.setBoard(this);
	}

	public void render(RenderContext context) {
		player1.render(context);
		player2.render(context);

		for(int lane : entityMap.keySet()) {
			ArrayList<Actor> laneActors = entityMap.get(lane);
			for(int i = 0; i < laneActors.size(); i++) {
				Actor actor = laneActors.get(i);
				actor.render(context);
			}
		}
	}

    public void update() {
        player1.update();
        player2.update();

		for(int lane : entityMap.keySet()) {
			ArrayList<Actor> laneActors = entityMap.get(lane);
			for(int i = 0; i < laneActors.size(); i++) {
				Actor actor = laneActors.get(i);
                actor.update();
                if (actor.canRemove()) {
                    removeActor(actor, lane);
					i--;
				}
			}
		}
	}

	public void addActor(Actor actor, int lane) {
		//TODO TEMPORARY!!
		Player owner = actor.getOwner();
		int xPos = 0;
		switch(owner.getOrigin()) {
			case WEST: xPos = 25; break;
			case EAST: xPos = ConquestGameDesktopLauncher.INIT_WIDTH / 2 - 25; break;
		}
		actor.setPosition(xPos, ConquestGameDesktopLauncher.INIT_HEIGHT / 3 - lane * 25);
		actor.assignBoard(this, lane);

		ArrayList<Actor> actors = entityMap.get(lane);
		actors.add(actor);
	}

	public ArrayList<Actor> getOpponentActorsInLane(Player owner, int lane) {
		ArrayList<Actor> result = new ArrayList<>();
		ArrayList<Actor> actors = getActorsInLane(lane);
		for(Actor actor : actors) {
			if(actor.getOwner().equals(owner)) continue;
			result.add(actor);
		}
		return result;
	}

	public void removeActor(Actor actor, int lane) {
		List<Actor> actors = entityMap.get(lane);
		actors.remove(actor);
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public ArrayList<Actor> getActorsInLane(int lane) {
		return entityMap.get(lane);
	}
}
