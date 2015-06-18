package Base.logic;

public abstract class Movable extends CollidableBACKUP {

	public abstract void updatePosition(int delta);
	
	public abstract boolean isValid();
}
