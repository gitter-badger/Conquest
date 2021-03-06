package tk.hes.conquest.game;

import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.actor.ActorFactory;
import tk.hes.conquest.actor.ActorRace;
import tk.hes.conquest.actor.ActorType;
import tk.hes.conquest.game.scene.Scene;

import java.awt.*;
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
	private int boardWidth = 0;

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

		ArrayList<Actor> aliveActors = new ArrayList<>();
		for(int lane : actorMap.keySet()) {
			ArrayList<Actor> laneActors = actorMap.get(lane);
			for(int i = 0; i < laneActors.size(); i++) {
				Actor actor = laneActors.get(i);

				if(actor.isDead())
					actor.render(context);
				else
					aliveActors.add(actor);
			}

            for (Actor actor : aliveActors) {
                actor.render(context);
            }
        }


		boardWidth = context.getWidth();
	}

    public void update(Input input, double delta) {
		scene.update(delta);
        player1.update(input);
        player2.update(input);

		for(int lane : actorMap.keySet()) {
			ArrayList<Actor> laneActors = actorMap.get(lane);
			for(int i = 0; i < laneActors.size(); i++) {
				Actor actor = laneActors.get(i);
                actor.update(delta);
                if (actor.canRemove()) {
                    removeActor(actor, lane);
					i--;
				}
			}
		}
	}

	public void addActor(Actor actor, int lane) {
		Player owner = actor.getOwner();
		int xPos = 0;
		switch(owner.getOrigin()) {
			case WEST: xPos = 25; break;
			case EAST: xPos = boardWidth - 35; break;
		}
		actor.setPosition(xPos, 128 + lane * LANE_SIZE - (int) (actor.getBB().getHeight() - 8 * Actor.SPRITE_SCALE));
		actor.assignBoard(this, lane);

		ArrayList<Actor> actors = actorMap.get(lane);
		actors.add(actor);
        System.out.println(actors.size());
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
			dominance += actor.getAttributes().dominanceReward;
		}
		if(owner.equals(player2)) {
			dominance -= actor.getAttributes().dominanceReward;
		}
	}

	public void sendChargeWave(Player player, ActorRace race, ActorType type) {
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
		if(!searchLeft && !searchRight) return new ArrayList<>();

		ArrayList<Actor> actors = getActorsInLane(lane);
		ArrayList<Actor> result = new ArrayList<>();
		int bx = (searchLeft) ? (int) origin.getX() - range : (int) origin.getX();
		int by = (int) origin.getY();
		int bw;
		int bh = range;
		if(searchLeft && searchRight) bw = 2 * range;
		else bw = range;
		Rectangle box = new Rectangle(bx, by, bw, bh);
		for(Actor actor : actors) {
			Rectangle bounds = actor.getBounds();
			if(box.intersects(bounds)) {
				result.add(actor);
			}
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
