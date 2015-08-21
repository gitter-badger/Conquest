package tk.hes.conquest;

import me.deathjockey.tinypixel.TinyPixelGameDesktopWrapper;

public class ConquestGameDesktopLauncher {

	public static final String TITLE = "Conquest Game";
	public static final String VERSION = "v0.0.1";
	public static final int INIT_WIDTH = 800, INIT_HEIGHT = 640;

	public static void main(String[] args) {
		ConquestGame game = new ConquestGame(TITLE + "  " + VERSION, INIT_WIDTH, INIT_HEIGHT);
		TinyPixelGameDesktopWrapper wrapper = new TinyPixelGameDesktopWrapper(game);
		game.setGraphicsScale(4);
		game.setNumBuffer(2);
		game.setTargetFps(60);
		game.setFpsVerbose(true);
		wrapper.start();

		ConquestGame.instance = game;
	}

}
