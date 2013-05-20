package GUI;

public class ViewListener {
	private GUI g;

	ViewListener(GUI g) {
		this.g = g;
	}

	public void actionPerformed() {
		g.updateAll();

	}

}
