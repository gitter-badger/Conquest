package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.bar.GBarColor;
import tk.hes.conquest.gui.base.GBar;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.base.GLabel;
import tk.hes.conquest.gui.button.GButton;

import java.awt.*;

/**
 * A part of GGameOverlay which displays player statistics.
 *
 * @author James Roberts
 */
public class GPlayerInfo extends GComponent {

    private GImage backgroundImage, chargeBar, moneyBar;
    private GButton playerButton, toolButton;

    private GBar chargeFillBar;
    private GLabel moneyLabel;

    public GPlayerInfo(Vector2f position) {
        super(position);
    }

    @Override
    public void init(RenderContext c) {
        this.backgroundImage = new GImage(Art.STATS_BACKGROUND, new Vector2f(0, 0), this);

        playerButton = new GButton(new Vector2f(2, 34), this);
        playerButton.setButtonNormal(Art.STATS_BUTTONS.getSprite(0, 0));
        playerButton.setButtonPressed(Art.STATS_BUTTONS.getSprite(1, 0));
        playerButton.init(c);

        toolButton = new GButton(new Vector2f(31, 34), this);
        toolButton.setButtonNormal(Art.STATS_BUTTONS.getSprite(0, 1));
        toolButton.setButtonPressed(Art.STATS_BUTTONS.getSprite(1, 1));
        toolButton.init(c);

        chargeBar = new GImage(Art.STATS_BARS.getSprite(0, 0), new Vector2f(2, 2), this);
        chargeBar.init(c);
        chargeFillBar = new GBarColor(new Vector2f(13, 2), new Dimension(39, 10), Colors.PURE_CYAN, chargeBar);
        chargeFillBar.init(c);
        chargeFillBar.setFilledPercent(0);

        moneyBar = new GImage(Art.STATS_BARS.getSprite(1, 0), new Vector2f(2, 18), this);
        moneyLabel = new GLabel("0", new Vector2f(15, 3), Colors.toInt(207, 214, 0, 255), moneyBar);
        moneyLabel.init(c);

    }

    @Override
    public void render(RenderContext c) {
        backgroundImage.render(c);

        chargeFillBar.render(c);
        chargeBar.render(c);


        moneyBar.render(c);
        moneyLabel.render(c);

        playerButton.render(c);
        toolButton.render(c);
    }


    @Override
    public void update() {
        playerButton.update();
        toolButton.update();
    }

    public void setMoneyAmount(int amount) {
        this.moneyLabel.setText(String.valueOf(amount));
    }

    public void setChargeAmount(float amount) {
        this.chargeFillBar.setFilledPercent(amount);
    }

    public GButton getPlayerButton() {
        return playerButton;
    }

    public GButton getToolButton() {
        return toolButton;
    }
}
