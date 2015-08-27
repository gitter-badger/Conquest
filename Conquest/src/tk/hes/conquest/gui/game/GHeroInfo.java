package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.bar.GBar;
import tk.hes.conquest.gui.bar.GStatBar;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.base.enums.GStatBarType;

/**
 * A part of GGameOverlay which display the hero statistics.
 *
 * @author James Roberts
 */
public class GHeroInfo extends GComponent {

    private GImage backgroundImage;
    private GImage heroSlot;
    private GHeroAbilitySlot slotOne, slotTwo, slotThree;

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

        slotOne = new GHeroAbilitySlot(new Vector2f(29, 23), this);
        slotTwo = new GHeroAbilitySlot(new Vector2f(47, 23), this);
        slotThree = new GHeroAbilitySlot(new Vector2f(65, 23), this);

        slotOne.init(c);
        slotTwo.init(c);
        slotThree.init(c);

        healthBar = new GStatBar(new Vector2f(28, 5), GStatBarType.RED, this);
        chargeBar = new GStatBar(new Vector2f(28, 11), GStatBarType.BLUE, this);
        experienceBar = new GStatBar(new Vector2f(28, 17), GStatBarType.GREEN, this);

        healthBar.init(c);
        chargeBar.init(c);
        experienceBar.init(c);

        healthBar.setFilledPercent(90);
        chargeBar.setFilledPercent(70);
        experienceBar.setFilledPercent(20);
    }

    @Override
    public void render(RenderContext c) {
        backgroundImage.render(c);
        heroSlot.render(c);

        slotOne.render(c);
        slotTwo.render(c);
        slotThree.render(c);


        healthBar.render(c);
        chargeBar.render(c);
        experienceBar.render(c);
    }

    @Override
    public void update() {
    }

    public void setExperience(float amount) {
        this.experienceBar.setFilledPercent(amount);
    }

    public void setCharge(float amount) {
        this.chargeBar.setFilledPercent(amount);
    }

    public void setHealth(float amount) {
        this.healthBar.setFilledPercent(amount);
    }
}
