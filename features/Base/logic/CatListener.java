package Base.logic;

public interface CatListener {

	/*if[JazzMode]*/
	public enum CatMode {
		ORIGINAL, JAZZ;
	}

	public void didEnterMode(Cat cat, CatMode mode);
	/*end[JazzMode]*/
	
	
	/*if[Noten_schiessen]*/
	public void didShoot(Obstacle obstacle);
	/*end[Noten_schiessen]*/
}
