package tk.hes.conquest;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.TinyPixelGame;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.BitmapLoader;
import me.deathjockey.tinypixel.graphics.RenderContext;

/**
 * Created by James on 8/19/2015.
 */
public class ConquestGame extends TinyPixelGame {

	protected static ConquestGame instance;
	private Bitmap hum00;

    public ConquestGame(String title, int width, int height) {
        super(title, width, height);

    }

    @Override
    protected void init(TinyPixelGame game, RenderContext renderContext) {
		hum00 = BitmapLoader.loadInternalBitmap("/hum02.png");
	}

    @Override
    protected void gameRender(RenderContext c) {
//		c.fillRegion(0, 0, 200, 200, Colors.PURE_YELLOW);
//		c.setDrawScale(3);
		c.render(hum00, 20, 20);
	}

    @Override
    protected void gameUpdate(Input input) {

    }

	public ConquestGame instance() {
		return instance;
	}
}
