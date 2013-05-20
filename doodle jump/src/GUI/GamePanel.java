package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.jfree.ui.TextAnchor;

import doodleJump.Ball;
import doodleJump.Engine;
import doodleJump.Platform;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -1000316276088237911L;
	private Engine e;
	private int height;
	private int width;
	private CircleTailDrawer emoteDrawer;
	private BufferedImage background;

	GamePanel(Engine engine, int width, int height) {
		this.width = width;
		this.e = engine;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		setCircleTailDrawer(new BasicCircleTail());
		try {
			background = ImageIO.read(GUI.backgroundURL);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void setCircleTailDrawer(CircleTailDrawer d) {
		synchronized (e) {
			emoteDrawer = d;
			d.setDimensions(width, height);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		synchronized (e) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, width, height, 0, 0,
					Math.min(background.getWidth(), width),
					Math.min(background.getHeight(), height), null);
			int top, left;
			// drawing the platforms
			for (Platform p : e.getPlatForms()) {
				left = p.getX();
				top = height - p.getY() - Platform.HEIGHT / 2;
				g.setColor(Color.GREEN);
				g.fillRect(left, top, Platform.WIDTH, Platform.HEIGHT);
			}
			g.setColor(Color.RED);
			Ball b = e.getBall();
			emoteDrawer.drawBallWithTail(g, b);
			g.setColor(Color.BLACK);
			((Graphics2D) g).setStroke(new BasicStroke(Platform.WIDTH / 2));
			g.drawRect(0, 0, width, height);
			DrawingUtil.drawString("Score = " + e.getScore(),g,12, Platform.WIDTH / 2,
					Platform.WIDTH,TextAnchor.TOP_LEFT,"Times new roman");
		}
	}

}
