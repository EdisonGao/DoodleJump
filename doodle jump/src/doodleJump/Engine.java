package doodleJump;

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import GUI.ViewListener;

/**
 * The engine for the doodle jump game. The coordinates were all designed with a
 * rectangular grid system in mind, in which the BOTTOM-LEFT corner is
 * designated with (0,0).
 * 
 * @author E Gao
 * 
 */
public class Engine {
	static int highScoresKept = 5;
	private static Score[] highScores = new Score[highScoresKept];

	private int score = 0;
	private int WINDOW_WIDTH;
	private int WINDOW_HEIGHT;
	private int scrollUpLimit;
	private int distanceBetweenPlatforms;
	private final long timerDelay = 100;
	LinkedList<Platform> visiblePlatforms;;
	/*
	 * invariant: the visiblePlatforms are supposed to always be in increasing
	 * order, in terms of the y coordinate of the platform
	 */

	private int variance;
	private ExecutorService pool = Executors.newFixedThreadPool(1);
	private Ball ball;
	private ViewListener gui;
	private boolean hasStarted = false;
	private boolean gameOver = false;
	private ActionListener gameOverListener;

	public Engine(int width, int height) {

		WINDOW_HEIGHT = height;
		scrollUpLimit = WINDOW_HEIGHT * 4 / 5;
		WINDOW_WIDTH = width;
		variance = WINDOW_WIDTH / 10;
		distanceBetweenPlatforms = Ball.maxDistance;
		init();
		pool.execute(new Timer(this, timerDelay));
	}

	/**
	 * this function can be called to re-set up the game, restoring it to its
	 * initial conditions
	 */
	public void init() {
		initPlatforms();
		initBall();
		hasStarted = false;
		gameOver = false;
		this.score = 0;
	}

	public static Score[] getHighScores() {
		synchronized (highScores) {
			return highScores;
		}
	}

	public static boolean shouldAddScore(int score) {
		synchronized (highScores) {
			for (Score s : highScores) {
				if (s == null)
					return true;
				// there is still an empty spot in the high scores list
				else if (s.getScore() > score)
					return true;
				// there is a score that can be evicted
			}
			return false;
		}
	}

	public int getScore() {
		return this.score;
	}

	private void initBall() {
		Platform first = visiblePlatforms.get(0);
		ball = new Ball(first.getX(), first.getY());
	}

	public Ball getBall() {
		return ball;
	}

	public LinkedList<Platform> getPlatForms() {
		return visiblePlatforms;
	}

	private boolean hasCollided(Platform p) {
		int x = p.getX();
		int y = p.getY();
		int cx = ball.getX();
		int cy = ball.getY();
		int r = Ball.r;
		if (x - Platform.WIDTH / 2 < cx && x + Platform.WIDTH / 2 > cx
				&& y - Platform.HEIGHT / 2 < cy - r
				&& y + Platform.HEIGHT / 2 > cy - r) {
			return true;
		} else
			return false;
	}

	private void initPlatforms() {
		visiblePlatforms = new LinkedList<Platform>();
		Random gen = new Random();
		int y = gen.nextInt(distanceBetweenPlatforms / 2);
		y += distanceBetweenPlatforms / 2;
		int x = WINDOW_WIDTH / 2;
		visiblePlatforms.add(new Platform(x, y));
		int ydiff;
		int xdiff;
		while (y < WINDOW_HEIGHT) {
			ydiff = gen.nextInt(distanceBetweenPlatforms / 2);
			ydiff += (distanceBetweenPlatforms / 2);

			y += ydiff;
			xdiff = gen.nextInt(variance * 2);
			x = (x + xdiff - variance) % WINDOW_WIDTH;
			if (x < 0)
				x += WINDOW_WIDTH;
			visiblePlatforms.add(new Platform(x, y));
		}
	}

	private void refillPlatforms() {
		Platform last;

		last = visiblePlatforms.get(visiblePlatforms.size() - 1);
		int ydiff;
		int xdiff;
		Random gen = new Random();
		int x;
		while (last.getY() < WINDOW_HEIGHT) {
			ydiff = gen.nextInt(distanceBetweenPlatforms / 2);
			ydiff += (distanceBetweenPlatforms / 2);
			xdiff = gen.nextInt(variance * 2);
			x = (last.getX() + xdiff - variance) % WINDOW_WIDTH;
			if (x < 0)
				x += WINDOW_WIDTH;
			last = new Platform(x, last.getY() + ydiff);
			visiblePlatforms.add(last);
			score += 1;
		}
	}

	private void updateBallPos() {
		// updating the ball position according to its velocity, and its
		// velocity according to gravity.
		ball.updateX(WINDOW_WIDTH);
		if (ball.getY() < 0) {
			gameOverListener.actionPerformed(null);
			this.gameOver = true;
		} else if (ball.isMovingDown()) {
			// only update smoothly (move one step at a time) when the ball is
			// falling down. Otherwise just apply the difference of dy to y;
			while (ball.isMovingDown() && ball.shouldMoveY()) {
				for (Platform p : visiblePlatforms) {
					if (hasCollided(p)) {
						p.doCollisionAction();
						ball.bounce();
						break;
					} else if (p.getY() - Platform.HEIGHT / 2 > ball.getY()
							+ Ball.r) {
						// the platform check is above where the ball is.
						break;
					}
				}
				ball.updateSmoothY();
			}
		} else
			ball.updateRoughY();
		ball.resetYStepCounter();
		ball.updateDy();
	}

	private void updatePlatforms() {
		// updating the platforms by deleting the olds ones if they appear
		int y = ball.getY();
		if (y > scrollUpLimit) {
			int diff = y - scrollUpLimit;
			ball.setY(scrollUpLimit);
			Iterator<Platform> iter = visiblePlatforms.iterator();
			Platform p;
			while (iter.hasNext()) {
				p = iter.next();
				if (p.getY() > diff) {
					p.scroll(diff);
				} else {
					iter.remove();
				}
			}
		}
		refillPlatforms();
	}

	public boolean hasStarted() {
		return hasStarted;
	}

	public boolean gameOver() {
		return gameOver;
	}

	public void registerGameOverListener(ActionListener a) {
		this.gameOverListener = a;
	}

	void updateAll() {
		synchronized (this) {
			if (hasStarted) {
				if (!gameOver) {
					updateBallPos();
					updatePlatforms();
				}
				gui.actionPerformed();

			}
		}
	}

	public void moveLeft() {
		synchronized (this) {
			if (hasStarted) {
				ball.moveLeft();
			}
		}
	}

	public void moveRight() {
		synchronized (this) {
			if (hasStarted) {
				ball.moveRight();
			}
		}
	}

	/**
	 * registering a listener that should be called to update when there is a
	 * change in the model
	 * 
	 * @param object
	 *            a listener that should tell the GUI to update
	 */

	public void registerView(ViewListener object) {
		gui = object;
	}

	public void start() {
		synchronized (this) {
			if (hasStarted == false)
				hasStarted = true;
			else
				init();
		}
	}

	public static void addScore(Score s) {
		synchronized (highScores) {
			for (int i = 0; i < highScoresKept; i++) {
				if (highScores[i] == null) {
					highScores[i] = s;
					return;
				} else if (s.compareTo(highScores[i]) > 0) {
					for (int j = highScoresKept - 1; j > i; j--) {
						highScores[j] = highScores[j - 1];
					}
					highScores[i] = s;
					return;
				}
			}
		}
	}
}
