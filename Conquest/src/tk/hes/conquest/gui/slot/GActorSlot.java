package tk.hes.conquest.gui.slot;

import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.actor.Action;
import tk.hes.conquest.actor.ActionType;
import tk.hes.conquest.actor.SampleActor;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.bar.GBarImage;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.base.enums.GOrientation;

/**
 * @author James
 */
public class GActorSlot extends GComponent {

    private GImage disabled, enabled, selected;
    private GBarImage barMask;

    private SampleActor actorData;
    private GSlotState state;
	private GImage deployBar;

    public GActorSlot(SampleActor actor, Vector2f position, GComponent parent) {
        super(position, parent);
        state = GSlotState.ENABLED;
        this.actorData = actor;

        disabled = new GImage(Art.UI_SLOTS.getSprite(0, 0), new Vector2f(0, 0), this);
        enabled = new GImage(Art.UI_SLOTS.getSprite(1, 0), new Vector2f(0, 0), this);
        selected = new GImage(Art.UI_SLOTS.getSprite(2, 0), new Vector2f(0, 0), this);
        deployBar = new GImage(Art.UI_DEPLOY_BAR, new Vector2f(3, 27), this);

        barMask = new GBarImage(Art.UI_SLOTS.getSprite(0, 0), new Vector2f(0, 0), this);
        barMask.setOrientation(GOrientation.VERTICAL);
        barMask.setFilledPercent(0);
    }

    @Override
    public void render(RenderContext c) {
        if (state == GSlotState.DISABLED)
            disabled.render(c);
        else if (state == GSlotState.ENABLED)
            enabled.render(c);
        else if (state == GSlotState.SELECTED) {
			selected.render(c);
			deployBar.render(c);
		}
        barMask.render(c);

        Action.Frame frame = actorData.getActionSet().get(ActionType.STATIC).getCurrentFrame();
        int actorX = (int) (getPosition().getX() + getSize().getWidth() / 2 - frame.getBitmap().getWidth() / 2);
        int actorY = (int) (getPosition().getY() + getSize().getHeight() / 2 - frame.getBitmap().getHeight() / 2);
        actorData.render(c, actorX, actorY);
    }

    @Override
    public void update(Input input) {
    }

    public void setPosition(Vector2f position) {
        super.setPosition(position);

        Vector2f pos = this.getPosition();
        disabled.setPosition(pos);
        enabled.setPosition(pos);
        selected.setPosition(pos);
        barMask.setPosition(pos);
        deployBar.setPosition(new Vector2f(pos.getX() + 3, pos.getY() + 27));
        actorData.setPosition(pos);
    }

    public void setState(GSlotState state) {
        this.state = state;
    }

    public void setDisabledAmount(float amount) {
        this.barMask.setFilledPercent(amount);
    }

	public void setDeployBarEnabled(boolean deployBarEnabled) {
		deployBar.setVisible(deployBarEnabled);
	}

    public SampleActor getActorData() {
        return actorData;
    }
}
