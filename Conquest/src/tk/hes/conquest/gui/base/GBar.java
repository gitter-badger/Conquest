package tk.hes.conquest.gui.base;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.enums.GBarOrigin;
import tk.hes.conquest.gui.base.enums.GBarType;

/**
 * @author James
 */
public class GBar extends GComponent {

    protected GImage barBackground, barOverlay;
    protected float filled;
    private GBarOrigin drawOrigin;
    private GBarType type;
    private boolean isFlipped;


    public GBar(Vector2f position, GBarType type) {
        this(position, type, null);
    }

    public GBar(Vector2f position, GBarType type, GComponent parent) {
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
            case CUSTOM:
            default:
                barBackground = null;
                barOverlay = null;
        }
    }

    @Override
    public void init(RenderContext c) {

    }

    @Override
    public void render(RenderContext c) {
        c.render(barBackground.getImage().getFlipped(false, isFlipped), (int) position.getX(), (int) position.getY());
        if (filled > 0) {

            //BITMAP REGION
            int a = (int) barOverlay.getSize().getWidth() - (int) filled;

            int x0 = (drawOrigin == GBarOrigin.LEFT) ? 0 : a;
            int x1 = (drawOrigin == GBarOrigin.LEFT) ? (int) filled : (int) barOverlay.getSize().getWidth();
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

    public void setDrawOrigin(GBarOrigin drawOrigin) {
        this.drawOrigin = drawOrigin;
    }

    public float getFilledPercent() {
        return filled;
    }

    public void setFilledPercent(float amount) {
        if (amount > 100) amount = 100;
        if (amount < 0) amount = 0;
        amount *= .01;
        this.filled = amount * (int) barBackground.getSize().getWidth();
    }

    public void setFlipped(boolean flipped) {
        this.isFlipped = flipped;
    }
}
