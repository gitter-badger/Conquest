package tk.hes.conquest.gui.base;

import me.nibby.pix.Input;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.gui.base.enums.GDialogType;
import tk.hes.conquest.gui.dialog.GDialogPart;

import java.awt.*;

/**
 * Base Dialog Box
 *
 * @author James Roberts
 */
public class GDialog extends GComponent {

    protected GDialogType type;
    protected GDialogPart bitmapParts;

    private int centerWidth;
    private int centerWidthAddition;
    private int centerHeight;
    private int centerHeightAddition;

    public GDialog(Vector2f position) {
        super(position);
        this.type = GDialogType.CUSTOM;
    }

    public GDialog(Vector2f position, Dimension size) {
        this(position, size, GDialogType.INFORMATION);
    }

    public GDialog(Vector2f position, Dimension sizeInPixels, GDialogType type) {
        super(position);
        if (sizeInPixels.getWidth() < 32 || sizeInPixels.getHeight() < 32)
            throw new IllegalArgumentException("The GDialog size must be at least 32x32");
        this.type = type;
        this.size = sizeInPixels;

        centerWidth = (int) (sizeInPixels.getWidth() / 16);
        centerWidthAddition = (int) sizeInPixels.getWidth() % 16;
        centerHeight = (int) (sizeInPixels.getHeight() / 16);
        centerHeightAddition = (int) (sizeInPixels.getHeight() % 16);

        bitmapParts = new GDialogPart(type, false);
    }

    @Override
    public void render(RenderContext c) {
        //TOP
        for (int i = 0; i < centerWidth; i++) {
            if (i == 0)
                c.renderBitmap(bitmapParts.getTopLeftCorner(), (int) position.getX(), (int) position.getY());
            else if (i == centerWidth - 1)
                c.renderBitmap(bitmapParts.getTopRightCorner(), (int) position.getX() + (16 * i) + centerWidthAddition, (int) position.getY());
            else
                c.renderBitmap(bitmapParts.getTopCenterEdge(), (int) position.getX() + (16 * i) + centerWidthAddition, (int) position.getY());
            if (centerWidthAddition != 0)
                c.renderBitmap(bitmapParts.getTopCenterEdge().getRegionAsBitmap(0, 0, centerWidthAddition, 16), (int) position.getX() + 16, (int) position.getY());
        }

        // MIDDLE
        for (int y = 1; y <= centerHeight - 2; y++) {
            int yy = (int) position.getY() + (16 * y);
            for (int x = 0; x < centerWidth; x++) {
                if (x == 0)
                    c.renderBitmap(bitmapParts.getMidLeftEdge(), (int) position.getX(), yy);
                else if (x == centerWidth - 1)
                    c.renderBitmap(bitmapParts.getMidRightEdge(), (int) position.getX() + (16 * x) + centerWidthAddition, yy);
                else
                    c.renderBitmap(bitmapParts.getCenter(), (int) position.getX() + (16 * x) + centerWidthAddition, yy);
                if (centerWidthAddition != 0)
                    c.renderBitmap(bitmapParts.getCenter().getRegionAsBitmap(0, 0, centerWidthAddition, 16), (int) position.getX() + 16, yy);
            }
        }

        // Bottom
        for (int x = 1; x <= centerWidth; x++) {
            int yy = (int) position.getY() + (16 * centerHeight) - 16;
            if (x == 1) {
                c.renderBitmap(bitmapParts.getLowLeftCorner(), (int) position.getX(), yy);
            } else if (x == centerWidth) {
                c.renderBitmap(bitmapParts.getLowRightCorner(), (int) position.getX() + (16 * x) - 16 + centerWidthAddition, yy);
            } else {
                c.renderBitmap(bitmapParts.getLowCenterEdge(), (int) position.getX() + (16 * x) - 16 + centerWidthAddition, yy);
            }
            if (centerWidthAddition != 0)
                c.renderBitmap(bitmapParts.getLowCenterEdge().getRegionAsBitmap(0, 0, centerWidthAddition, 16), (int) position.getX() + 16, yy);
        }

    }

    @Override
    public void update(Input input) {
    }

}
