package tk.hes.conquest.gui.button;

import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;

/**
 * @author James
 */
public class GButton extends GAbstractButton {

    public GButton(Vector2f position) {
        this(position, null);
    }

    public GButton(Vector2f position, GComponent parent) {
        super(position, parent);
    }

    @Override
    public void init(RenderContext c) {
        super.init(c);
    }

    public void setButtonNormal(Bitmap buttonNormal) {
        this.buttonNormal = buttonNormal;
    }

    public void setButtonPressed(Bitmap buttonPressed) {
        this.buttonPressed = buttonPressed;
    }


}
