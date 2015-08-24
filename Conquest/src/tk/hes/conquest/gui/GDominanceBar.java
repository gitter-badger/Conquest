package tk.hes.conquest.gui;

import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.graphics.Art;
import tk.hes.conquest.gui.base.GComponent;
import tk.hes.conquest.gui.base.GImage;

/**
 * @author James
 */
public class GDominanceBar extends GComponent {

    private GImage dominanceBarImage;

    public GDominanceBar(Vector2f position) {
        super(position);
    }

    @Override
    public void init(RenderContext c) {
        dominanceBarImage = new GImage(Art.DOMINANCE_BAR, new Vector2f(0, 0), this);
        this.setSize(dominanceBarImage.getSize());
    }

    @Override
    public void render(RenderContext c) {
        dominanceBarImage.render(c);

    }

    @Override
    public void update() {

    }
}
