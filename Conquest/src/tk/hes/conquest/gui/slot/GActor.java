package tk.hes.conquest.gui.slot;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.actor.SampleActor;
import tk.hes.conquest.gui.base.GComponent;

/**
 * @author James
 */
public class GActor extends GComponent {

    private SampleActor actor;

    public GActor(SampleActor actor, Vector2f position, GComponent parent) {
        super(position, parent);
        this.actor = actor;
    }

    @Override
    public void init(RenderContext c) {
        actor.setPosition(position);
    }

    @Override
    public void render(RenderContext c) {
        actor.render(c);
    }

    @Override
    public void update() {

    }
}
