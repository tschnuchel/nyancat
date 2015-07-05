package Base.logic;

public enum Difficulty {

	EASY(2),
	MIDDLE(1),
	HARD(0.5);
	
	private double difficulty;
	
	private Difficulty(double difficulty) {
		
		this.difficulty = difficulty;
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
			break;
		}
		
		return null;
	}

	public double getDifficulty() {
		return difficulty;
	}
}
