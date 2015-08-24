package tk.hes.conquest.gui.button;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GLabel;
import tk.hes.conquest.gui.base.enums.GButtonColor;


/**
 *  It is built off {@code GAbstractButton},
 * which provides the basic mouse over detection capabilities.
 *
 * @author James Roberts
 */
public class GTextButton extends GButton {


    private GButtonColor buttonColor;
    private GLabel label;

    public GTextButton(String text, GComponent parent) {
        this(text, new Vector2f(0, 0), parent);
    }

    public GTextButton(String text, GButtonColor color, GComponent parent) {
        this(text, new Vector2f(0, 0), color, parent);
    }

    public GTextButton(String text, Vector2f position) {
        this(text, position, GButtonColor.BLUE);
    }

    public GTextButton(String text, Vector2f position, GButtonColor color) {
        this(text, position, color, null);
    }

    public GTextButton(String text, Vector2f position, GComponent parent) {
        this(text, position, GButtonColor.BLUE, parent);
    }

    public GTextButton(String text, Vector2f position, GButtonColor color, GComponent parent) {
        super(position, parent);
        this.label = new GLabel(text, new Vector2f(0, 0), this);
        this.buttonColor = color;
        this.size.setSize(96, 22);
        updateButtonBitmaps();
    }

    private void updateButtonBitmaps() {
        switch (buttonColor) {
            case GREY:
                buttonNormal = Art.UI_BIG_BUTTONS.getSprite(0, 0);
                buttonPressed = Art.UI_BIG_BUTTONS.getSprite(0, 1);
                break;
            default:
            case BLUE:
                buttonNormal = Art.UI_BIG_BUTTONS.getSprite(0, 2);
                buttonPressed = Art.UI_BIG_BUTTONS.getSprite(0, 3);
                break;
            case GREEN:
                buttonNormal = Art.UI_BIG_BUTTONS.getSprite(1, 0);
                buttonPressed = Art.UI_BIG_BUTTONS.getSprite(1, 1);
                break;
            case RED:
                buttonNormal = Art.UI_BIG_BUTTONS.getSprite(1, 2);
                buttonPressed = Art.UI_BIG_BUTTONS.getSprite(1, 3);
                break;
        }
    }

    @Override
    public void init(RenderContext c) {
        super.init(c);
        label.init(c);
        label.setPosition((float) (size.getWidth() / 2 - label.getSize().getWidth() / 2), 5);
    }

    @Override
    public void render(RenderContext c) {
        super.render(c);
        label.render(c);
    }

    @Override
    public void update() {
        super.update();
    }
}
