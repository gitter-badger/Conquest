package tk.hes.conquest.gui.slot;

import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
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
        actor.setPosition(position);
    }

    @Override
    public void render(RenderContext c) {
        actor.render(c);
    }

    @Override
    public void update(Input input) {

    }

	public SampleActor getSampleActor() {
		return actor;
	}
}
