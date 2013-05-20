package doodleJump;

public class Ball {
	public static int r = 10;
	public static int initialUpVelocity = 10;
	public static int grav = 1;
	public static int dxReductionCounterInit = 3;
	public static int maxDistance = initialUpVelocity * initialUpVelocity
			/ (2 * grav) - Ball.r;

	private final int dxMovementStepSize = 2;

	private int x, y;
	private int dx = 0;
	private int dy = initialUpVelocity;
	private int movedThisTimeStepY = 0;
	private final int coarseness = 3;
	private int dxReductionCounter = dxReductionCounterInit;

	Ball(int x, int y) {
		this.x = x;
		this.y = y;
	}

	void updateX(int maxWidth) {
		this.x += dx;
		dxReductionCounter -= 1;
		if (dxReductionCounter == 0) {
			dx /= 2;
			dxReductionCounter = dxReductionCounterInit;
		}
		if (x < 0)
			x += maxWidth;
		else if (x > maxWidth)
			x -= maxWidth;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	void updateDy() {
		this.dy -= grav;
	}

	boolean isMovingDown() {
		return dy < 0;
	}

	void updateSmoothY() {
		// assert (this.dy < 0);
		this.y -= coarseness;
		this.movedThisTimeStepY -= coarseness;
	}

	void resetYStepCounter() {
		this.movedThisTimeStepY = 0;
	}

	void updateRoughY() {
		// assert (this.dy >= 0);
		this.y += dy;

	}

	void setY(int y) {
		// should only be called from the Engine's scrolling mechanism
		this.y = y;
	}

	boolean shouldMoveY() {
		return this.movedThisTimeStepY > this.dy;
	}

	void bounce() {
		this.dy = initialUpVelocity;
	}

	public void moveLeft() {
		dx -= dxMovementStepSize;
	}

	public void moveRight() {
		dx += dxMovementStepSize;
	}

	@Override
	public String toString() {
		return "Ball(" + x + "," + y + ")";

	}
}
