package tk.hes.conquest.actor;

import tk.hes.conquest.actor.Actor;
import tk.hes.conquest.actor.ActorType;
import tk.hes.conquest.actor.human.*;

import java.util.HashMap;

/**
 * @author Kevin Yang
 */
public enum ActorRace {

	HUMAN {
		@Override
		public void indexActorMap(HashMap<ActorType, Class<? extends Actor>> map) {
			map.put(ActorType.MELEE, Hu$Warrior.class);
			map.put(ActorType.RANGER, Hu$Ranger.class);
			map.put(ActorType.CASTER, Hu$Mage.class);
			map.put(ActorType.SCOUT, Hu$Assassin.class);
			map.put(ActorType.CASTER2, Hu$Priest.class);
			map.put(ActorType.SPECIAL, Hu$Mystic.class);
			map.put(ActorType.SPECIAL2, Hu$LongSwordman.class);
			map.put(ActorType.SPECIAL3, Hu$Knight.class);
		}
	},

	UNDEAD {
		@Override
		public void indexActorMap(HashMap<ActorType, Class<? extends Actor>> map) {

		}
	};

	private HashMap<ActorType, Class<? extends Actor>> actorMap = new HashMap<>();

	ActorRace(){
		indexActorMap(actorMap);
	}

	//initializes actors for the race
	public abstract void indexActorMap(HashMap<ActorType, Class<? extends Actor>> actorList);

	public HashMap<ActorType, Class<? extends Actor>> getActorMap() {
		return actorMap;
	}
}
