package me.deathjockey.tinypixel.graphics;

import com.sun.istack.internal.NotNull;

import java.util.LinkedList;

/**
 * An animation is a series of Bitmaps played in a timed manner.
 * Various controls exist to render animations on-screen. These
 * are managed by the update method.
 *
 * @author Kevin Yang
 */
public class Animation {

	/** Number of frames in the animation */
	private LinkedList<Frame> frames = new LinkedList<>();

	/** Current frame being drawn */
	private int frame = -1;

	/** Is the animation playing? */
	private boolean started = false;

	/** Relative speed to play the animation in (original time * speed factor) */
	private float speed = 1f;

	/** Frame update timer */
	private long lastUpdate;

	/** Control for looping the animation (i.e. start from beginning again when completed) */
	private boolean looping = false;

	/** Control for reversing the animation (i.e. start from last frame of bitmap list) */
	private boolean reverseMode = false;

	/** Control for pingpong mode (i.e. start from beginning but play reverse when last frame is reached, loop) */
	private boolean pingpongMode = false;

	/** Internal logic switch to initialize timers and variables on first update */
	private boolean firstUpdate = true;

	public Animation() {}

	public Animation(@NotNull Bitmap[] animation, @NotNull int[] delayTimes) {
		if(animation.length != delayTimes.length)
			throw new IllegalArgumentException("Animation frames and delay time length mismatch!");
		for(int i = 0; i < animation.length; i++) {
			frames.add(new Frame(animation[i], delayTimes[i]));
		}
	}

	public Animation(@NotNull Bitmap[] animation, int delay) {
		for(int i = 0; i < animation.length; i++) {
			frames.add(new Frame(animation[i], delay));
		}
	}

	/**
	 * Adds a new frame to the end of the animation list
	 * @param bitmap Bitmap to be drawn
	 * @param delay Time in milliseconds the frame lasts
	 * @return
	 */
	public Animation addFrame(Bitmap bitmap, int delay) {
		frames.add(new Frame(bitmap, delay));
		return this;
	}

	/**
	 * Renders the animation on a given context.
	 *
	 * @param context Render context to be drawn on
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 */
	public void render(RenderContext context, int x, int y) {
		render(context, x, y, 1.0f);
	}

	/**
	 * Renders the animation on a given context with specified transparency.
	 *
	 * @param context Render context to be drawn on
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 * @param alpha	Alpha transparency of the animation
	 */
	public void render(RenderContext context, int x, int y, float alpha) {
		render(context, x, y, alpha, Colors.toInt(0, 0, 0, 0));
	}

	/**
	 * Renders the animation on a given context with specified transparency and tint color.
	 *
	 * @param context Render context to be drawn on
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 * @param alpha	Alpha transparency of the animation
	 * @param tintColor Tint color of the animation
	 */
	public void render(RenderContext context, int x, int y, float alpha, int tintColor) {
		update();
		Frame f = frames.get(frame);
		context.render(f.bitmap, x, y, alpha, tintColor);
	}

	/**
	 * Updates animation timers and frame information
	 */
	public void update() {
		if(firstUpdate && !started) {
			start();
			firstUpdate = false;
		}

		if(started) {
			Frame f = frames.get(frame);
			int delay = (int) ((float) f.time * speed);
			if(System.currentTimeMillis() - lastUpdate > delay) {
				if(reverseMode) {
					frame--;
					if(frame < 0) {
						if(pingpongMode) {
							setReverseMode(false);
							frame++;
						} else if(looping) {
							frame = frames.size() - 1;
						} else {
							frame = 0;
							started = false;
						}
					}
				} else {
					frame++;
					if(frame > frames.size() - 1) {
						if(pingpongMode) {
							setReverseMode(true);
							frame--;
						} else if(looping) {
							frame = 0;
						} else {
							frame = frames.size() - 1;
							started = false;
						}
					}
				}
				lastUpdate = System.currentTimeMillis();
			}
		}
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public boolean isLooping() {
		return looping;
	}

	public void setLooping(boolean looping) {
		this.looping = looping;
	}

	public boolean isReverseMode() {
		return reverseMode;
	}

	public void setReverseMode(boolean reverseMode) {
		this.reverseMode = reverseMode;
	}

	public boolean isPingpongMode() {
		return pingpongMode;
	}

	public void setPingpongMode(boolean pingpongMode) {
		this.pingpongMode = pingpongMode;
	}

	/**
	 * Begin playing animation.
	 */
	public void start() {
		if(!started) {
			restart();
		}
	}

	/**
	 * Resets animation timer to current time and frame to 0.
	 * Plays the animation again if stopped.
	 */
	public void restart() {
		frame = 0;
		lastUpdate = System.currentTimeMillis();
		started = true;
	}

	/**
	 * Stops playing animation.
	 */
	public void stop() {
		if(started) {
			lastUpdate = System.currentTimeMillis();
		}
	}

	public boolean hasStopped() {
		return !started;
	}

	/**
	 * A frame represents a Bitmap with a given time (in milliseconds).
	 */
	class Frame {

		private Bitmap bitmap;
		private int time;

		private Frame(Bitmap bitmap, int time) {
			this.bitmap = bitmap;
			this.time = time;
		}

	}

}
