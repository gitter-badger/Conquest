package tk.hes.conquest.gui.slot;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.actor.Action;
import tk.hes.conquest.actor.ActionType;
import tk.hes.conquest.actor.SampleActor;
import tk.hes.conquest.game.ActorType;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.base.GLabel;

import java.util.ArrayList;

/**
 * @author James
 */
public class GActorSlotBar extends GComponent {

    private Player player;
    private ArrayList<GActorSlot> actors;
    private int currentSelectedIndex, offset;
    private GImage buttonLeft, buttonRight;
    private GLabel unitName;

    public GActorSlotBar(Vector2f position, Player player) {
        super(position);
        actors = new ArrayList<>();
        this.currentSelectedIndex = 0;
        this.offset = 0;
        this.player = player;

        float cx = Art.UI_SLOTS.getCellSize().width / 2;
        float cy = Art.UI_SLOTS.getCellSize().height / 2;
        for (ActorType actor : player.getActorsOwned()) {
            SampleActor sample = player.getActorBuffer().get(actor).getSampleActor();
            Action action = sample.getActionSet().get(sample.getAction());
            if (action != null) {
                Action.Frame frame = action.getCurrentFrame();
                Vector2f actorPosition = new Vector2f(cx - frame.getBitmap().getWidth() / 2 - frame.getxOffset() / 2,
                        cy - frame.getBitmap().getHeight() / 2 - frame.getyOffset() / 2);
                addSlot(new GActorSlot(sample, actorPosition, this));
            }
        }

    }

    @Override
    public void init(RenderContext c) {
        for (GActorSlot actor : actors) actor.init(c);


        this.buttonLeft = new GImage(Art.UI_ARROW_LEFT, new Vector2f(-14, 12), this);
        this.buttonLeft.init(c);

        this.buttonRight = new GImage(Art.UI_ARROW_RIGHT, new Vector2f(208, 12), this);
        this.buttonRight.init(c);

        unitName = new GLabel("", new Vector2f(0, 38), this);
        unitName.init(c);
    }

    @Override
    public void render(RenderContext c) {
        for (int i = offset; i < offset + 6; i++) {
            if (i < 0 || i > actors.size() - 1) continue;
            actors.get(i).render(c);
        }
        if (actors.size() - offset > 6) buttonRight.render(c);
        if (offset > 0) buttonLeft.render(c);
        unitName.render(c);
    }

    @Override
    public void update() {
        currentSelectedIndex = player.getSelectedActorIndex();
        if (currentSelectedIndex < 0) {
            currentSelectedIndex = actors.size() - 1;
            offset = currentSelectedIndex - 6 < 0 ? 0 : currentSelectedIndex - 6;
        }

		if (currentSelectedIndex > actors.size() - 1) {
            currentSelectedIndex = 0;
            offset = 0;
        }
        if (currentSelectedIndex < offset + 1 && currentSelectedIndex != 0)
			offset--;

		if (currentSelectedIndex > offset + 4 && currentSelectedIndex != actors.size() - 1)
			offset++;

		if (currentSelectedIndex == 0) {
			offset = 0;
		}

		if (currentSelectedIndex == actors.size() - 1 && actors.size() > 6) {
			offset = actors.size() - 6;
		}

        for (int i = 0; i < actors.size(); i++) {
            boolean selected = i == currentSelectedIndex;
            actors.get(i).setState(selected ? GSlotState.SELECTED : GSlotState.ENABLED);
            actors.get(i).getGActor().getSampleActor().setAction(selected ? ActionType.MOVE : ActionType.STATIC);
        }
        unitName.setText(actors.get(currentSelectedIndex).getGActor().getSampleActor().getAttributes().name);
        updateButtonPosition();

        // Must be ran last for unknown reasons...
        for (int i = 0; i < actors.size(); i++) {
			float disabledAmount = 100f - player.getActorCooldown(i) * 100;
			actors.get(i).setDisabledAmount(disabledAmount);
			actors.get(i).setDeployBarEnabled(disabledAmount == 0f);
		}
    }

    private void updateButtonPosition() {
        for (int i = offset; i < offset + 6; i++) {
            if (i < 0 || i > actors.size() - 1) continue;
            int x = (i - offset);
            actors.get(i).setPosition(((x * 30) + (4 * x)), 0);
            actors.get(i).init(RenderContext.getInstance());
        }

    }

    public void addSlot(GActorSlot actor) {
        int i = actors.size();
        actor.setPosition(((i * 30) + (4 * i)), 0);
        actor.init(null);
        actors.add(actor);
    }


}
