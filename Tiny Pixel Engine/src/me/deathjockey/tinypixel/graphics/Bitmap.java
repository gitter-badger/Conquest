package me.deathjockey.tinypixel.graphics;

import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Bitmap is a representation of a region of pixel data as derived from a BufferedImage.
 * Assets (images) are loaded in the form of BufferedImage before translated into an array
 * integer representation of its pixel data. The render context then calculates the suitable
 * co-ordinate on-screen to render these.
 *
 * @author Kevin Yang
 */
public class Bitmap {

	/** Original image representation of Bitmap */
	private BufferedImage image;

	private int[] pixels;
	private int width, height;

	protected Bitmap() {

	}

	/**
	 * Loads an instance from a provided input stream
	 *
	 * @param inputStream Data stream used to source a Bitmap.
	 * @throws IOException Thrown upon an error during loading Bitmap.
	 * 					   Likely due to bad resource path.
	 */
	protected Bitmap(InputStream inputStream) throws IOException {
		this(ImageIO.read(inputStream));
	}

	/**
	 * Generates an instance from a loaded BufferedImage.
	 *
	 * @param image A loaded BufferedImage to derive information from.
	 */
	protected Bitmap(BufferedImage image) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		pixels = new int[width  * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	}

	/**
	 * Makes a deep copy of another bitmap instance.
	 *
	 * @param bitmap Bitmap instance to clone
	 */
	protected Bitmap(@NotNull Bitmap bitmap) {
		this.image = bitmap.image;
		this.pixels = bitmap.pixels;
		this.width = bitmap.width;
		this.height = bitmap.height;
	}

	/**
	 * Used for wrapping parameters in a Bitmap instance.
	 *
	 * @param pixels Integer pixel color data
	 * @param width Width of bitmap
	 * @param height Height of bitmap
	 */
	protected Bitmap(int[] pixels, int width, int height) {
		this.pixels = pixels;
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, width, height, pixels, 0, width);
	}

	/**
	 * Returns a region of pixels within the bitmap. The parameters
	 * define the bounds of the region.
	 *
	 * @param x0 Start X
	 * @param y0 Start Y
	 * @param x1 End X
	 * @param y1 End Y
	 * @return
	 */
	public int[] getPixels(int x0, int y0, int x1, int y1) {
		if(x1 < x0 || y1 < y0)
			throw new IllegalArgumentException("x1, y1 cannot be less than x0, y0!");
		if(x1 > getWidth() || x0 < 0 || y1 > getHeight() || y0 < 0)
			throw new IllegalArgumentException(
					String.format("Region out of bounds!%n" +
									"%d,%d,%d,%d vs. spritesheet: %d,%d,%d,%d (x0,y0,x1,y1)",
							x0, y0, x1, y1, 0, 0, getWidth(), getHeight()));

		int rWidth = x1 - x0;
		int rHeight = y1 - y0;
		int[] region = new int[rWidth * rHeight];
		for(int xx = x0; xx < x1; xx++) {
			for(int yy = y0; yy < y1; yy++) {
				int rx = xx - x0, ry = yy - y0;
				region[ry * rWidth + rx] = pixels[yy * getWidth() + xx];
			}
		}

		return region;
	}

	/**
	 * Returns a bitmap that is a smaller portion of the original. This is
	 * similar to 'getPixels(int x0, int y0, int x1, int y1)' but the result
	 * is wrapped in a Bitmap class for simplified use in RenderContext.
	 *
	 * The parameters specified define the bounds for the sub-bitmap.
	 *
	 * @param x0 Start X
	 * @param y0 Start Y
	 * @param x1 End X
	 * @param y1 End Y
	 * @see RenderContext
	 * @return A bitmap that is a portion of the original
	 */
	public Bitmap getBitmapRegion(int x0, int y0, int x1, int y1) {
		return new Bitmap(getPixels(x0, y0, x1, y1), x1 - x0, y1 - y0);
	}

	/**
	 * Transforms the bitmap by flipping vertically or horizontally. A copy
	 * of the pixel data is generated and the original is not affected by
	 * this method.
	 *
	 * @param horizontal Flag for flipping horizontally
	 * @param vertical Flag for flipping vertically
	 * @return A bitmap that is flipped by provided parameters
	 */
	public Bitmap getFlipped(boolean horizontal, boolean vertical) {
		if(!horizontal && !vertical) return this;
		int[] data = new int[getPixels().length];
		if(horizontal && !vertical) {
			for(int x = 0; x < getWidth(); x++) {
				for(int y = getHeight() - 1; y >= 0; y--) {
					data[(getHeight() - y - 1) * getWidth() + x] = pixels[y * getWidth() + x];
				}
			}
		} else if(vertical && !horizontal) {
			for(int x = getWidth() - 1; x >= 0; x--) {
				for(int y = 0; y < getHeight(); y++) {
					data[y * getWidth() + (getWidth() - x - 1)] = pixels[y * getWidth() + x];
				}
			}
		} else {
			for (int i = pixels.length - 1; i > 0; i--) {
				data[pixels.length - i] = pixels[i];
			}
		}
		return new Bitmap(data, width, height);
	}

	/**
	 * Generates a scaled copy (enlarged or diminished) of the
	 * current bitmap data.
	 *
	 * @param scaleFactor Scale Factor to resize with
	 * @return A scaled version of the bitmap
	 */
	public Bitmap getScaled(float scaleFactor) {
		int newWidth = (int) (width * scaleFactor);
		int newHeight = (int) (height * scaleFactor);
		BufferedImage img = new BufferedImage((int) (width * scaleFactor), (int) (height * scaleFactor),
				image.getType());
		for(int x = 0; x < newWidth; x++) {
			for(int y = 0; y < newHeight; y++) {
				int ox = (int) (x / scaleFactor);
				int oy = (int) (y / scaleFactor);
				img.setRGB(x, y, image.getRGB(ox, oy));
			}
		}
		return new Bitmap(img);
	}

	/**
	 * Generates a scaled copy (enlarged or diminished) of the
	 * current bitmap data.
	 *
	 * @param width Width of scaled bitmap
	 * @param height Height of scaled bitmap
	 * @return A scaled version of the bitmap of given width and height.
	 */
	public Bitmap getScaled(int width, int height) {
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException("Width or height cannot be less than or equal to 0!");

		float wScale = (float) width / (float) getWidth();
		float hScale = (float) height / (float) getHeight();
		BufferedImage img = new BufferedImage(width, height, image.getType());
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int ox = (int) (x / wScale);
				int oy = (int) (y / hScale);
				img.setRGB(x, y, image.getRGB(ox, oy));
			}
		}
		return new Bitmap(img);
	}

	/**
	 * Supplies the pixel data for the bitmap
	 *
	 * @return Integer array of pixel data (DataBufferInt)
	 */
	public int[] getPixels() {
		return pixels;
	}

	/**
	 * Supplies the width of the bitmap
	 *
	 * @return Width of the bitmap
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Supplies the height of the bitmap
	 *
	 * @return Height of the bitmap
	 */
	public int getHeight() {
		return height;
	}

	public BufferedImage getImage() {
		return image;
	}
}
