package tk.hes.conquest.gui.button;

import me.nibby.pix.Bitmap;
import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.enums.GAlignment;

import java.util.ArrayList;

/**
 * A button group class which easily keeps buttons in alignment relative to a parent position.
 *
 * @author James Roberts
 */
public class GButtonGroup extends GComponent {

    public Bitmap selectorBitmap;
    private ArrayList<GTextButton> buttons;
    private GAlignment alignment;
    private int buttonSpacing;

    public GButtonGroup(Vector2f position, int buttonSpacing, GAlignment alignment) {
        super(position);
        buttons = new ArrayList<>();
        this.buttonSpacing = buttonSpacing;
        this.alignment = alignment;
        this.selectorBitmap = Art.UI_CURSOR;

        if (buttons.size() <= 1)
            throw new IllegalArgumentException("GTextButton group must have at least two buttons!");
        for (int i = 0; i < buttons.size(); i++) {
            GTextButton button = buttons.get(i);
            //TODO: This alignment implementation may cause issues in the future.
            button.getPosition().add(new Vector2f(
                    (alignment == GAlignment.HORIZONTAL) ? ((int) button.getSize().getWidth() * i) + (buttonSpacing * i) : 0,
                    (alignment == GAlignment.VERTICAL) ? ((int) button.getSize().getHeight() * i) + (buttonSpacing * i) : 0));
        }
    }

    @Override
    public void render(RenderContext c) {
        for (GTextButton button : buttons) button.render(c);

    }

    @Override
    public void update(Input input) {
        for (GTextButton button : buttons) {
            button.update(input);
        }
    }

    public void addButton(GTextButton button) {
        buttons.add(button);
    }

    public GAlignment getAlignment() {
        return alignment;
    }

    public int getButtonSpacing() {
        return buttonSpacing;
    }

    public ArrayList<GTextButton> getButtons() {
        return buttons;
    }
}
