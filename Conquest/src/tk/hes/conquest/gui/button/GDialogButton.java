package tk.hes.conquest.gui.button;

import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.enums.GDialogButtonType;

/**
 * @author James
 */
public class GDialogButton extends GAbstractButton {

    private GDialogButtonType type;

    public GDialogButton(Vector2f position, GDialogButtonType type, GComponent parent) {
        super(position, parent);
        this.type = type;
        this.size.setSize(6, 6);
        updateDialogButton();
    }

    private void updateDialogButton() {
        switch (type) {
            case CROSS:
                this.buttonNormal = Art.DIALOG_BUTTON.getSprite(0, 0);
                this.buttonPressed = Art.DIALOG_BUTTON.getSprite(1, 0);
                break;
            case CHECK:
                this.buttonNormal = Art.DIALOG_BUTTON.getSprite(0, 1);
                this.buttonPressed = Art.DIALOG_BUTTON.getSprite(1, 1);
                break;
            case LEFT:
                this.buttonNormal = Art.DIALOG_BUTTON.getSprite(0, 2);
                this.buttonPressed = Art.DIALOG_BUTTON.getSprite(1, 2);
                break;
            case RIGHT:
                this.buttonNormal = Art.DIALOG_BUTTON.getSprite(0, 3);
                this.buttonPressed = Art.DIALOG_BUTTON.getSprite(1, 3);
                break;
            case ELLIPSE:
                this.buttonNormal = Art.DIALOG_BUTTON.getSprite(0, 4);
                this.buttonPressed = Art.DIALOG_BUTTON.getSprite(1, 4);
                break;
        }
    }

    public void setType(GDialogButtonType type) {
        this.type = type;
    }

}
