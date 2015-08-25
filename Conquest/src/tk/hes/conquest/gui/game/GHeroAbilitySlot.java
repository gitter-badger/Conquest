package tk.hes.conquest.gui.game;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;
import tk.hes.conquest.gui.game.enums.GHeroAbilityState;

/**
 * Slots for hero ability.
 *
 * @author James
 */
public class GHeroAbilitySlot extends GComponent {

    private GHeroAbilityState currentState;

    private GImage disabled, enabled, active;

    public GHeroAbilitySlot(Vector2f position) {
        this(position, null);
    }

    public GHeroAbilitySlot(Vector2f position, GComponent parent) {
        super(position, parent);
        currentState = GHeroAbilityState.DISABLED;
    }


    @Override
    public void init(RenderContext c) {
        disabled = new GImage(Art.UI_HERO_ABILITY_SLOT.getSprite(0, 0), new Vector2f(0, 0), this);
        enabled = new GImage(Art.UI_HERO_ABILITY_SLOT.getSprite(1, 0), new Vector2f(0, 0), this);
        active = new GImage(Art.UI_HERO_ABILITY_SLOT.getSprite(2, 0), new Vector2f(0, 0), this);
    }

    @Override
    public void render(RenderContext c) {
        if (currentState == GHeroAbilityState.ACTIVE)
            active.render(c);
        else if (currentState == GHeroAbilityState.ENABLED)
            enabled.render(c);
        else if (currentState == GHeroAbilityState.DISABLED)
            disabled.render(c);

    }

    @Override
    public void update() {

    }
}
