package tk.hes.conquest.game;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.actor.Actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameMatch {

	private int time; //time remaining
	private int dominance; //50 start, 0 = p2 win, 100 = p1 win
	private Player player1, player2;

	private final HashMap<Integer, ArrayList<Actor>> entityMap = new HashMap<>();

	public GameMatch(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	public void render(RenderContext context) {
		for(int lane : entityMap.keySet()) {
			ArrayList<Actor> laneActors = entityMap.get(lane);
			for(int i = 0; i < laneActors.size(); i++) {
				Actor actor = laneActors.get(i);
				actor.render(context);
			}
		}
	}

	public void update(Input input) {
		for(int lane : entityMap.keySet()) {
			ArrayList<Actor> laneActors = entityMap.get(lane);
			for(int i = 0; i < laneActors.size(); i++) {
				Actor actor = laneActors.get(i);
				actor.update(input);
				if(actor.canRemove()) {
					removeActor(actor, lane);
					i--;
				}
			}
		}
	}

	public void addActor(Actor actor, int lane) {
		ArrayList<Actor> actors = entityMap.get(lane);
		actors.add(actor);
	}

	public void removeActor(Actor actor, int lane) {
		removeActor(actor.getUID(), lane);
	}

	public void removeActor(long actorUID, int lane) {
		List<Actor> actors = entityMap.get(lane);
		for(int i = 0; i < actors.size(); i++) {
			if(actors.get(i).getUID() == actorUID)
				actors.remove(i);

		}
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
}
