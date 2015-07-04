package Base.movement;

import org.newdawn.slick.geom.Point;

public class LinearMovement extends Movement {

	/**
	 * in pixels per second
	 */
	private Point speedVector;
	
	public LinearMovement(Point speedVector) {
		
		this.speedVector = speedVector;
	}
	
	public LinearMovement(Point speedVector, Movement child) {
		
		super(child);
		this.speedVector = speedVector;
	}

	@Override
	public Point getDeltaMovement(int delta) {
		
		Point result;
		
		if (getChild() != null) {
			
			result = getChild().getDeltaMovement(delta);
		} else {
			
			result = new Point(0, 0);
		}
		float x = speedVector.getX() * delta / 1000f;
		float y = speedVector.getY() * delta / 1000f;
		result.setX(result.getX() + x);
		result.setY(result.getY() + y);
		return result;
	}

	@Override
	public Point getCurrentSpeed() {

		Point result;
		
		if (getChild() != null) {
			
			result = getChild().getCurrentSpeed();
		} else {
			
			result = new Point(0, 0);
		}
		
		result.setX(result.getX() + speedVector.getX());
		result.setY(result.getY() + speedVector.getY());
		
		return result;
	}

	public void setSpeedVector(Point speedVector) {
		
		this.speedVector = speedVector;
	}
}
