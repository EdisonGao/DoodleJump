package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.jfree.ui.TextAnchor;

import doodleJump.Engine;

public class StartPanel extends JPanel {
	private BufferedImage background;
	private int width;
	private int height;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7365560470317243895L;

	public StartPanel(final Engine engine, int width, int height) {
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		try {
			background = ImageIO.read(GUI.backgroundURL);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, width, height, 0, 0,
				Math.min(background.getWidth(), width),
				Math.min(background.getHeight(), height), null);
		/*
		 * drawing the text box that the title is on
		 */
		g.drawRoundRect(width / 8, height * 3 / 8, width * 3 / 4, height / 4,
				width / 8, height / 8);

		DrawingUtil.drawString("Doodle", g, 35, width / 2, height / 2,
				TextAnchor.BOTTOM_CENTER, "AR DESTINE");
		DrawingUtil.drawString("Jump", g, 35, width / 2, height / 2,
				TextAnchor.TOP_CENTER, "AR DESTINE");
		DrawingUtil.drawString("Click anywhere to start", g, 14, width / 2,
				height * 3 / 4, TextAnchor.CENTER, "AR DESTINE");

	}
}
