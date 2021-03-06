package tk.hes.conquest.actor;

import me.nibby.pix.Bitmap;

import java.util.ArrayList;
import java.util.LinkedList;

public class Action {

	private LinkedList<Frame> actionFrames = new LinkedList<>();
	private int currentFrame = 0;
	private int currentDuration = 0;
	private boolean firstTime = true;
	private long lastUpdateTime;
	private boolean customUpdateTime = false;

	private ArrayList<ActionKeyFrameListener> listeners = new ArrayList<>();
	private int currentFrameDuration;

	public Action(ActionKeyFrameListener... listeners) {
        if (listeners != null) {
            for (ActionKeyFrameListener listener : listeners) {
				this.listeners.add(listener);
			}
		}
	}

	public void update() {
		if(firstTime) {
			reset();
			firstTime = false;
		}

		currentDuration -= (int) (System.currentTimeMillis() - lastUpdateTime);
		if(currentDuration <= 0) {
			if(currentFrame < actionFrames.size() - 1) {
				currentFrame++;
			} else {
				reset();
			}
			currentDuration = actionFrames.get(currentFrame).duration;
			ArrayList<String> keyList = actionFrames.get(currentFrame).keyList;
			for(String key : keyList) {
				for(ActionKeyFrameListener l : listeners) {
					l.keyFrameReached(key);
				}

				//Special frame keys
				if(key.startsWith("$")) {
					if(key.startsWith("$RANDOM_DELAY")) {
						String[] kvs = key.split(" ");
						int randomValue = (int) (Math.random() * Integer.parseInt(kvs[2]) + Integer.parseInt(kvs[1]));
						currentDuration += randomValue;
					}
				}
			}
		}

		if(!customUpdateTime)
			lastUpdateTime = System.currentTimeMillis();
	}

	public Frame getCurrentFrame() {
		return actionFrames.get(currentFrame);
	}

	public Action addFrame(Bitmap frame, int duration, int xOffset, int yOffset, String ... keys) {
		actionFrames.add(new Frame(frame, duration, xOffset, yOffset, keys));
		return this;
	}

	public void reset() {
		currentFrame = 0;
		currentDuration = getCurrentFrame().duration;
		lastUpdateTime = System.currentTimeMillis();
		customUpdateTime = false;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
		customUpdateTime = true;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public int getCurrentFrameDuration() {
		return currentFrameDuration;
	}

	public class Frame {

		private int xOffset, yOffset;
		private Bitmap frame;
		private int duration;
		private ArrayList<String> keyList = new ArrayList<>();

		private Frame(Bitmap frame, int duration, int xFlipOffset, int yFlipOffset, String ... keys) {
			this.xOffset = xFlipOffset;
			this.yOffset = yFlipOffset;
			this.frame = frame;
			this.duration = duration;

			if(keys != null) {
				for (String key : keys) {
					keyList.add(key);
				}
			}
		}

		public ArrayList<String> getKeyList() {
			return keyList;
		}

		public int getxOffset() {
			return xOffset;
		}

		public int getyOffset() {
			return yOffset;
		}

		public Bitmap getBitmap() {
			return frame;
		}

		public int getDuration() {
			return duration;
		}
	}

}
