package doodleJump;

public class Platform {
	private int x;
	private int y;
	public static int WIDTH = 20;
	public static int HEIGHT = 6;

	Platform(int x, int y) {
		if (x< 0) throw new RuntimeException("HOW");
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	void doCollisionAction() {
	}

	public int getY() {
		return y;
	}

	public void scroll(int diff) {
		this.y -= diff;

	}

	@Override
	public String toString() {
		return "Platform(" + x +","+ y+")";
	}
}
