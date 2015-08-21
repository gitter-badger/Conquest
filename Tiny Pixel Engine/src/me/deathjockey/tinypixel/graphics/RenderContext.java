package me.deathjockey.tinypixel.graphics;

import com.sun.istack.internal.Nullable;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This is the heart of the rendering engine. A master BufferImage is created using the ARGB color model
 * with DataBufferInt. Each pixel is rendered onto the screen using the 32-bit integer color model.
 * An image is represented by an array of size width * height, and rendering is accomplished by the
 * transformation of pixel data from bitmap pixel array to the master image.
 *
 * The master image is then draw using Java2D Graphics object onto the canvas. Dimensions for the
 * render context is different to the dimension of the wrapper, but a resize of the wrapper should
 * update the size of the context. The size of the context refers to the dimensions of the master image.
 *
 * @author Kevin Yang
 */
public class RenderContext {

	/** Bitmap fonts registered by the user during load time */
	private HashMap<String, BitFont> installedBitFonts = new HashMap<>();

	/** The master BufferImage that holds all displayed pixel data */
	private BufferedImage image;

	/** Dimensions for the render context */
	private int width, height;

	/** Array of DataBufferInt pixel data linked to the master image */
	private int[] pixelData;

	/** Default color to fill the background with */
	private int clearColor = Colors.toInt(0, 0, 0, 255);

	private float defaultDrawScale = 1.0f;

	private float drawScale = 1f;


	/**
	 * Creates a render context with given dimensions.
	 *
	 * @param width Width of the context, in pixels
	 * @param height Height of the context, in pixels
	 */
	public RenderContext(int width, int height) {
		setSize(width, height);
	}

	/**
	 * Draws a given animation to the context.
	 *
	 * @param animation Animation to be drawn
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 */
	public void render(Animation animation, int x, int y) {
		render(animation, x, y, 1.0f);
	}

	/**
	 * Draws a given animation to the context with transparency.
	 *
	 * @param animation Animation to be drawn
	 * @param x x co-ordinate on screen
	 * @param y	y co-ordinate on screen
	 * @param alpha Transparency (0f - 1f) of the animation
	 */
	public void render(Animation animation, int x, int y, float alpha) {
		render(animation, x, y, alpha, Colors.toInt(0, 0, 0, 0));
	}

	/**
	 * Draws a given animatin to the context with transparency and color tint.
	 * @param animation Animation to be drawn
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 * @param alpha Transparency (0f - 1f) of the animation
	 * @param color Color tint of the animation
	 */
	public void render(Animation animation, int x, int y, float alpha, int color) {
		animation.render(this, x, y, alpha, color);
	}

	/**
	 * Draws a given bitmap to the context. Transfers the pixel data from
	 * the bitmap to the specified location on the context.
	 *
	 * @param bitmap Bitmap to be rendered
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 */
	public void render(Bitmap bitmap, int x, int y) {
		render(bitmap, x, y, 1.0f);
	}

	/**
	 * Draws a given bitmap to the context. In addition, the pixel alpha is
	 * modified to the one provided, giving transparency effects.
	 *
	 * @param bitmap Bitmap to be rendered
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 * @param alpha Alpha value desired (0f - 1f)
	 */
	public void render(Bitmap bitmap, int x, int y, float alpha) {
		render(((drawScale != 1f) ? bitmap.getScaled(drawScale).getPixels() : bitmap.getPixels()),
				(int) (bitmap.getWidth() * drawScale), (int) (bitmap.getHeight() * drawScale), x, y, alpha);

	}

	/**
	 * Draws a given bitmap to the context. In addition, the bitmap
	 * is tinted with a given color.
	 *
	 * @param bitmap Bitmap to be rendered
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 * @param alpha Transparency of the bitmap
	 * @param tintColor Color used to tint the bitmap
	 */
	public void render(Bitmap bitmap, int x, int y, float alpha, int tintColor) {
		render(((drawScale != 1f) ? bitmap.getScaled(drawScale).getPixels() : bitmap.getPixels()),
				(int) (bitmap.getWidth() * drawScale), (int) (bitmap.getHeight() * drawScale), x, y, alpha, tintColor);
	}

