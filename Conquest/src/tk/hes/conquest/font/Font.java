package tk.hes.conquest.font;

import me.deathjockey.tinypixel.graphics.BitFont;
import me.deathjockey.tinypixel.graphics.RenderContext;
import tk.hes.conquest.graphics.Art;

import java.awt.*;

/**
 * A Font class for managing fonts apart of the game.
 *
 * @author James Roberts, Kevin Yang
 */
public class Font {

    public static final String NORMAL = "normal";

    private BitFont gameFont;

    public void initFonts() {
        gameFont = new BitFont(Art.FONT, "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789;'\"             " +
                "!@#$%^&*()-+_=~.,<>?/\\[]|:", 4, 26, new Dimension(6, 10));
        gameFont.addSpacingRule("sxItrf<>?", 5, 0);
        gameFont.addSpacingRule("^1*()+-;:/.,", 4, 0);
        gameFont.addSpacingRule("j", 4, 1);
        gameFont.addSpacingRule("li!", 3, 0);
    }


    public void addFonts(RenderContext c) {
        c.installFont(Font.NORMAL, gameFont);
    }

}
