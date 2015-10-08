package tk.hes.conquest;

import me.nibby.pix.PixGameDesktopWrapper;

public class ConquestGameDesktopLauncher {

	public static final String TITLE = "Conquest Game";
	public static final String VERSION = "v0.0.1";
	public static final int INIT_WIDTH = 800, INIT_HEIGHT = 640, SCALE = 2;

	public static void main(String[] args) {
		ConquestGame game = new ConquestGame(TITLE + "  " + VERSION, INIT_WIDTH, INIT_HEIGHT, SCALE);
		PixGameDesktopWrapper wrapper = new PixGameDesktopWrapper(game);
		game.setTargetFps(60);
        game.setFpsVerbose(true);
        wrapper.launch();

		ConquestGame.instance = game;
	}

}
