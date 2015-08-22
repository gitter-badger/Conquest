package tk.hes.conquest.graphics;

import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.BitmapLoader;
import me.deathjockey.tinypixel.graphics.Spritesheet;

import java.awt.*;

public class Art {

	public static final Bitmap UI_UNIT_DEPLOY_CURSOR = get("/ui_cursor.png", 2.0f);
	public static final Spritesheet FONT = getSpritesheet("/font.png", 8, 10);

	public static final Spritesheet UNIT_HUMAN_MELEE = getSpritesheet("/actors/human/hum_melee.png", 8, 8, 3.0f);
	public static final Spritesheet UNIT_HUMAN_CASTER = getSpritesheet("/actors/human/hum_caster.png", 8, 8, 3.0f);

	public static Bitmap get(String resource) {
		return get(resource, 1.0f);
	}

	public static Bitmap get(String resource, float scale) {
		Bitmap result = BitmapLoader.loadInternalBitmap(resource);
		return result.getScaled(scale);
	}

	public static Spritesheet getSpritesheet(String resource, int cellSize) {
		return getSpritesheet(resource, cellSize, cellSize);
	}

	public static Spritesheet getSpritesheet(String resource, int cellWidth, int cellHeight) {
		return getSpritesheet(resource, cellWidth, cellHeight, 1.0f);
	}

	public static Spritesheet getSpritesheet(String resource, int cellWidth, int cellHeight, float scale) {
		Spritesheet ss = BitmapLoader.loadInternalSpritesheet(resource, new Dimension(cellWidth, cellHeight),
				new Point(0, 0), 0, 0);
		if(scale != 1.0f)
			return ss.getScaled(scale);
		else
			return ss;
	}
}
