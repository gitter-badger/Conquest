package tk.hes.conquest.gui.base;

import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.enums.GBarOrigin;
import tk.hes.conquest.gui.base.enums.GBarType;

/**
 * @author James
 */
public class GBar extends GComponent {

    private Bitmap barBackground;
    private Bitmap barOverlay;

    private GBarOrigin drawOrigin;
    private GBarType type;
    private float filled;
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
                barBackground = Art.BARS.getSprite(0, 0);
                barOverlay = Art.BARS.getSprite(1, 0);
                break;
            case BLUE:
                barBackground = Art.BARS.getSprite(0, 1);
                barOverlay = Art.BARS.getSprite(1, 1);
                break;
            case GREEN:
                barBackground = Art.BARS.getSprite(0, 2);
                barOverlay = Art.BARS.getSprite(1, 2);
                break;
            default:
                barBackground = Art.BARS.getSprite(0, 0);
                barOverlay = Art.BARS.getSprite(1, 0);
        }
    }

    @Override
    public void init(RenderContext c) {

    }

    @Override
    public void render(RenderContext c) {
        c.render(barBackground.getFlipped(false, isFlipped), (int) position.getX(), (int) position.getY());
        if (filled > 0) {

            //BITMAP REGION
            int a = barOverlay.getWidth() - (int) filled;

            int x0 = (drawOrigin == GBarOrigin.LEFT) ? 0 : a;
            int x1 = (drawOrigin == GBarOrigin.LEFT) ? (int) filled : barOverlay.getWidth();
            int y0 = 0;
            int y1 = 4;

            // LOCATION
            int xx = (int) position.getX() + ((drawOrigin == GBarOrigin.LEFT) ? 0 : a);
            int yy = (int) position.getY();

            c.render(barOverlay.getFlipped(false, isFlipped).getBitmapRegion(x0, y0, x1, y1), xx, yy);

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
        this.filled = amount * barBackground.getWidth();
    }

    public void setFlipped(boolean flipped) {
        this.isFlipped = flipped;
    }
}
