package me.deathjockey.tinypixel.graphics;

import com.sun.istack.internal.Nullable;

import java.awt.*;

/**
 * A pre-allocated spritesheet representation. Spritesheets are treated as
 * a whole Bitmap with functions to locate specific sprites in cells.
 *
 * The equivalent to using a Spritesheet is Bitmap[][].
 *
 * @author Kevin Yang
 */
public class Spritesheet extends Bitmap {

	/** Horizontal strip orientation */
	public static final int ORIENTATION_VERTICAL = 0;

	/** Vertical strip orientation */
	public static final int ORIENTATION_HORIZONTAL = 1;


	/** Size of each sprite cell (assuming they are constant) */
	private Dimension cellSize;

	/** Size of pixel gap between cells (0 if none) */
	private int verticalGapSize = 0, horizontalGapSize = 0;

	/** Initial offset to grab sprite from, this is the first co-ordinate for sprite poisition calculations */
	private Point startOffset = new Point(0, 0);

	/** Sorted cell sprites are stored here */
	private Bitmap[][] cellSprites;

	/** Scaling **/
	private float scale = 1f;

	/**
	 * Instantiates a spritesheet object from a given Bitmap object (the main image) with spritsheet
	 * parameters.
	 *
	 * @param bitmap The big image to be used as spritesheet
	 * @param cellSize Width and height of each sprite in a cell (Assuming cells are same in size)
	 * @param startOffset Starting position to calculate sprite position (in pixels)
	 * @param vertGap Vertical pixel gap size (0 if none)
	 * @param horizGap Horizontal pixel gap size (0 if none)
	 */
	protected Spritesheet(Bitmap bitmap, Dimension cellSize, Point startOffset, int vertGap, int horizGap) {
		super(bitmap);
		this.cellSize = cellSize;
		this.verticalGapSize = vertGap;
		this.horizontalGapSize = horizGap;
		this.startOffset = startOffset;

		int ox = startOffset.x;
		int oy = startOffset.y;
		int w = cellSize.width;
		int h = cellSize.height;
		int ww = bitmap.getWidth() / w;
		int hh = bitmap.getHeight() / h;
		cellSprites = new Bitmap[ww][hh];
		for(int x = 0; x < ww; x++) {
			for(int y = 0; y < hh; y++) {
				//Map cells into a grid for future reference
				int[]  cellBmpPixels = bitmap.getPixels(ox + x * (w + vertGap), oy + y * (h + horizGap),
														(x + 1) * w, (y + 1) * h);
				cellSprites[x][y] = new Bitmap(cellBmpPixels, w, h);
			}
		}
	}

	@Override
	public Spritesheet getScaled(float scale) {
		Spritesheet s = new Spritesheet(getScaled((int) (getWidth() * scale), (int) (getHeight() * scale)),
				new Dimension((int) (cellSize.width * scale), (int) (cellSize.height * scale)),
				startOffset, verticalGapSize, horizontalGapSize);
		s.scale = scale;
		return s;
	}

	/**
	 * Generates a series of timed action.
	 * Generates a series of timed action.
	 *
	 * @param time Time in milliseconds to be applied to all generated frames
	 * @param spriteLoc Location of the sprites (in terms of cells)
	 * @return Generated action with specified cells and provided time for all frames.
	 */
	public Animation generateAnimation(int time, Point ... spriteLoc) {
		Bitmap[] sprites = new Bitmap[spriteLoc.length];
		int i = 0;
		for(Point p : spriteLoc) {
			Bitmap sprite = getSprite(p.x, p.y);
			sprites[i] = sprite;
			i++;
		}
		return new Animation(sprites, time);
	}

	/**
	 * Generates a series of timed action.
	 *
	 * Note that the length of the time array must be the same as spriteLoc array.
	 *
	 * @param time Time in milliseconds to be applied for each frame. Size should be equal to <strong>spriteLoc</strong>
	 * @param spriteLoc Location of the sprites (in terms of cells)
	 * @return Generated action with specified cells and provided time for each frame.
	 */
	public Animation generateAnimation(int[] time, Point[] spriteLoc) {
		if(time.length != spriteLoc.length)
			throw new IllegalArgumentException("Frame time array size should have the same length as spriteLoc array size!");
		Bitmap[] sprites = new Bitmap[spriteLoc.length];
		int i = 0;
		for(Point p : spriteLoc) {
			Bitmap sprite = getSprite(p.x, p.y);
			sprites[i] = sprite;
			i++;
		}
		return new Animation(sprites, time);
	}

	/**
	 * Generates a series of timed action, assuming the spritesheet is either a vertical
	 * or horizontal strip (i.e. 1 in width or height) and stores frames for an action.
	 *
	 * @param sequenceOrientation Orientation of the spritesheet
	 * @param time Time in milliseconds to be applied to every frame
	 * @return Generated action with specified cells and provided time for every frame.
	 */
	@Nullable
	public Animation generateAnimation(int sequenceOrientation, int time) {
		Bitmap[] sprites;
		Animation animation = null;
		switch(sequenceOrientation) {
			case ORIENTATION_HORIZONTAL:
				sprites = new Bitmap[cellSprites.length];
				for(int i = 0; i < cellSprites.length; i++)
					sprites[i] = cellSprites[i][0];
				animation = new Animation(sprites, time);
				return animation;
			case ORIENTATION_VERTICAL:
				sprites = new Bitmap[cellSprites[0].length];
				for(int i = 0; i < sprites.length; i++)
					sprites[i] = cellSprites[0][i];
				animation = new Animation(sprites, time);
				return animation;
			default:
				return null;
		}
	}

	/**
	 * Generates a series of timed action, assuming the spritesheet is either a vertical
	 * or horizontal strip (i.e. 1 in width or height) and stores frames for an action.
	 *
	 * @param sequenceOrientation Orientation of the spritesheet
	 * @param times Times in milliseconds to be applied to each frame
	 * @return Generated action with specified cells and provided time for each frame.
	 */
	@Nullable
	public Animation generateAnimation(int sequenceOrientation, int[] times) {
		Bitmap[] sprites;
		Animation animation = null;
		switch(sequenceOrientation) {
			case ORIENTATION_HORIZONTAL:
				sprites = new Bitmap[cellSprites.length];
				for(int i = 0; i < cellSprites.length; i++)
					sprites[i] = cellSprites[i][0];
				animation = new Animation(sprites, times);
				return animation;
			case ORIENTATION_VERTICAL:
				sprites = new Bitmap[cellSprites[0].length];
				for(int i = 0; i < sprites.length; i++)
					sprites[i] = cellSprites[0][i];
				animation = new Animation(sprites, times);
				return animation;
			default:
				return null;
		}
	}

	public float getScale() {
		return scale;
	}

	@Nullable
	public Bitmap getSprite(int x, int y) {
		return cellSprites[x][y];
	}

	public Bitmap[][] getSprites() {
		return cellSprites;
	}

	public int getVerticalGapSize() {
		return verticalGapSize;
	}

	public void setVerticalGapSize(int verticalGapSize) {
		this.verticalGapSize = verticalGapSize;
	}

	public int getHorizontalGapSize() {
		return horizontalGapSize;
	}

	public void setHorizontalGapSize(int horizontalGapSize) {
		this.horizontalGapSize = horizontalGapSize;
	}

	public Point getStartOffset() {
		return startOffset;
	}

	public void setStartOffset(Point startOffset) {
		this.startOffset = startOffset;
	}

	public Dimension getCellSize() {
		return cellSize;
	}
}
