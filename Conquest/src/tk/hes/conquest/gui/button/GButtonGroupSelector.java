package tk.hes.conquest.gui.button;

import me.nibby.pix.Bitmap;
import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.enums.GAlignment;
import tk.hes.conquest.gui.base.enums.GState;
import tk.hes.conquest.gui.listener.GButtonActionListener;

import java.awt.event.KeyEvent;

/**
 * A the GUI selector graphics for the GButtonGroup with keyboard selection
 *
 * @author James Roberts
 */
public class GButtonGroupSelector {

    public GButtonGroup group;
    public boolean isActive;
    private Bitmap selectorBitmap;
    private int selectedItem;

    public GButtonGroupSelector(GButtonGroup group) {
        isActive = true;
        this.group = group;
        selectorBitmap = Art.UI_CURSOR;
    }

    public void render(RenderContext c) {
        if (group.getAlignment() == GAlignment.VERTICAL) {
            c.renderBitmap(selectorBitmap, (int) group.getPosition().getX() - 26,
                    (int) group.getPosition().getY() - 3 + (group.getButtonSpacing() * selectedItem) + 22 * selectedItem);
            c.renderBitmap(selectorBitmap.getFlipped(false, true), (int) group.getPosition().getX() + 90,
                    (int) group.getPosition().getY() - 3 + (group.getButtonSpacing() * selectedItem) + 22 * selectedItem);
        }
    }

    public void caretUpdate(Input input) {
        if (group.getAlignment() != GAlignment.VERTICAL) return;

        for (int i = 0; i < group.getButtons().size(); i++) {
            GTextButton b = group.getButtons().get(i);
            if (b.getCurrentState() == GState.PRESSED) {
                this.selectedItem = i;
            }
        }

        if (input.isKeyPressed(KeyEvent.VK_W)) {
            this.selectedItem--;
        } else if (input.isKeyPressed(KeyEvent.VK_S)) {
            this.selectedItem++;
        } else if (input.isKeyPressed(KeyEvent.VK_ENTER)) {
            GTextButton button = group.getButtons().get(this.selectedItem);
            for (GButtonActionListener listener : button.getActionListeners())
                listener.actionPreformed(button);
        }
        if (this.selectedItem < 0) this.selectedItem = 0;
        if (this.selectedItem > group.getButtons().size() - 1) this.selectedItem = group.getButtons().size() - 1;
        group.getButtons().get(this.selectedItem).currentState = GState.PRESSED;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
