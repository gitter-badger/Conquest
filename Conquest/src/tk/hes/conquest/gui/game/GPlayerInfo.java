package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.bar.GBarColor;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.base.GLabel;
import tk.hes.conquest.gui.button.GAbstractButton;
import tk.hes.conquest.gui.button.GButton;
import tk.hes.conquest.gui.listener.GButtonActionListener;

import java.awt.*;

/**
 * A part of GGameOverlay which displays player statistics.
 *
 * @author James Roberts
 */
public class GPlayerInfo extends GComponent implements GButtonActionListener {

    private GImage backgroundImage, chargeBar, moneyBar;
    private GButton storeButton, toolButton;

    private GBarColor chargeFillBar;
    private GLabel moneyLabel;

    private GGameOverlay overlay;

    public GPlayerInfo(GGameOverlay overlay, Vector2f position) {
        super(position);
        this.overlay = overlay;
    }

    @Override
    public void init(RenderContext c) {
        this.backgroundImage = new GImage(Art.STATS_BACKGROUND, new Vector2f(0, 0), this);

        storeButton = new GButton(new Vector2f(2, 34), this);
        storeButton.setButtonNormal(Art.STATS_BUTTONS.getSprite(0, 0));
        storeButton.setButtonPressed(Art.STATS_BUTTONS.getSprite(1, 0));
        storeButton.init(c);
        storeButton.addActionListener(this);

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

        this.backgroundImage = new GImage(Art.STATS_BACKGROUND, new Vector2f(0, 0), this);
    }

    @Override
    public void render(RenderContext c) {
        backgroundImage.render(c);

        chargeFillBar.render(c);
        chargeBar.render(c);

        moneyBar.render(c);
        moneyLabel.render(c);

        storeButton.render(c);
        toolButton.render(c);
    }


    @Override
    public void update() {
        storeButton.update();
        toolButton.update();
    }

    public void setMoneyAmount(int amount) {
        this.moneyLabel.setText(String.valueOf(amount));
    }

    public void setChargePercentage(float amount) {
        this.chargeFillBar.setFilledPercent(amount);
        int color = (amount == 100f) ? Colors.PURE_YELLOW : Colors.PURE_CYAN;
        chargeFillBar.setFillColor(color);
    }

    public GButton getStoreButton() {
        return storeButton;
    }

    public GButton getToolButton() {
        return toolButton;
    }

    @Override
    public void actionPreformed(GAbstractButton button) {
        if (button == storeButton) {
            //TODO Check later if this is a good location for store creation
            GStore store = new GStore(new Vector2f(10, 100));
            store.init(RenderContext.getInstance());
            overlay.addDialogBox(store);
        }
    }
}