	/**
	 * Draws a region of pixel data to a given location in the context.
	 * Transfers the pixel data from the given pixel array to the location
	 * on the context.
	 *
	 * @param pixels An array of pixel data be drawn
	 * @param pw Width of the pixel region
	 * @param ph Height of the pixel region
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 */
	public void render(int[] pixels, int pw, int ph, int x, int y)  {
		render(pixels, pw, ph, x, y, 1.0f);
	}

	/**
	 * Draws a region of pixel data to a given location in the context.
	 * Transfers the pixel data from the given pixel array to the location
	 * on the context with alpha transparency.
	 *
	 * @param pixels An array of pixel data be drawn
	 * @param pw Width of the pixel region
	 * @param ph Height of the pixel region
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 * @param alpha Transparency of the pixel region
	 */
	public void render(int[] pixels, int pw, int ph, int x, int y, float alpha) {
		int x0 = x;
		int x1 = x + pw;
		int y0 = y;
		int y1 = y + ph;
		//check bounds
		if(x0 < 0) 				x0 = 0;
		if(x1 > getWidth())		x1 = getWidth();
		if(y0 < 0) 				y0 = 0;
		if(y1 > getHeight()) 	y1 = getHeight();

		//render
		for(int xx = x0; xx < x1; xx++) {
			for(int yy = y0; yy < y1; yy++) {
				int contextIndex = yy * getWidth() + xx;
				if(contextIndex < 0 || contextIndex > pixelData.length - 1)
					continue;
				int bmpIndex = (yy - y) * pw + (xx - x);
				int pxl = pixels[bmpIndex];
				int[] rgba = Colors.fromInt(pxl);
				float a = rgba[3];
				//check for valid alpha value (redundant alpha values are ignored)
				if(a > 0) {
					rgba[3] = (int) (255f * alpha);
					pxl = Colors.toInt(rgba);
					pixelData[contextIndex] = pxl;
				}
			}
		}
	}

	/**
	 * Draws a region of pixel data to a given location in the context.
	 * Each non-transparent (alpha > 0) pixels are tinted before drawn
	 * to the screen.
	 *
	 * @param pixels Pixel data to be drawn
	 * @param pw Width of the pixel region
	 * @param ph Height of the pixel region
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 * @param alpha Transparency of the bitmap
	 * @param tintColor Color tint to apply before rendering
	 */
	public void render(int[] pixels, int pw, int ph, int x, int y, float alpha, int tintColor) {
		/*
	 	 * TODO Tinting is not perfect yet, pixels only tint to the light side
 		 * 		A point-RGB average is needed for proper overlay effects
		 */
		int[] tintRGBA = Colors.fromInt(tintColor);
		float rTint = (float) tintRGBA[0] / 255f;
		float gTint = (float) tintRGBA[1] / 255f;
		float bTint = (float) tintRGBA[2] / 255f;
		float maskAlpha = (float) tintRGBA[3] / 255f;

		int[] pixelBuffer = pixels.clone();
		for(int i = 0; i < pixelBuffer.length; i++) {
			int[] rgba = Colors.fromInt(pixelBuffer[i]);
			int a = rgba[3];
			if(a == 0) continue;

			//blend colors (for tint)
			float red = rgba[0] + (255 - rgba[0]) * (rTint * maskAlpha);
			float green = rgba[1] + (255 - rgba[1]) * (gTint * maskAlpha);
			float blue = rgba[2] + (255 - rgba[2]) * (bTint * maskAlpha);
			int alpha2 = (int) alpha * 255;

			//color correction
			if(red > 255) red = 255;
			if(green > 255) green = 255;
			if(blue > 255) green = 255;
			if(alpha2 > 255) alpha = 255;

			pixelBuffer[i] = Colors.toInt((int) red, (int) green, (int) blue, alpha2);
		}
		render(pixelBuffer, pw, ph, x, y, alpha);
	}

