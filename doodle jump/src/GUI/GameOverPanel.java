package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.ui.TextAnchor;

import doodleJump.Engine;
import doodleJump.Score;

public class GameOverPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5496400981778894020L;

	private BufferedImage background;
	private int width;
	private int height;
	private Engine e;
	private int score;

	public GameOverPanel(Engine engine, int width, int height) {
		this.width = width;
		this.height = height;
		this.e = engine;
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		try {
			background = ImageIO.read(GUI.backgroundURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void setup() {
		score = e.getScore();
		if (Engine.shouldAddScore(score)) {
			final JTextField entry = new JTextField("Enter Your Name");
			entry.setFocusable(true);
			entry.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent arg0) {
					if (entry.getText().equals("")) {
						entry.setText("Enter Your Name");
					}
				}

				@Override
				public void focusGained(FocusEvent arg0) {
					if (entry.getText().equals("Enter Your Name")) {
						entry.setText("");
					}

				}
			});
			entry.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("printed");
					JTextField textBox = (JTextField) arg0.getSource();
					Engine.addScore(new Score(score, textBox.getText()));
					Container parent = textBox.getParent();
					parent.remove(textBox);
					// parent.revalidate();
					parent.repaint();
				}

			});
			entry.setPreferredSize(new Dimension(width, height / 7));
			this.add(entry, BorderLayout.SOUTH);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, width, height, 0, 0,
				Math.min(background.getWidth(), width),
				Math.min(background.getHeight(), height), null);
		Score[] scores = Engine.getHighScores();
		int numScores = scores.length;
		Score s;
		String text;
		DrawingUtil.drawString("High scores", g, 20, width / 2, 0,
				TextAnchor.TOP_CENTER, "AR DESTINE");
		DrawingUtil.drawString("Click anywhere to restart", g, 12, width / 2,
				(numScores + 1) * height / (numScores + 2),
				TextAnchor.TOP_CENTER, "AR DESTINE");

		for (int i = 0; i < numScores; i++) {
			s = scores[i];
			if (s != null) {
				text = "" + s.getScore() + " : " + s.getName();
				DrawingUtil.drawString(text, g, 15, width / 2, (i + 1) * height
						/ (numScores + 2), TextAnchor.TOP_CENTER,
						"Times New Roman");
			}
		}
	}
}
