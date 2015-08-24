package tk.hes.conquest.gui.dialog;

import me.deathjockey.tinypixel.graphics.Bitmap;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.enums.GDialogType;

/**
 * Parts of a Dialog Box. This class will simplify the extraction of the squares for each dialog box.
 *
 * @author James Roberts
 */
public class GDialogPart {

    private Bitmap INFORMATION = Art.DIALOG.getSprite(0, 0);
    private Bitmap SUCCESS = Art.DIALOG.getSprite(0, 1);
    private Bitmap ERROR = Art.DIALOG.getSprite(0, 2);
    private Bitmap WARNING = Art.DIALOG.getSprite(0, 3);

    private Bitmap INFORMATION_POPUP = Art.DIALOG.getSprite(1, 0);
    private Bitmap SUCCESS_POPUP = Art.DIALOG.getSprite(1, 1);
    private Bitmap ERROR_POPUP = Art.DIALOG.getSprite(1, 2);
    private Bitmap WARNING_POPUP = Art.DIALOG.getSprite(1, 3);

    private Bitmap topLeftCorner;
    private Bitmap topCenterEdge;
    private Bitmap topRightCorner;

    private Bitmap midLeftEdge;
    private Bitmap center;
    private Bitmap midRightEdge;

    private Bitmap lowLeftCorner;
    private Bitmap lowCenterEdge;
    private Bitmap lowRightCorner;

    private GDialogType type;
    private boolean isTitleDialog;

    public GDialogPart(GDialogType type, boolean isTitleDialog) {
        this.type = type;
        this.isTitleDialog = isTitleDialog;
        genTypeInfo();
    }

    private void genTypeInfo() {
        Bitmap info = !isTitleDialog ? getBitmapOfType(type) : getBitmapOfTitleType(type);
        this.topLeftCorner = info.getBitmapRegion(0, 0, 16, 16);
        this.topCenterEdge = info.getBitmapRegion(16, 0, 32, 16);
        this.topRightCorner = info.getBitmapRegion(32, 0, 48, 16);

        this.midLeftEdge = info.getBitmapRegion(0, 16, 16, 32);
        this.center = info.getBitmapRegion(16, 16, 32, 32);
        this.midRightEdge = info.getBitmapRegion(32, 16, 48, 32);

        this.lowLeftCorner = info.getBitmapRegion(0, 32, 16, 48);
        this.lowCenterEdge = info.getBitmapRegion(16, 32, 32, 48);
        this.lowRightCorner = info.getBitmapRegion(32, 32, 48, 48);
    }

    private Bitmap getBitmapOfType(GDialogType type) {
        switch (type) {
            case INFORMATION:
                return this.INFORMATION;
            case SUCCESS:
                return this.SUCCESS;
            case ERROR:
                return this.ERROR;
            case WARNING:
                return this.WARNING;
            default:
                return this.INFORMATION;
        }
    }

    private Bitmap getBitmapOfTitleType(GDialogType type) {
        switch (type) {
            case INFORMATION:
                return this.INFORMATION_POPUP;
            case SUCCESS:
                return this.SUCCESS_POPUP;
            case ERROR:
                return this.ERROR_POPUP;
            case WARNING:
                return this.WARNING_POPUP;
            default:
                return this.INFORMATION_POPUP;
        }
    }


    public Bitmap getTopLeftCorner() {
        return topLeftCorner;
    }

    public Bitmap getTopCenterEdge() {
        return topCenterEdge;
    }

    public Bitmap getTopRightCorner() {
        return topRightCorner;
    }

    public Bitmap getMidLeftEdge() {
        return midLeftEdge;
    }

    public Bitmap getCenter() {
        return center;
    }

    public Bitmap getMidRightEdge() {
        return midRightEdge;
    }

    public Bitmap getLowLeftCorner() {
        return lowLeftCorner;
    }

    public Bitmap getLowCenterEdge() {
        return lowCenterEdge;
    }

    public Bitmap getLowRightCorner() {
        return lowRightCorner;
    }

    public void setIsPopup(boolean isPopup) {
        this.isTitleDialog = isPopup;
    }
}
