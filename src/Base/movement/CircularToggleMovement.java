package Base.movement;

import org.newdawn.slick.geom.Point;

public class CircularToggleMovement extends Movement {

	private int roundTimeMillis;
	private int counter;
	private float radius;
	private double lastX, lastY;
	private float speedX = 0, speedY = 0;
	private boolean clockwise;
	private double angleOffset;
	private int toggleTime;
	private int toggleTimeCounter = 0;
	
	public CircularToggleMovement(int roundTimeMillis, float radius, double angleOffset, int toggleTime) {

		this.roundTimeMillis = roundTimeMillis;
		counter = roundTimeMillis;
		
		this.clockwise = false;
		this.radius = radius;
		this.angleOffset = angleOffset;
		this.toggleTime = toggleTime;
	}

	public CircularToggleMovement(int roundTimeMillis, float radius, double angleOffset, int toggleTime, Movement child) {
		
		super(child);
		
		this.roundTimeMillis = roundTimeMillis;
		counter = roundTimeMillis;
		
		this.clockwise = true;
		this.radius = radius;
		this.angleOffset = angleOffset;
		this.toggleTime = toggleTime;
	}

	@Override
	public Point getDeltaMovement(int delta) {
		
		Point result;
		
		toggleTimeCounter -= delta;
		if (toggleTimeCounter <= 0) {
			
			toggleTimeCounter = toggleTime;
			clockwise = !clockwise;
		}
		
		Movement child = getChild();
		if (child != null) {
			
			result = child.getDeltaMovement(delta);
			
		} else {
			
			result = new Point(0, 0);
		}
		
		double nowX, nowY;
		if (clockwise) {
			
			nowX = Math.sin(2 * Math.PI * (counter - delta) / roundTimeMillis + angleOffset) * 2 * radius;
			nowY = Math.sin(2 * Math.PI * (counter - delta) / roundTimeMillis + Math.PI / 2.0 + angleOffset) * 2 * radius;
		}  else {
			
			nowX = Math.sin(2 * Math.PI - 2 * Math.PI * (counter - delta) / roundTimeMillis + angleOffset) * 2 * radius;
			nowY = Math.sin(2 * Math.PI - 2 * Math.PI * (counter - delta) / roundTimeMillis + Math.PI / 2.0 + angleOffset) * 2 * radius;
		}

		if (counter <= 0) {
			
			counter += roundTimeMillis;
		}
		
		counter -= delta;
		
		// update speed
		speedX = (float) ((nowX - lastX) * 1000f / delta);
		speedY = (float) ((nowY - lastY) * 1000f / delta);
		
		// create result
		result.setX((float) (result.getX() + (nowX - lastX)));
		result.setY((float) (result.getY() + (nowY - lastY)));
		
		lastX = nowX;
		lastY = nowY;
		
		return result;
	}

	@Override
	public Point getCurrentSpeed() {

		return new Point(speedX, speedY);
	}
	
}
