package tk.hes.conquest.graphics;

import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.BitmapLoader;
import me.deathjockey.tinypixel.graphics.Spritesheet;
import tk.hes.conquest.actor.Actor;

import java.awt.*;

public class Art {

    // UI

    public static final Spritesheet FONT = getSpritesheet("/font.png", 8, 10);
    public static final Bitmap UI_CURSOR = get("/ui/ui_cursor.png", 2.0f);

    public static final Spritesheet UI_BIG_BUTTONS = getSpritesheet("/ui/ui_buttons.png", 48, 11, 2.0f);

    public static final Spritesheet DIALOG = getSpritesheet("/ui/ui_panel.png", 48, 48, 1.0f);
    public static final Spritesheet DIALOG_BUTTON = getSpritesheet("/provisional/ui_dialog_buttons.png", 6, 6);

    public static final Spritesheet BARS = getSpritesheet("/ui/ui_bars.png", 54, 4);
    public static final Bitmap DOMINANCE_BAR = get("/ui/ui_dominance_bar.png");

    public static final Spritesheet STATS_BUTTONS = getSpritesheet("/ui/stats/ui_stats_buttons.png", 24, 13);
    public static final Spritesheet STATS_BACKGROUND = getSpritesheet("/ui/stats/ui_stats_background.png", 24, 13);
    public static final Spritesheet STATS_BARS = getSpritesheet("/ui/stats/ui_stats_bars.png", 53, 14);

    public static final Spritesheet UI_HERO_ABILITY_SLOT = getSpritesheet("/ui/hero/ui_hero_ability_slot.png", 17, 30);
    public static final Bitmap UI_HERO_BACKGROUND = get("/ui/hero/ui_hero_background.png");
    public static final Bitmap UI_HERO_SLOT = get("/ui/hero/ui_hero_slot.png");

	public static final Bitmap[] UNIT_SHADOW = {
			get("/actors/shadow_8x8.png", 2.0f),
			get("/actors/shadow_8x10.png", 2.0f),
			get("/actors/shadow_10x12.png", 2.0f)
	};
	public static final Spritesheet UNIT_HUMAN_WARRIOR = getSpritesheet("/actors/human/hum_melee.png", 8, 8, Actor.SPRITE_SCALE);
	public static final Spritesheet UNIT_HUMAN_ARCHER = getSpritesheet("/actors/human/hum_ranger.png", 8, 8, Actor.SPRITE_SCALE);
	public static final Spritesheet UNIT_HUMAN_MAGE = getSpritesheet("/actors/human/hum_caster.png", 8, 8, Actor.SPRITE_SCALE);
	public static final Spritesheet UNIT_HUMAN_ASSASSIN = getSpritesheet("/actors/human/hum_scout.png", 8, 8, Actor.SPRITE_SCALE);
	public static final Spritesheet UNIT_HUMAN_PRIEST = getSpritesheet("/actors/human/hum_caster2.png", 8, 8, Actor.SPRITE_SCALE);
	public static final Spritesheet UNIT_HUMAN_MYSTIC = getSpritesheet("/actors/human/hum_special.png", 8, 8, Actor.SPRITE_SCALE);
    public static final Bitmap UI_STORE = get("/ui/store/ui_store.png");

	public static final Spritesheet PARTICLE_PROJECTILE_ARROW = getSpritesheet("/particles/arrows.png", 8, 8, Actor.SPRITE_SCALE);
	public static final Spritesheet PARTICLE_PROJECTILE_BOLT = getSpritesheet("/particles/bolts.png", 8, 8, Actor.SPRITE_SCALE);
	public static final Spritesheet PARTICLE_STATUS_EFFECTS = getSpritesheet("/particles/effects.png", 3, 3, Actor.SPRITE_SCALE);
    //SLOT
    public static final Spritesheet UI_SLOTS = getSpritesheet("/ui/slot/ui_slots.png", 30, 36);
    public static final Bitmap UI_ARROW_LEFT = get("/ui/slot/ui_arrow_left.png");
    public static final Bitmap UI_ARROW_RIGHT = get("/ui/slot/ui_arrow_right.png");

    private static Bitmap get(String resource) {
        return get(resource, 1.0f);
    }

    private static Bitmap get(String resource, float scale) {
        Bitmap result = BitmapLoader.loadInternalBitmap(resource);
        return result.getScaled(scale);
    }

    private static Spritesheet getSpritesheet(String resource, int cellSize) {
        return getSpritesheet(resource, cellSize, cellSize);
    }

    private static Spritesheet getSpritesheet(String resource, int cellWidth, int cellHeight) {
        return getSpritesheet(resource, cellWidth, cellHeight, 1.0f);
    }

    private static Spritesheet getSpritesheet(String resource, int cellWidth, int cellHeight, float scale) {
        Spritesheet ss = BitmapLoader.loadInternalSpritesheet(resource, new Dimension(cellWidth, cellHeight),
                new Point(0, 0), 0, 0);
        if (scale != 1.0f)
            return ss.getScaled(scale);
        else
            return ss;
    }
}
