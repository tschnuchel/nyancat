package Base.movement;

import org.newdawn.slick.geom.Point;

public abstract class Movement {

	private Movement child;
	
	public Movement() {
		
		child = null;
	}
	
	public Movement(Movement child) {
		
		this.child = child;
	}
	
	public abstract Point getDeltaMovement(int delta);
	
	public abstract Point getCurrentSpeed();

	public Movement getChild() {
		return child;
	}
	
	public void setChild(Movement child) {
		
		this.child = child;
	}
}
