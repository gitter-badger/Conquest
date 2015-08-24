package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GBar;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.base.enums.GBarType;

/**
 * @author James
 */
public class GHeroInfo extends GComponent {

    private GImage backgroundImage;
    private GImage heroSlot;

    private GImage upgradesImage;

    private GBar healthBar;
    private GBar chargeBar;
    private GBar experienceBar;


    public GHeroInfo(Vector2f position, GComponent parent) {
        super(position, parent);
    }

    @Override
    public void init(RenderContext c) {
        backgroundImage = new GImage(Art.UI_HERO_BACKGROUND, new Vector2f(0, 0), this);
        heroSlot = new GImage(Art.UI_HERO_SLOT, new Vector2f(4, 2), this);
        upgradesImage = new GImage(Art.UI_HERO_UPGRADES, new Vector2f(29, 23), this);

        healthBar = new GBar(new Vector2f(28, 5), GBarType.RED, this);
        chargeBar = new GBar(new Vector2f(28, 11), GBarType.BLUE, this);
        experienceBar = new GBar(new Vector2f(28, 17), GBarType.GREEN, this);

        healthBar.setFilledPercent(90);
        chargeBar.setFilledPercent(70);
        experienceBar.setFilledPercent(20);
    }

    @Override
    public void render(RenderContext c) {
        backgroundImage.render(c);
        heroSlot.render(c);
        upgradesImage.render(c);

        healthBar.render(c);
        chargeBar.render(c);
        experienceBar.render(c);
    }

    @Override
    public void update() {

    }
}
