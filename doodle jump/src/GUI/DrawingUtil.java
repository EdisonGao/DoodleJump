package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.jfree.text.TextUtilities;
import org.jfree.ui.TextAnchor;

/**
 * this class contains statically all the methods that are helpful for drawing
 * things easily.
 * 
 * @author E Gao
 * 
 */
public final class DrawingUtil {
	private DrawingUtil() {

	}

	static void drawString(String text, Graphics g, int fontSize, int xLoc,
			int yLoc, TextAnchor anchor,String fontName) {
		g.setFont(new Font(fontName, Font.BOLD, fontSize - 1));
		g.setColor(Color.WHITE);
		TextUtilities.drawAlignedString(text, (Graphics2D) g, xLoc, yLoc,
				anchor);
	}
	
	static void drawCircleWithBorder(Graphics g, Color c, int cx, int cy, int r) {

		int left = cx - r;
		int top = cy - r;
		g.setColor(c);
		g.fillOval(left, top, 2 * r, 2 * r);
		g.setColor(Color.black);
		g.drawOval(left, top, 2*r, 2*r);
	}
	
	static void drawCircle(Graphics g, Color c, int cx, int cy, int r) {

		int left = cx - r;
		int top = cy - r;
		g.setColor(c);
		g.fillOval(left, top, 2 * r, 2 * r);
	}

}
