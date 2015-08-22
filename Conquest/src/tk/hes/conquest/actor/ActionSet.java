package tk.hes.conquest.actor;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;

/*
	A set of registrar which keeps a record of unit animation
 */
public class ActionSet {

	private Actor owner;
	private ActionType lastOwnerAction;
	private HashMap<ActionType, Action> actionMap = new HashMap<>();

	public ActionSet(Actor owner) {
		this.owner = owner;
	}

	public void update() {
		ActionType current = owner.getCurrentAction();
		Action action = actionMap.get(current);
		if(action == null) return;

		if(!current.equals(lastOwnerAction)) {
			action.reset();
		}
		action.update();
		lastOwnerAction = current;
	}

	public void set(ActionType action, Action animation) {
		actionMap.put(action, animation);
	}

	@Nullable
	public Action get(ActionType action) {
		return actionMap.get(action);
	}
}
