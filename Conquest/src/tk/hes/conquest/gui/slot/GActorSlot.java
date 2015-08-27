package tk.hes.conquest.gui.slot;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.actor.SampleActor;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;

/**
 * @author James
 */
public class GActorSlot extends GComponent {

    private GImage disabled, enabled, selected;

    private SampleActor actorData;
    private GActor gActor;
    private GSlotState state;

    public GActorSlot(SampleActor actor, Vector2f position, GComponent parent) {
        super(position, parent);
        state = GSlotState.ENABLED;
        this.actorData = actor;
    }

    @Override
    public void init(RenderContext c) {
        disabled = new GImage(Art.UI_SLOTS.getSprite(0, 0), new Vector2f(0, 0), this);
        enabled = new GImage(Art.UI_SLOTS.getSprite(1, 0), new Vector2f(0, 0), this);
        selected = new GImage(Art.UI_SLOTS.getSprite(2, 0), new Vector2f(0, 0), this);
        this.gActor = new GActor(actorData, new Vector2f(7, 8), this);
        this.gActor.init(c);
    }

    @Override
    public void render(RenderContext c) {
        if (state == GSlotState.DISABLED)
            disabled.render(c);
        else if (state == GSlotState.ENABLED)
            enabled.render(c);
        else if (state == GSlotState.SELECTED)
            selected.render(c);
        gActor.render(c);
    }

    @Override
    public void update() {

    }

    public void setState(GSlotState state) {
        this.state = state;
    }
}
