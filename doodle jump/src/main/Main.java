package main;

import javax.swing.SwingUtilities;

import GUI.FiveCircleTail;
import GUI.GUI;
import doodleJump.Engine;
import doodleJump.Platform;
/**
 * This class is stylized for using SWING to implement the GUI.
 */

public class Main {
	private static int width = 200;
	private static int height = 400;
	public static void main(String[] args){
		final Engine game = new Engine(width-Platform.WIDTH,height);
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				GUI gui = new GUI(game,width,height);
				gui.setCircleTailDrawer(new FiveCircleTail());
			}
		});
	}
}
