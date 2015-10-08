package tk.hes.conquest.gui.slot;

import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
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
    private ArrayList<GActorSlot> actorSlots;
    private int selectIndex, offset;
    private GImage buttonLeft, buttonRight;
    private GLabel unitName;

    public GActorSlotBar(Vector2f position, Player player) {
        super(position);
        actorSlots = new ArrayList<>();
        this.selectIndex = 0;
        this.offset = 0;
        this.player = player;

        float cx = Art.UI_SLOTS.getCellSize().x / 2;
        float cy = Art.UI_SLOTS.getCellSize().y / 2;
        for (ActorType actor : player.getActorsOwned()) {
            SampleActor sample = player.getActorBuffer().get(actor).getSampleActor();
            Vector2f slotPosition = new Vector2f(cx, cy);
            addSlot(new GActorSlot(sample, slotPosition, this));
        }

        this.buttonLeft = new GImage(Art.UI_ARROW_LEFT, new Vector2f(-14, 12), this);
        this.buttonRight = new GImage(Art.UI_ARROW_RIGHT, new Vector2f(208, 12), this);

        unitName = new GLabel("", new Vector2f(0, 38), this);
    }

    @Override
    public void render(RenderContext c) {
        for (int i = offset; i < offset + 6; i++) {
            if (i < 0 || i > actorSlots.size() - 1) continue;
            actorSlots.get(i).render(c);
        }
        if (actorSlots.size() - offset > 6) buttonRight.render(c);
        if (offset > 0) buttonLeft.render(c);
        unitName.render(c);
    }

    @Override
    public void update(Input input) {
        selectIndex = player.getSelectedActorIndex();
        if (selectIndex < 0) {
            selectIndex = actorSlots.size() - 1;
            offset = selectIndex - 6 < 0 ? 0 : selectIndex - 6;
        }

		if (selectIndex > actorSlots.size() - 1) {
            selectIndex = 0;
            offset = 0;
        }
        if (selectIndex < offset + 1 && selectIndex != 0)
			offset--;

		if (selectIndex > offset + 4 && selectIndex != actorSlots.size() - 1)
			offset++;

		if (selectIndex == 0) {
			offset = 0;
		}

		if (selectIndex == actorSlots.size() - 1 && actorSlots.size() > 6) {
			offset = actorSlots.size() - 6;
		}

        for (int i = 0; i < actorSlots.size(); i++) {
            boolean selected = i == selectIndex;
            actorSlots.get(i).setState(selected ? GSlotState.SELECTED : GSlotState.ENABLED);
            actorSlots.get(i).getActorData().setAction(selected ? ActionType.MOVE : ActionType.STATIC);

            float disabledAmount = 100f - player.getActorCooldown(i) * 100;
            actorSlots.get(i).setDisabledAmount(disabledAmount);
            actorSlots.get(i).setDeployBarEnabled(disabledAmount == 0f);
        }
        unitName.setText(actorSlots.get(selectIndex).getActorData().getAttributes().name);
        updateButtonPosition();
    }

    private void updateButtonPosition() {
        for (int i = offset; i < offset + 6; i++) {
            if (i < 0 || i > actorSlots.size() - 1) continue;
            int x = (i - offset);
            actorSlots.get(i).setPosition(((x * 30) + (4 * x)), 0);
        }

    }

    public void addSlot(GActorSlot slot) {
        actorSlots.add(slot);
        updateButtonPosition();
    }


}
