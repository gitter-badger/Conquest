package tk.hes.conquest.states;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.TinyPixelStateBasedGame;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.state.PixelState;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.gui.base.GAlignment;
import tk.hes.conquest.gui.base.GButtonColor;
import tk.hes.conquest.gui.base.dialog.GDialogType;
import tk.hes.conquest.gui.base.dialog.GTitleDialog;
import tk.hes.conquest.gui.button.GAbstractButton;
import tk.hes.conquest.gui.button.GButtonGroup;
import tk.hes.conquest.gui.button.GButtonGroupSelector;
import tk.hes.conquest.gui.button.GTextButton;
import tk.hes.conquest.gui.listener.GButtonActionListener;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestState extends PixelState implements GButtonActionListener {

    public GButtonGroupSelector selector;
    public GButtonGroup menuGroup;
    private GTextButton campaignButton;
    private GTextButton multiplayerButton;
    private GTextButton scrimmageButton;
    private GTextButton exitButton;

    private GTitleDialog dialog;


    public TestState(TinyPixelStateBasedGame game) {
        super(game);
    }

    @Override
    public void init(RenderContext c) {
        menuGroup = new GButtonGroup(new Vector2f(30, 205), 5, GAlignment.VERTICAL);
        menuGroup.addButton((campaignButton = new GTextButton("Campaign", GButtonColor.BLUE, menuGroup)));
        menuGroup.addButton((multiplayerButton = new GTextButton("Multiplayer", GButtonColor.RED, menuGroup)));
        menuGroup.addButton((scrimmageButton = new GTextButton("Scrimmage", GButtonColor.GREEN, menuGroup)));
        menuGroup.addButton((exitButton = new GTextButton("Exit", GButtonColor.GREY, menuGroup)));
        menuGroup.init(c);

        selector = new GButtonGroupSelector(menuGroup);

        campaignButton.addActionListener(this);
        multiplayerButton.addActionListener(this);
        scrimmageButton.addActionListener(this);
        exitButton.addActionListener(this);

        dialog = new GTitleDialog("Test Message!", new Vector2f(0, 0), new Dimension(75, 75), GDialogType.ERROR);
        dialog.setTitle("Warning");
        dialog.init(c);

    }

    @Override
    public void update() {
        if (Input.getKeyDown(KeyEvent.VK_ESCAPE)) System.exit(0);
        menuGroup.update();
        selector.carrotUpdate();
        dialog.update();
    }

    @Override
    public void render(RenderContext c) {
        menuGroup.render(c);
        selector.render(c);
        dialog.render(c);
    }

    @Override
    public int getID() {
        return StateID.TEST_STATE.getID();
    }


    @Override
    public void actionPreformed(GAbstractButton button) {
        if (button == campaignButton) {
            System.out.println("CAMPAIGN");
        } else if (button == scrimmageButton) {
            System.out.println("SCRIMMAGE");
        } else if (button == multiplayerButton) {
            System.out.println("MULTIPLAYER");
        } else if (button == exitButton) {
            System.out.println("QUIT GAME");
        }

    }
}
