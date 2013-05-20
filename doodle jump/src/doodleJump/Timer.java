package doodleJump;

public class Timer extends Thread {

	private long delay;
	private Engine e;

	Timer(Engine e, long delay) {
		this.delay = delay;
		this.e = e;
	}

	@Override
	public void run() {
		while (true) {
			try {
				sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

				e.updateAll();

		}
	}
}
