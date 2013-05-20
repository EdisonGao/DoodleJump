package doodleJump;

public class Score implements Comparable<Score> {

	private int score;
	private String name;

	public Score(int score, String name) {
		this.score = score;
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Score o) {
		return this.score - o.score;
	}

}
