package Base.movement;

import org.newdawn.slick.geom.Point;

import Base.logic.Movable;

public class GravitationMovement extends Movement {

	
	private static final int MIN_DISTANCE = 10;
	private static final int MAX_ACCELERATION = 1;
	private Movable center, object;
	private float mass;
	private Point speedVector;
	
	public GravitationMovement(Movable center, Movable object, float mass) {

		this.center = center;
		this.object = object;
		this.mass = mass;
		speedVector = new Point(0, 0);
	}

	public GravitationMovement(Movable center, Movable object, float mass, Point initialSpeed) {
		
		this.center = center;
		this.object = object;
		this.mass = mass;
		speedVector = initialSpeed;
	}
	
	@Override
	public Point getDeltaMovement(int delta) {

		Point result;
		
		Movement child = getChild();
		if (child != null) {
			
			result = child.getDeltaMovement(delta);
			
		} else {
			
			result = new Point(0, 0);
		}
		float difX = center.getBoundingBox().getCenterX() - object.getBoundingBox().getCenterX();
		float difY = center.getBoundingBox().getCenterY() - object.getBoundingBox().getCenterY();
		
		double accel = mass / Math.min(MAX_ACCELERATION, (difX * difX + difY * difY));
		double accelFactor = accel / Math.min(MIN_DISTANCE, Math.sqrt(difX * difX + difY * difY));
		
		double accelX = difX * accelFactor;
		double accelY = difY * accelFactor;

		double speedDeltaX = accelX * delta / 1000f; 
		double speedDeltaY = accelY * delta / 1000f; 
		speedVector.setX(new Float(speedVector.getX() + speedDeltaX));
		speedVector.setY(new Float(speedVector.getY() + speedDeltaY));
		
		float deltaX = speedVector.getX() * delta / 1000f;
		float deltaY = speedVector.getY() * delta / 1000f;
		
		result.setX(result.getX() + deltaX);
		result.setY(result.getY() + deltaY);
		
		return result;
	}

	@Override
	public Point getCurrentSpeed() {

		return speedVector;
	}

}
