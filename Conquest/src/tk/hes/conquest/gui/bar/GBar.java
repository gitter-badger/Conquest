package tk.hes.conquest.gui.bar;

import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.enums.GBarOrigin;
import tk.hes.conquest.gui.base.enums.GOrientation;

/**
 * An abstract bar which can be filled to a given percentage.
 *
 * @author James
 */
public abstract class GBar extends GComponent {

    protected float amountFilled;
    protected GBarOrigin drawOrigin;
    protected boolean isFlipped;

    private GOrientation orientation;

    public GBar(Vector2f position) {
        this(position, null);
    }

    public GBar(Vector2f position, GComponent parent) {
        super(position, parent);
        this.orientation = GOrientation.HORIZONTAL;
        setFilledPercent(100);
    }


    public void setFilledPercent(float amount) {
        if (amount > 100) amount = 100;
        if (amount < 0) amount = 0;
        amount *= .01;
        this.amountFilled = (float) (amount * ((orientation == GOrientation.HORIZONTAL) ? getSize().getWidth() : getSize().getHeight()));
    }

    public void setFlipped(boolean flipped) {
        this.isFlipped = flipped;
    }

    public void setOrientation(GOrientation orientation) {
        this.orientation = orientation;
    }

    public float getAmountFilled() {
        return amountFilled;
    }

    public GBarOrigin getDrawOrigin() {
        return drawOrigin;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public boolean isHorizontal() {
        return this.orientation == GOrientation.HORIZONTAL;
    }
}
