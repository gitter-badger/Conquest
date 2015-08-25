package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;

/**
 * The game overlay which will be rendered on top of "tk.hes.conquest.game" gameplay.
 *
 * @author James Roberts
 */
public class GGameOverlay extends GComponent {

    private GDominanceBar dominanceBar;
    private GPlayerInfo playerInfo;
    private GHeroInfo heroInfo;

    public GGameOverlay(Vector2f position) {
        super(position);
    }

    @Override
    public void init(RenderContext c) {
        dominanceBar = new GDominanceBar(new Vector2f(0, 0));
        dominanceBar.init(c);
        dominanceBar.setFilledPercent(50);

        playerInfo = new GPlayerInfo(new Vector2f(0, (int) dominanceBar.getSize().getHeight() - 4));
        playerInfo.init(c);

        heroInfo = new GHeroInfo(new Vector2f(64, 16), this);
        heroInfo.init(c);
    }

    @Override
    public void update() {
        dominanceBar.update();
        playerInfo.update();
        heroInfo.update();
    }

    @Override
    public void render(RenderContext c) {
        dominanceBar.render(c);
        playerInfo.render(c);
        heroInfo.render(c);
    }


    public void setGold(int amount) {
        this.playerInfo.setMoneyAmount(amount);
    }

    public void setCharge(float amount, float maxCharge) {
        this.playerInfo.setChargeAmount((amount / maxCharge) * 100);
    }

    public void setDominance(int dominance) {
        this.dominanceBar.setFilledPercent(dominance);
    }

    public void setHeroHealth(int amount) {
        this.heroInfo.setHealth(amount);
    }

    public void setHeroCharge(int amount) {
        this.heroInfo.setCharge(amount);
    }

    public void setHeroExperience(float amount) {
        this.heroInfo.setExperience(amount);
    }

}
