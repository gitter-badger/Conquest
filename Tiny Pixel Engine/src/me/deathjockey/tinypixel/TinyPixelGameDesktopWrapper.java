package me.deathjockey.tinypixel;

import javax.swing.*;
import java.awt.*;

/**
 * A game wrapper for common desktop deployment. Essentially a JFrame that implements
 * Wrapper controls.
 *
 * @author Kevin Yang
 */
public class TinyPixelGameDesktopWrapper implements TinyPixelGameWrapper {

	private JFrame frame;
	private TinyPixelGame game;

	public TinyPixelGameDesktopWrapper(TinyPixelGame game) {
		this.game = game;

		frame = new JFrame(game.getTitle());
		frame.setResizable(game.isResizable());

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(game, BorderLayout.CENTER);
		frame.getContentPane().add(panel);
		frame.setMinimumSize(new Dimension(game.getWidth(), game.getHeight() + 25));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void start() {
		if(!game.isRunning()) {
			frame.setVisible(true);
			game.start();
		}
	}

	@Override
	public void stop() {
		if(game.isRunning()) {
			game.stop();
		}
	}

	@Override
	public void setTitle(String title) {
		frame.setTitle(title);
	}

	@Override
	public void setMinimumSize(int width, int height) {
		frame.setMinimumSize(new Dimension(width, height));
	}

	@Override
	public void setSize(int width, int height) {
		frame.pack();
	}

	@Override
	public void setMaximumSize(int width, int height) {
		frame.setMaximumSize(new Dimension(width, height));
	}

	@Override
	public void setResizable(boolean resizable) {
		frame.setResizable(resizable);
	}
}