	/**
	 * Draws a trimmed region (sub-image) from a given bitmap in the context.
	 *
	 * @param bitmap Bitmap to be rendered
	 * @param bx0 Start X (pixels before this are ignored)
	 * @param by0 Start Y (pixels before this are ignored)
	 * @param bx1 End X (pixels after this are ignored)
	 * @param by1 End Y (pixels after this are ignored)
	 * @param x x co-ordinate to be drawn
	 * @param y y co-ordinate to be drawn
	 */
	public void render(Bitmap bitmap, int bx0, int by0, int bx1, int by1, int x, int y) {
		if(bitmap instanceof Spritesheet)
			throw new IllegalArgumentException("bitmap cannot be spritesheet!");
		int[] pixels = bitmap.getPixels(bx0, by0, bx1, by1);
		render(pixels, bx1 - bx0, by1 - by0, x, y);
	}

	/**
	 * Fills a given rectangular region with a color.
	 *
	 * @param x0 Start X
	 * @param y0 Start Y
	 * @param x1 End X
	 * @param y1 End Y
	 * @param color Fill color (colored integer)
	 */
	public void fillRegion(int x0, int y0, int x1, int y1, int color) {
		if(x0 < 0) x0 = 0;
		if(x1 > getWidth()) x1 = getWidth();
		if(y0 < 0) y0 = 0;
		if(y1 > getHeight()) y1 = getHeight();

		for(int xx = x0; xx < x1; xx++) {
			for(int yy = y0; yy < y1; yy++) {
				pixelData[yy * getWidth() + xx] = color;
			}
		}
	}

	/**
	 * Changes the color of a pixel at a specific co-ordinate.
	 *
	 * @param color Color of the pixel to change to
	 * @param x x co-ordinate to be drawn
	 * @param y y co-ordinate to be drawn
	 */
	public void setPixel(int color, int x, int y) {
		try {
			pixelData[y * getWidth() + x] = color;
		} catch(ArrayIndexOutOfBoundsException ignored) { }
	}

	/**
	 * Installs a BitFont for displaying text to the player. Each font
	 * is stored with a unique 'key' that is used to access it later.
	 *
	 * @param key Key used to identify the font
	 * @param font BitFont instance that displays a font
	 * @see BitFont
	 */
	public void installFont(String key, BitFont font) {
		if(getFont(key) != null)
			throw new IllegalArgumentException("Another font with key '" + key + "' already exists!");

		font.setContext(this);
		installedBitFonts.put(key, font);
	}

	/**
	 * Supplies a BitFont that has already been installed previously.
	 *
	 * @param key The unique key used to recall the font
	 * @return BitFont instance used to render text
	 */
	@Nullable
	public BitFont getFont(String key) {
		return installedBitFonts.get(key);
	}

	/**
	 * Sets the background color upon clearing screen.
	 *
	 * @param clearColor Color integer
	 */
	public void setClearColor(int clearColor) {
		this.clearColor = clearColor;
	}

	/**
	 * Supplies the background color upon clearing screen
	 *
	 * @return Color integer
	 */
	public int getClearColor() {
		return clearColor;
	}

	/**
	 * Defines the context's size. All existing pixel data information will be
	 * lost.
	 *
	 * @param width Width of context
	 * @param height Height of context
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixelData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	/**
	 * Supplies the width of the context.
	 *
	 * @return Width of the context
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Supplies the height of the context.
	 *
	 * @return Height of the context
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Supplies the colored pixel data of the context.
	 *
	 * @return Pixel data of the context
	 */
	public int[] getPixelData() {
		return pixelData;
	}

	/**
	 * Supplies the original BufferedImage of the context
	 *
	 * @return BufferedImage of the context
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Resets all pixel data to the clear color.
	 *
	 */
	public void clearAll() {
		Arrays.fill(pixelData, clearColor);
	}

	public void setDrawScale(float scale) {
		this.drawScale = scale;
	}

	public void setDefaultDrawScale(float defaultDrawScale) {
		this.defaultDrawScale = defaultDrawScale;
	}

	public float getDefaultDrawScale() {
		return defaultDrawScale;
	}
}