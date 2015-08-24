package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.button.GButton;

/**
 * @author James Roberts
 */
public class GPlayerInfo extends GComponent {

    private GImage backgroundImage, strengthBar, moneyBar;
    private GButton playerButton, toolButton;

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

        strengthBar = new GImage(Art.STATS_BARS.getSprite(0, 0), new Vector2f(2, 2), this);
        moneyBar = new GImage(Art.STATS_BARS.getSprite(1, 0), new Vector2f(2, 18), this);

    }

    @Override
    public void render(RenderContext c) {
        backgroundImage.render(c);
        strengthBar.render(c);
        moneyBar.render(c);
        playerButton.render(c);
        toolButton.render(c);
    }

    @Override
    public void update() {
        playerButton.update();
        toolButton.update();
    }
}
