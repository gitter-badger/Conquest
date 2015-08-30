package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.BitFont;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.Font;
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
		this.topColor = Colors.toInt(172, 50, 50, 255);
		this.opponentColor = Colors.toInt(0, 123, 255, 255);
        this.playerName = new GLabel(playerName, new Vector2f(0, 0), Colors.PURE_YELLOW, this);
        this.opponentName = new GLabel(opponentName, new Vector2f(0, 0), Colors.PURE_YELLOW, this);
    }

    @Override
    public void init(RenderContext c) {
        barBackground = new GImage(Art.DOMINANCE_BAR, new Vector2f(0, 0), this);
        this.setSize(barBackground.getSize());
        this.playerName.setPosition(new Vector2f(10, 4));
        this.playerName.init(c);

        int nameW = BitFont.widthOf(opponentName.getText(), c.getFont(Font.NORMAL));
        this.opponentName.setPosition(new Vector2f(c.getWidth() - nameW - 10, 4));
        this.opponentName.init(c);
        super.init(c);
    }

    @Override
    public void render(RenderContext c) {
        barBackground.render(c);
        c.fillRegion(0, 0, c.getWidth(), 15, opponentColor);
        if (amountFilled > 0)
            c.fillRegion(0, 0, (int) amountFilled, 15, topColor);
        playerName.render(c);
        opponentName.render(c);
    }

    @Override
    public void update() {
    }

    public void setOpponentColor(int opponentColor) {
        this.opponentColor = opponentColor;
    }


    public void setTopColor(int topColor) {
        this.topColor = topColor;
    }
}
