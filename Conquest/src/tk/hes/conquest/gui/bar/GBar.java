package tk.hes.conquest.gui.bar;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.enums.GOrientation;

/**
 * @author James
 */
public abstract class GBar extends GComponent {

    protected float amountFilled = 0;
    protected boolean isFlipped;
    private GOrientation orientation;


    public GBar(Vector2f position, GComponent parent) {
        super(position, parent);
    }

    @Override
    public void init(RenderContext c) {
        this.orientation = GOrientation.HORIZONTAL;
        this.isFlipped = false;
        setFilledPercent(100);
    }

    public void setFilledPercent(float amount) {
        if (amount > 100f) amount = 100f;
        if (amount < 0f) amount = 0f;
        amount *= .01;
        this.amountFilled = (float) (amount * ((orientation == GOrientation.HORIZONTAL) ? getSize().getWidth() : getSize().getHeight()));
    }

    public void setOrientation(GOrientation orientation) {
        this.orientation = orientation;
    }

    public boolean isHorizontal() {
        return this.orientation == GOrientation.HORIZONTAL;
    }

}
