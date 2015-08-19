package me.deathjockey.tinypixel.graphics;

import me.deathjockey.tinypixel.TinyPixelGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Utility class for Bitmap loading.
 *
 * @author Kevin Yang
 */
public class BitmapLoader {

	public static Bitmap loadInternalBitmap(String resource) {
		try {
			return new Bitmap(TinyPixelGame.class.getResourceAsStream(resource));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap loadBitmap(Path resource) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(resource.toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Bitmap(image);
	}

	public static Spritesheet loadInternalSpritesheet(String resource, int cellSize) {
		return loadInternalSpritesheet(resource, new Dimension(cellSize, cellSize));
	}

	public static Spritesheet loadInternalSpritesheet(String resource, Dimension cellSize) {
		return loadInternalSpritesheet(resource, cellSize, new Point(0, 0), 0, 0);
	}

	public static Spritesheet loadInternalSpritesheet(String resource, Dimension cellSize, Point offset, int vertGap, int horizGap) {
		Bitmap bigBitmap = loadInternalBitmap(resource);
		return new Spritesheet(bigBitmap, cellSize, offset, vertGap, horizGap);
	}

	public static Spritesheet loadSpritesheet(Path resource, int cellSize) {
		return loadSpritesheet(resource, new Dimension(cellSize, cellSize));
	}

	public static Spritesheet loadSpritesheet(Path resource, Dimension cellSize) {
		return loadSpritesheet(resource, cellSize, new Point(0, 0), 0, 0);
	}

	public static Spritesheet loadSpritesheet(Path resource, Dimension cellSize, Point offset, int vertGap, int horizGap) {
		Bitmap bigBitmap = loadBitmap(resource);
		return new Spritesheet(bigBitmap, cellSize, offset, vertGap, horizGap);
	}
}
