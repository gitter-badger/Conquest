package tk.hes.conquest.game;

import com.sun.istack.internal.Nullable;
import tk.hes.conquest.actor.Actor;

import java.util.HashMap;

public class ActorFactory {

	@Nullable
	public static Actor make(Player owner, Race race, ActorType actorType) {
		HashMap<ActorType, Class<? extends Actor>> raceActorMap = race.getActorMap();
		Class<? extends Actor> actorClass = raceActorMap.get(actorType);
		if (actorClass != null) {
			try {
				long uid = Actor.generateNextUID();
				Actor actor = actorClass.getConstructor(Player.class, Long.class).newInstance(owner, uid);
				return actor;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Unable to create: " + race.name() + " for '" + owner.getName()
					+ "' because the corresponding actor class is not found!");
		}
		return null;
	}
}
