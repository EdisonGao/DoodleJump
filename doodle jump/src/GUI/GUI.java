package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

import doodleJump.Engine;

public class GUI {
	public static String backgroundName = "images/background.png";
	public static URL backgroundURL = GUI.class.getResource("background.png");

	private GamePanel game;
	private JPanel startScreen;
	private JFrame main;
	private GameOverPanel gameOverScreen;
	private Engine e;



	/**
	 * Constructor for the class GUI. this sets up the three types of screens
	 * that should be displayed during the lifetime of a game, the startscreen,
	 * game screen, and end screen
	 * 
	 * @param e
	 *            the engine for the game
	 * @param width
	 *            the width of the desired size of game
	 * @param height
	 *            the height of the desired size of game
	 */
	public GUI(final Engine e, int width, int height) {
		e.registerView(new ViewListener(this));
		this.e = e;
		/*
		 * setting up the main JFrame
		 */
		e.registerGameOverListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameOverScreen.setup();
				switchTo(gameOverScreen);
			}
		});
		main = new JFrame();
		setupStartupContainer(e, width, height);
		setupGameContainer(e, width, height);
		setupGameOverContainer(e, width, height);
		main.setSize(width, height);
		main.setResizable(false);
		switchTo(startScreen);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}

	private void setupGameOverContainer(final Engine e, int width, int height) {
		gameOverScreen = new GameOverPanel(e,width, height);
		gameOverScreen.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent event) {
				e.init();
				switchTo(startScreen);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void switchTo(JPanel p) {
		main.remove(startScreen);
		main.remove(game);
		main.remove(gameOverScreen);
		main.add(p);
		main.setContentPane(p);
		main.pack();
	}

	private void setupStartupContainer(final Engine e, int width, int height) {
		startScreen = new StartPanel(e, width, height);
		startScreen.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				switchTo(game);
				e.start();
				game.requestFocusInWindow();

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	private void setupGameContainer(final Engine e, int width, int height) {

		game = new GamePanel(e, width, height);
		game.setFocusable(true);
		game.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				int code = arg0.getKeyCode();
				if (code == 39)
					// Right arrow key
					e.moveRight();
				else if (code == 37)
					// Left arrow key
					e.moveLeft();
				else if (code == 32)
					// spacebar
					e.start();
				else
					System.out.println("key error :" + code);
			}
		});
	}

	public void setCircleTailDrawer(CircleTailDrawer d) {
		game.setCircleTailDrawer(d);
	}

	void updateAll() {
		if (e.gameOver()) {
			gameOverScreen.repaint();
		} else if (e.hasStarted())
			game.repaint();
	}
}
