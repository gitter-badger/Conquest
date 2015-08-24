package tk.hes.conquest.gui.base.dialog;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;

import java.awt.*;

/**
 * Base Dialog Box
 *
 * @author James Roberts
 */
public class GDialog extends GComponent {

    private GDialogType type;
    private GDialogPart part;

    private int centerWidth;
    private int centerWidthAddition;

    private int centerHeight;
    private int centerHeightAddition;

    public GDialog(Vector2f position, Dimension size) {
        this(position, size, GDialogType.INFORMATION);
    }

    public GDialog(Vector2f position, Dimension sizeInPixels, GDialogType type) {
        super(position);
        if (sizeInPixels.getWidth() < 24 || sizeInPixels.getHeight() < 24)
            throw new IllegalArgumentException("The GDialog size must be at least 24x24");
        this.type = type;
        this.size = sizeInPixels;

        // TODO: Below may be equal to 0 depending on "Dimension sizeInPixels," which will cause an error.
        centerWidth = (int) (sizeInPixels.getWidth() / 16);
        centerWidthAddition = (int) sizeInPixels.getWidth() % 16;
        centerHeight = (int) (sizeInPixels.getHeight() / 16);
        centerHeightAddition = (int) (sizeInPixels.getHeight() % 16);
    }

    @Override
    public void init(RenderContext c) {
        part = new GDialogPart(type);
    }

    @Override
    public void render(RenderContext c) {
        //TOP
        for (int i = 0; i < centerWidth; i++) {
            if (i == 0)
                c.render(part.getTopLeftCorner(), (int) position.getX(), (int) position.getY());
            else if (i == centerWidth - 1)
                c.render(part.getTopRightCorner(), (int) position.getX() + (16 * i) + centerWidthAddition, (int) position.getY());
            else
                c.render(part.getTopCenterEdge(), (int) position.getX() + (16 * i) + centerWidthAddition, (int) position.getY());
            c.render(part.getTopCenterEdge().getBitmapRegion(0, 0, centerWidthAddition, 16), (int) position.getX() + 16, (int) position.getY());
        }

        // MIDDLE
        for (int y = 1; y <= centerHeight - 2; y++) {
            int yy = (int) position.getY() + (16 * y);
            for (int x = 0; x < centerWidth; x++) {
                if (x == 0)
                    c.render(part.getMidLeftEdge(), (int) position.getX(), yy);
                else if (x == centerWidth - 1)
                    c.render(part.getMidRightEdge(), (int) position.getX() + (16 * x) + centerWidthAddition, yy);
                else
                    c.render(part.getCenter(), (int) position.getX() + (16 * x) + centerWidthAddition, yy);
                c.render(part.getCenter().getBitmapRegion(0, 0, centerWidthAddition, 16), (int) position.getX() + 16, yy);
            }
        }

        // Bottom
        for (int x = 1; x <= centerWidth; x++) {
            int yy = (int) position.getY() + (16 * centerHeight) - 16;
            if (x == 1) {
                c.render(part.getLowLeftCorner(), (int) position.getX(), yy);
            } else if (x == centerWidth) {
                c.render(part.getLowRightCorner(), (int) position.getX() + (16 * x) - 16 + centerWidthAddition, yy);
            } else {
                c.render(part.getLowCenterEdge(), (int) position.getX() + (16 * x) - 16 + centerWidthAddition, yy);
            }
            c.render(part.getLowCenterEdge().getBitmapRegion(0, 0, centerWidthAddition, 16), (int) position.getX() + 16, yy);
        }

    }

    @Override
    public void update() {
        //System.out.println(Input.getCursorPosition().toString());
    }
}
