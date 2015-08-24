package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GBar;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.base.enums.GBarType;

/**
 * {@code GDominanceBar} is rendered at the top of the screen to display which side is winning.
 *
 * @author James Roberts
 */
public class GDominanceBar extends GBar {

    /**
     * Color bar background color.
     */
    private int opponentColor;

    public GDominanceBar(Vector2f position) {
        super(position, GBarType.CUSTOM);
        this.opponentColor = Colors.toInt(100, 0, 0, 255);
    }

    @Override
    public void init(RenderContext c) {
        barBackground = new GImage(Art.DOMINANCE_BAR, new Vector2f(0, 0), this);
        this.setSize(barBackground.getSize());
    }

    @Override
    public void render(RenderContext c) {
        barBackground.render(c);
        c.fillRegion(0, 0, c.getWidth(), 15, opponentColor);
        if (filled > 0) {
            c.fillRegion(0, 0, (int) filled, 15, Colors.toInt(0, 0, 100, 255));
        }
    }

    @Override
    public void update() {
    }


}
