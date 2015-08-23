package tk.hes.conquest.gui.button;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GAlignment;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GState;
import tk.hes.conquest.gui.listener.GButtonActionListener;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * A button group class which easily keeps buttons in alignment relative to a parent position.
 * Includes keyboard selection
 *
 * @author James Roberts
 */
public class GButtonGroup extends GComponent {

    public Bitmap selectorBitmap;
    private ArrayList<GTextButton> buttons;
    private GAlignment alignment;
    private int buttonSpacing;
    private int selectedItem;

    public GButtonGroup(Vector2f position, int buttonSpacing, GAlignment alignment) {
        super(position);
        buttons = new ArrayList<>();
        this.buttonSpacing = buttonSpacing;
        this.alignment = alignment;
        this.selectorBitmap = Art.UI_CURSOR;
        this.selectedItem = 0;
    }

    @Override
    public void init(RenderContext c) {
        if (buttons.size() <= 1)
            throw new IllegalArgumentException("GTextButton group must have at least two buttons!");
        for (int i = 0; i < buttons.size(); i++) {
            GTextButton button = buttons.get(i);
            //TODO: This alignment implementation may cause issues in the future.
            button.getPosition().add(new Vector2f(
                    (alignment == GAlignment.HORIZONTAL) ? ((int) button.getSize().getWidth() * i) + (buttonSpacing * i) : 0,
                    (alignment == GAlignment.VERTICAL) ? ((int) button.getSize().getHeight() * i) + (buttonSpacing * i) : 0));
            button.init(c);
        }


    }

    @Override
    public void render(RenderContext c) {
        for (GTextButton button : buttons) button.render(c);
        if (alignment == GAlignment.VERTICAL) {
            c.render(selectorBitmap, (int) getPosition().getX() - 26, (int) getPosition().getY() - 3 + (buttonSpacing * selectedItem) + 22 * selectedItem);
            c.render(selectorBitmap.getFlipped(false, true), (int) getPosition().getX() + 90, (int) getPosition().getY() - 3 + (buttonSpacing * selectedItem) + 22 * selectedItem);
        }

    }

    @Override
    public void update() {
        buttons.forEach(GTextButton::update);
        carrotUpdate();
    }

    public void carrotUpdate() {
        if (alignment != GAlignment.VERTICAL) return;

        for (int i = 0; i < buttons.size(); i++) {
            GTextButton b = buttons.get(i);
            if (b.getCurrentState() == GState.PRESSED) {
                this.selectedItem = i;
            }
        }

        if (Input.getKeyPressed(KeyEvent.VK_W)) {
            this.selectedItem--;
        } else if (Input.getKeyPressed(KeyEvent.VK_S)) {
            this.selectedItem++;
        } else if (Input.getKeyPressed(KeyEvent.VK_ENTER)) {
            GTextButton button = buttons.get(this.selectedItem);
            for (GButtonActionListener listener : button.getActionListeners())
                listener.actionPreformed(button);
        }
        if (this.selectedItem < 0) this.selectedItem = 0;
        if (this.selectedItem > buttons.size() - 1) this.selectedItem = buttons.size() - 1;
        buttons.get(this.selectedItem).currentState = GState.PRESSED;
    }

    public void addButton(GTextButton button) {
        buttons.add(button);
    }
}
