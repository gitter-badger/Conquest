package me.deathjockey.tinypixel.test;

import me.deathjockey.tinypixel.TinyPixelGameDesktopWrapper;

/**
 * Created by James on 8/21/2015.
 */
public class TestGameLauncher {
    public static final String TITLE = "Conquest Game";
    public static final String VERSION = "v0.0.1";
    public static final int INIT_WIDTH = 800, INIT_HEIGHT = INIT_WIDTH / 16 * 9;

    public static void main(String[] args) {
        TestGame game = new TestGame(TITLE + "  " + VERSION, INIT_WIDTH, INIT_HEIGHT);
        TinyPixelGameDesktopWrapper wrapper = new TinyPixelGameDesktopWrapper(game);
        game.setGraphicsScale(2);
        game.setNumBuffer(2);
        game.setTargetFps(60);
        game.setFpsVerbose(false);
        wrapper.start();
    }

}
