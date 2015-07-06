package Base.logic;

import Base.logic.collision.Collidable;

public abstract class Movable extends Collidable{

	public abstract void updatePosition(int delta);
	
	public abstract boolean isValid();
}
