package tk.hes.conquest.game;

import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.actor.human.Hu$Warrior;

import java.util.HashMap;

/**
 * Created by MrDeathJockey on 22/08/15.
 */
public enum Race {

	HUMAN {
		@Override
		public void indexActorMap(HashMap<ActorType, Class<? extends Actor>> map) {
			map.put(ActorType.MELEE, Hu$Warrior.class);
		}
	},

	UNDEAD {
		@Override
		public void indexActorMap(HashMap<ActorType, Class<? extends Actor>> map) {

		}
	};

	private HashMap<ActorType, Class<? extends Actor>> actorMap = new HashMap<>();

	Race(){
		indexActorMap(actorMap);
	}

	//initializes actors for the race
	public abstract void indexActorMap(HashMap<ActorType, Class<? extends Actor>> actorList);

	public HashMap<ActorType, Class<? extends Actor>> getActorMap() {
		return actorMap;
	}
}
