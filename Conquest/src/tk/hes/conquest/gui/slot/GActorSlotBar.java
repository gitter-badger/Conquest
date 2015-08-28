package tk.hes.conquest.gui.slot;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @author James
 */
public class GActorSlotBar extends GComponent {


    private ArrayList<GActorSlot> actors;
    private int currentSelectedIndex, offset;

    private GImage buttonLeft, buttonRight;

    public GActorSlotBar(Vector2f position) {
        super(position);
        actors = new ArrayList<>();
        this.currentSelectedIndex = 0;
        this.offset = 0;
    }

    @Override
    public void init(RenderContext c) {
        for (GActorSlot actor : actors) actor.init(c);


        this.buttonLeft = new GImage(Art.UI_ARROW_LEFT, new Vector2f(-14, 12), this);
        this.buttonLeft.init(c);

        this.buttonRight = new GImage(Art.UI_ARROW_RIGHT, new Vector2f(208, 12), this);
        this.buttonRight.init(c);
    }

    @Override
    public void render(RenderContext c) {
        for (int i = offset; i < offset + 6; i++) {
            if (i < 0 || i > actors.size() - 1) continue;
            actors.get(i).render(c);
        }
        if (actors.size() - offset > 6) buttonRight.render(c);
        if (offset > 0) buttonLeft.render(c);
    }

    @Override
    public void update() {
        if (Input.getKeyPressed(KeyEvent.VK_LEFT)) currentSelectedIndex--;
        if (Input.getKeyPressed(KeyEvent.VK_RIGHT)) currentSelectedIndex++;
        if (currentSelectedIndex < 0) {
            currentSelectedIndex = actors.size() - 1;
            offset = currentSelectedIndex - 6 < 0 ? 0 : currentSelectedIndex - 6;
        }
        if (currentSelectedIndex > actors.size() - 1) {
            currentSelectedIndex = 0;
            offset = 0;
        }
        if (currentSelectedIndex < offset + 1 && currentSelectedIndex != 0) offset--;
        if (currentSelectedIndex > offset + 4 && currentSelectedIndex != actors.size()) offset++;
        for (GActorSlot slot : actors)
            slot.setState(GSlotState.ENABLED);
        actors.get(currentSelectedIndex).setState(GSlotState.SELECTED);

        updateButtonPosition();
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
        System.out.println(i);
    }


}
