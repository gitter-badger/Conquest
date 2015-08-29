package tk.hes.conquest.actor;

import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;

/**
 * A exemplar of an Actor used for non-functional purposes.
 *
 * @author Kevin Yang
 */
public class SampleActor {

	private Vector2f position;
	private ActionSet actionSet;
	private ActionType currentAction;
	private float drawScale = 1.0f;
	private AttributeTuple attributes;
	private BB bb;
	private Actor sample;
	private boolean flipped = false;
    private boolean visible = true;
    private boolean drawShadow = false;

	public SampleActor(Actor actor) {
		this(actor, new Vector2f(0, 0));
	}

	public SampleActor(Actor actor, Vector2f position) {
		this(actor, position, ActionType.STATIC);
	}

	public SampleActor(Actor actor, Vector2f position, ActionType action) {
		this(actor, position, action, 1.0f);
	}

	public SampleActor(Actor actor, Vector2f position, ActionType action, float scale) {
		this.position = position;
		this.drawScale = scale;
		this.currentAction = action;
		this.sample = actor;
		this.attributes = AttributeTuple.getCopyOf(actor.attributes);
		this.bb = actor.getBB();
		this.actionSet = actor.actionSet;
	}

	public void render(RenderContext c) {
		Action action = actionSet.get(currentAction);
		if(action == null) return;

		Action.Frame frame = action.getCurrentFrame();
		Bitmap sprite = (flipped) ? frame.getBitmap().getFlipped(false, true) : frame.getBitmap();
		int drawX = (int) position.getX() + ((flipped) ? frame.getxOffset() : 0);
		int drawY = (int) position.getY() + ((flipped) ? frame.getyOffset() : 0);
		render(c, drawX, drawY);
	}

	public void render(RenderContext c, float x, float y) {
		if(!visible) return;

		Action action = actionSet.get(currentAction);
		if(action != null) {
			if(drawShadow) {
				Bitmap shadow = Art.UNIT_SHADOW[attributes.shadowType];
				c.render(shadow, (int) (x + bb.getRx() + bb.getWidth() / 2 - shadow.getWidth() / 2),
						(int) (y + bb.getRy() + bb.getHeight() - bb.getHeight() / 4 * 3), 0.7f);
			}
			Action.Frame frame = action.getCurrentFrame();
			Bitmap sprite = (flipped) ? frame.getBitmap().getFlipped(false, true) : frame.getBitmap();
			c.render((drawScale == 1f) ? sprite : sprite.getScaled(drawScale), (int) x, (int) y);
		}

		actionSet.update();
	}

	public SampleActor getAnotherCopy() {
		return sample.getSampleActor();
	}

	public ActionType getAction() {
		return currentAction;
	}

	public void setAction(ActionType currentAction) {
		this.currentAction = currentAction;
	}

	public boolean isFlipped() {
		return flipped;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}

	public float getDrawScale() {
		return drawScale;
	}

	public void setDrawScale(float drawScale) {
		this.drawScale = drawScale;
	}

	public boolean isDrawingShadow() {
		return drawShadow;
	}

	public void setDrawShadow(boolean drawShadow) {
		this.drawShadow = drawShadow;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

    public void setPosition(Vector2f position) {
        this.position = position;
    }

	public ActionSet getActionSet() {
		return actionSet;
	}
}
