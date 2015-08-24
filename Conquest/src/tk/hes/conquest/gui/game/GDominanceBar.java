package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GBar;
import tk.hes.conquest.gui.base.enums.GBarType;

/**
 * @author James
 */
public class GDominanceBar extends GBar {

    //TODO: Cleanup


    //private GImage dominanceBarImage;
    private int opponentColor;

    public GDominanceBar(Vector2f position) {
        super(position, GBarType.CUSTOM);
        this.opponentColor = Colors.toInt(100, 0, 0, 255);

    }

    @Override
    public void init(RenderContext c) {
        //dominanceBarImage = new GImage(Art.DOMINANCE_BAR, new Vector2f(0, 0), this);
        barBackground = Art.DOMINANCE_BAR;
        this.setSize(barBackground.getWidth(), barBackground.getHeight());
    }

    @Override
    public void render(RenderContext c) {
        //dominanceBarImage.render(c);
        c.render(barBackground, (int) position.getX(), (int) position.getY());
        c.fillRegion(0, 0, c.getWidth(), 15, opponentColor);
        if (filled > 0) {
            c.fillRegion(0, 0, (int) filled, 15, Colors.toInt(0, 0, 100, 255));
        }
    }

    @Override
    public void update() {
    }


    @Override
    public void setFilledPercent(float amount) {
        if (amount > 100) amount = 100;
        if (amount < 0) amount = 0;
        amount *= .01;
        this.filled = amount * barBackground.getWidth();
    }


}
