package tk.hes.conquest.gui;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GComponent;

/**
 * @author James
 */
public class GGameOverlay extends GComponent {

    private GPlayerInfo playerInfo;
    private GDominanceBar dominanceBar;

    public GGameOverlay(Vector2f position) {
        super(position);
    }

    @Override
    public void init(RenderContext c) {
        dominanceBar = new GDominanceBar(new Vector2f(0, 0));
        dominanceBar.init(c);

        playerInfo = new GPlayerInfo(new Vector2f(0, (int) dominanceBar.getSize().getHeight() - 4));
        playerInfo.init(c);
    }


    @Override
    public void update() {
        dominanceBar.update();
        playerInfo.update();
    }

    @Override
    public void render(RenderContext c) {
        dominanceBar.render(c);
        playerInfo.render(c);
    }

}
