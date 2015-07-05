package Base.logic;

public enum Difficulty {
	EASY(2.0), MIDDLE(1.0), HARD(0.5);

	private Double difficulty;

	private Difficulty(Double difficulty) {
		this.difficulty = difficulty;
	}
	
	public double getDifficulty() {
		return difficulty;
	}
	
	@Override
	public String toString() {
		switch (this) {
		case EASY:
			return "Easy";
		case MIDDLE:
			return "Middle";
		case HARD:
			return "Hard";
		default:
			return null;
		}
	}

}
