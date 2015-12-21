package tk.hes.conquest.gui.button;

import me.nibby.pix.Bitmap;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;

/**
 * This {@code GButton} class is the generic rectangular button class.
 *
 * @author James
 */
public class GButton extends GAbstractButton {

    public GButton(Vector2f position) {
        this(position, null);
    }

    public GButton(Vector2f position, GComponent parent) {
        super(position, parent);
    }

    public void setButtonNormal(Bitmap buttonNormal) {
        this.buttonNormal = buttonNormal;
        this.setSize(buttonNormal.getWidth(), buttonNormal.getHeight());
    }

    public void setButtonPressed(Bitmap buttonPressed) {
        this.buttonPressed = buttonPressed;
    }


}
