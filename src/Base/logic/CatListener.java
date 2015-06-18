package Base.logic;

public interface CatListener {

	public enum CatMode {
		ORIGINAL, JAZZ;
	}
	
	public void didEnterMode(Cat cat, CatMode mode);
}
