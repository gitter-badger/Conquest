package tk.hes.conquest.actor;

import com.sun.istack.internal.Nullable;
import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2i;
import tk.hes.conquest.game.Player;

import java.util.HashMap;

public abstract class Actor {

	private static final HashMap<Long, Actor> ACTOR_REGISTRAR = new HashMap<>();

	private long actorUID; //for multiplayer synchronization
	private Vector2i position;
	private BB bb;
	private AttributeTuple attributes;
	private Player owner;
	private boolean remove = false;

	//This constructor is invoked automatically via reflection in ActorFactory to create new actors
	public Actor(Player owner, long actorUID) {
		this.owner = owner;
		this.actorUID = actorUID;

		initialize();
	}

	private void initialize() {
		attributes = new AttributeTuple();
		bb = new BB();
		initializeAttributes(attributes, bb);
	}

	protected abstract void initializeAttributes(AttributeTuple tuple, BB bb);

	public void render(RenderContext c) {

	}

	public void update(Input input) {

	}

	public AttributeTuple getAttributes() {
		return attributes;
	}

	public Player getOwner() {
		return owner;
	}

	public long getUID() {
		return actorUID;
	}

	public static long generateNextUID() {
		long uid;
		do {
			uid = (int) (Math.random() * 999999999);
		} while(ACTOR_REGISTRAR.get(uid) != null);
		return uid;
	}

	public static Actor registerActor(Actor actor) {
		ACTOR_REGISTRAR.put(actor.getUID(), actor);
		return actor;
	}

	public static void removeActor(Actor actor) {
		removeActor(actor.getUID());
	}

	public static void removeActor(long actorUID) {
		ACTOR_REGISTRAR.remove(actorUID);
	}

	@Nullable
	public static Actor getActor(long actorUID) {
		return ACTOR_REGISTRAR.get(actorUID);
	}

	public void remove() {
		remove = true;
	}

	public boolean canRemove() {
		return remove;
	}
}
