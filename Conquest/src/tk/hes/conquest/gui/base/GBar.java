package tk.hes.conquest.gui.base;

import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.enums.GBarOrigin;

/**
 * An abstract bar which can be filled to a given percentage.
 *
 * @author James
 */
public abstract class GBar extends GComponent {

    protected GBarOrigin drawOrigin;
    protected float amountFilled;
    protected boolean isFlipped;

    public GBar(Vector2f position) {
        super(position);
    }

    public GBar(Vector2f position, GComponent parent) {
        super(position, parent);
    }


    public void setDrawOrigin(GBarOrigin drawOrigin) {
        this.drawOrigin = drawOrigin;
    }

    public float getAmountFilled() {
        return amountFilled;
    }

    public void setFilledPercent(float amount) {
        if (amount > 100) amount = 100;
        if (amount < 0) amount = 0;
        amount *= .01;
        this.amountFilled = amount * (int) getSize().getWidth();
    }

	public void setFlipped(boolean flipped) {
        this.isFlipped = flipped;
    }
}
