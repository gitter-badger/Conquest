package tk.hes.conquest.gui.game;

import me.nibby.pix.BitmapFont;
import me.nibby.pix.Input;
import me.nibby.pix.PixColor;
import me.nibby.pix.RenderContext;
import me.nibby.pix.util.Vector2f;
import tk.hes.conquest.ConquestGame;
import tk.hes.conquest.ConquestGameDesktopLauncher;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.bar.GStatBar;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.base.GLabel;
import tk.hes.conquest.gui.base.enums.GStatBarType;

/**
 * {@code GDominanceBar} is rendered at the top of the screen to display which side is winning.
 *
 * @author James Roberts
 */
public class GDominanceBar extends GStatBar {

    /**
     * Color bar background color.
     */
    private int opponentColor;
    private int topColor;

    public GLabel playerName;
    public GLabel opponentName;


    public GDominanceBar(Vector2f position, String playerName, String opponentName) {
        super(position, GStatBarType.CUSTOM);
		this.topColor = PixColor.toPixelInt(172, 50, 50, 255);
		this.opponentColor = PixColor.toPixelInt(0, 123, 255, 255);
        this.playerName = new GLabel(playerName, new Vector2f(0, 0), PixColor.YELLOW, this);
        this.opponentName = new GLabel(opponentName, new Vector2f(0, 0), PixColor.YELLOW, this);

        barBackground = new GImage(Art.DOMINANCE_BAR, new Vector2f(0, 0), this);
        this.setSize(barBackground.getSize());
        this.playerName.setPosition(new Vector2f(10, 4));

        int nameW = BitmapFont.getDefaultFont().widthOf(opponentName);
        this.opponentName.setPosition(new Vector2f(ConquestGame.WIDTH / ConquestGameDesktopLauncher.SCALE - nameW - 10, 4));
    }

    @Override
    public void render(RenderContext c) {
        barBackground.render(c);
        c.renderFilledRectangle(0, 0, c.getWidth(), 15, opponentColor);
        if (amountFilled > 0)
            c.renderFilledRectangle(0, 0, (int) amountFilled, 15, topColor);
        playerName.render(c);
        opponentName.render(c);
    }

    @Override
    public void update(Input input) {
    }

    public void setOpponentColor(int opponentColor) {
        this.opponentColor = opponentColor;
    }


    public void setTopColor(int topColor) {
        this.topColor = topColor;
    }
}
