package tk.hes.conquest.actor;

import com.sun.istack.internal.Nullable;
import tk.hes.conquest.game.Player;

import java.util.HashMap;

public class ActorFactory {

	@Nullable
	public static Actor make(Player owner, ActorRace race, ActorType actorType) {
		HashMap<ActorType, Class<? extends Actor>> raceActorMap = race.getActorMap();
		Class<? extends Actor> actorClass = raceActorMap.get(actorType);
		if (actorClass != null) {
			try {
				Actor actor = actorClass.getConstructor(Player.class).newInstance(owner);
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
