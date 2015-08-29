package tk.hes.conquest.game;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.ConquestGameDesktopLauncher;
import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.game.scene.Scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameBoard {

	public static final int LANE_SIZE = 16;

	private int time; //time remaining
	private int dominance; //50 start, 0 = p2 win, 100 = p1 win
	private Player player1, player2;
	private int laneCount;
	private Scene scene;

	private final HashMap<Integer, ArrayList<Actor>> actorMap = new HashMap<>();

	public GameBoard(Scene scene, Player player1, Player player2, int maxLaneSize, int startDominance, int timeLimit) {
		this.player1 = player1;
		this.player2 = player2;
		this.laneCount = maxLaneSize;
		this.dominance = startDominance;
		this.time = timeLimit;
		this.scene = scene;

		for(int i = 0; i < maxLaneSize; i++) {
			actorMap.put(i, new ArrayList<>());
		}
		player1.setBoard(this);
		player2.setBoard(this);
	}

	public void render(RenderContext context) {
		scene.render(context);
		player1.render(context);
		player2.render(context);

		for(int lane : actorMap.keySet()) {
			ArrayList<Actor> laneActors = actorMap.get(lane);
			for(int i = 0; i < laneActors.size(); i++) {
				Actor actor = laneActors.get(i);
				actor.render(context);
			}
		}
	}

    public void update() {
		scene.update();
        player1.update();
        player2.update();

		for(int lane : actorMap.keySet()) {
			ArrayList<Actor> laneActors = actorMap.get(lane);
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
		actor.setPosition(xPos, 128 + lane * LANE_SIZE);
		actor.assignBoard(this, lane);

		ArrayList<Actor> actors = actorMap.get(lane);
		actors.add(actor);
	}

	public void actorDeath(Actor actor) {
		Player owner = actor.getOwner();
		if(owner.equals(player1)) {
			player2.addGold(actor.getAttributes().goldReward + (int) (Math.random() * 5));
			player2.addCharge(actor.getAttributes().chargeReward + (int) (Math.random() * 2));
		}
		if(owner.equals(player2)) {
			player1.addGold(actor.getAttributes().goldReward + (int) (Math.random() * 5));
			player1.addCharge(actor.getAttributes().chargeReward + (int) (Math.random() * 2));
		}
	}

	public void actorReachEdge(Actor actor) {
		actor.remove();
		Player owner = actor.getOwner();
		if(owner.equals(player1)) {
			dominance += actor.getAttributes().chargeReward;
		}
		if(owner.equals(player2)) {
			dominance -= actor.getAttributes().chargeReward;
		}
	}

	public void sendChargeWave(Player player, Race race, ActorType type) {
		for(int i = 0; i < laneCount; i++) {
			addActor(ActorFactory.make(player, race, type), i);
		}
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
		List<Actor> actors = actorMap.get(lane);
		actors.remove(actor);
	}

	public ArrayList<Actor> getNearbyActorsInLane(int lane, Vector2f origin, int range, boolean searchLeft, boolean searchRight) {
		ArrayList<Actor> actors = getActorsInLane(lane);
		ArrayList<Actor> result = new ArrayList<>();
		for(Actor actor : actors) {

			Vector2f position = actor.getPosition();
			float xDiff = origin.getX() - position.getX();
			if(Math.abs(xDiff) <= range && xDiff < 0f && searchLeft) result.add(actor);
			if(xDiff <= range && xDiff > 0f && searchRight) result.add(actor);
			else result.add(actor);
		}
		return result;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public ArrayList<Actor> getActorsInLane(int lane) {
		return actorMap.get(lane);
	}

	public int getDominanceValue() {
		return dominance;
	}

	public int getLaneCount() {
		return laneCount;
	}

	public static ArrayList<Actor> filter(ArrayList<Actor> search, Player owner, boolean allied, boolean enemy, boolean dead) {
		ArrayList<Actor> result = new ArrayList<>();
		for(Actor actor : search) {
			if(actor.getOwner().equals(owner) && allied)
				result.add(actor);
			else if(!actor.getOwner().equals(owner) && enemy)
				result.add(actor);

			if(actor.isDead() && dead)
				result.add(actor);
			else if(!actor.isDead() && !dead)
				result.add(actor);
		}
		return result;
	}
}
