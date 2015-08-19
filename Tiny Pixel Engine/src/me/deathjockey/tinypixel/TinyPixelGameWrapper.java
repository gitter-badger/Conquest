package me.deathjockey.tinypixel;

/**
 * This is an abstract game wrapper interface to be implemented by wrapper models.
 */
public interface  TinyPixelGameWrapper {

	public void start();
	public void stop();

	public abstract void setTitle(String title);
	public abstract void setMinimumSize(int width, int height);
	public abstract void setSize(int width, int height);
	public abstract void setMaximumSize(int width, int height);
	public abstract void setResizable(boolean resizable);
}
