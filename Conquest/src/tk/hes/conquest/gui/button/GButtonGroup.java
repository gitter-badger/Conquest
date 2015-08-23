package tk.hes.conquest.gui.button;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GAlignment;
import tk.hes.conquest.gui.base.GComponent;

import java.util.ArrayList;

/**
 * A button group class which easily keeps buttons in alignment relative to a parent position.
 *
 * @author James Roberts
 */
public class GButtonGroup extends GComponent {

    //TODO: Selector button next to buttons for keyboard only use

    private GAlignment alignment;
    private ArrayList<GButton> buttons;
    private int buttonSpacing;

    public GButtonGroup(Vector2f position, int buttonSpacing, GAlignment alignment) {
        super(position);
        this.buttonSpacing = buttonSpacing;
        buttons = new ArrayList<>();
        this.alignment = alignment;
    }

    @Override
    public void init(RenderContext c) {
        if (buttons.size() <= 1)
            throw new IllegalArgumentException("GButton group must have at least two buttons!");
        for (int i = 0; i < buttons.size(); i++) {
            GButton button = buttons.get(i);
            buttons.get(i).setPosition(
                    (alignment == GAlignment.HORIZONTAL) ? ((int) button.getSize().getWidth() * i) + (buttonSpacing * i) : getPosition().getX(),
                    (alignment == GAlignment.VERTICAL) ? ((int) button.getSize().getHeight() * i) + (buttonSpacing * i) : getPosition().getY()
            );
            buttons.get(i).init(c);
        }


    }

    @Override
    public void render(RenderContext c) {
        for (GButton button : buttons) button.render(c);
    }

    @Override
    public void update() {
        buttons.forEach(GButton::update);
    }

    public void addButton(GButton button) {
        buttons.add(button);
    }
}
