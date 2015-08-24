package tk.hes.conquest.gui.bar;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GBar;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.base.enums.GBarOrigin;
import tk.hes.conquest.gui.base.enums.GStatBarType;

/**
 * @author James
 */
public class GStatBar extends GBar {

    protected GImage barBackground, barOverlay;
    private GStatBarType type;

    public GStatBar(Vector2f position, GStatBarType type) {
        this(position, type, null);
    }

    public GStatBar(Vector2f position, GStatBarType type, GComponent parent) {
        super(position, parent);
        this.type = type;
        this.drawOrigin = GBarOrigin.LEFT;
        updateBarBitmaps();
    }

    public void updateBarBitmaps() {
        switch (type) {
            case RED:
                barBackground = new GImage(Art.BARS.getSprite(0, 0), new Vector2f(0, 0), this);
                barOverlay = new GImage(Art.BARS.getSprite(1, 0), new Vector2f(0, 0), this);
                break;
            case BLUE:
                barBackground = new GImage(Art.BARS.getSprite(0, 1), new Vector2f(0, 0), this);
                barOverlay = new GImage(Art.BARS.getSprite(1, 1), new Vector2f(0, 0), this);
                break;
            case GREEN:
                barBackground = new GImage(Art.BARS.getSprite(0, 2), new Vector2f(0, 0), this);
                barOverlay = new GImage(Art.BARS.getSprite(1, 2), new Vector2f(0, 0), this);
                break;
        }
    }


    @Override
    public void init(RenderContext c) {
        this.setSize(barBackground.getSize());
    }

    @Override
    public void render(RenderContext c) {
        c.render(barBackground.getImage().getFlipped(false, isFlipped), (int) position.getX(), (int) position.getY());
        if (amountFilled > 0) {

            //BITMAP REGION
            int a = (int) barOverlay.getSize().getWidth() - (int) amountFilled;

            int x0 = (drawOrigin == GBarOrigin.LEFT) ? 0 : a;
            int x1 = (drawOrigin == GBarOrigin.LEFT) ? (int) amountFilled : (int) barOverlay.getSize().getWidth();
            int y0 = 0;
            int y1 = 4;

            // LOCATION
            int xx = (int) position.getX() + ((drawOrigin == GBarOrigin.LEFT) ? 0 : a);
            int yy = (int) position.getY();

            c.render(barOverlay.getImage().getFlipped(false, isFlipped).getBitmapRegion(x0, y0, x1, y1), xx, yy);
        }
    }

    @Override
    public void update() {

    }
}

