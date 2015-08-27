package tk.hes.conquest.gui.slot;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @author James
 */
public class GActorSlotBar extends GComponent {

    private ArrayList<GActorSlot> actors;
    private int currentSelectedIndex;

    public GActorSlotBar(Vector2f position) {
        super(position);
        actors = new ArrayList<>();
        this.currentSelectedIndex = 0;
    }

    @Override
    public void init(RenderContext c) {
        for (GActorSlot actor : actors) actor.init(c);
    }

    @Override
    public void render(RenderContext c) {
        for (GActorSlot actor : actors) actor.render(c);

    }

    @Override
    public void update() {
        if (Input.getKeyPressed(KeyEvent.VK_LEFT)) currentSelectedIndex--;
        if (Input.getKeyPressed(KeyEvent.VK_RIGHT)) currentSelectedIndex++;
        if (currentSelectedIndex < 0) currentSelectedIndex = actors.size() - 1;
        if (currentSelectedIndex > actors.size() - 1) currentSelectedIndex = 0;
        for (GActorSlot slot : actors)
            slot.setState(GSlotState.ENABLED);
        actors.get(currentSelectedIndex).setState(GSlotState.SELECTED);
    }

    public void addSlot(GActorSlot actor) {
        int i = actors.size();
        actor.setPosition(((i * 30) + (4 * i)), 0);
        actor.init(null);
        actors.add(actor);
    }


}
