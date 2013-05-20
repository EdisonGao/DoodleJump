package GUI;

import java.awt.Color;
import java.awt.Graphics;

import doodleJump.Ball;

public class BasicCircleTail extends CircleTailDrawer {

	@Override
	public void drawBallWithTail(Graphics g, Ball b) {
		int r = Ball.r;
		int cx = b.getX();
		int cy = b.getY();
		drawCircle(g, Color.RED, cx, cy, r);
	}

}
