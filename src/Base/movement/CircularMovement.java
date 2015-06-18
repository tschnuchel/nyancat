package Base.movement;

import org.newdawn.slick.geom.Point;

public class CircularMovement extends Movement {

	private int roundTimeMillis;
	private int counter;
	private float radius;
	private double lastX, lastY;
	private float speedX = 0, speedY = 0;
	private boolean clockwise;
	private double angleOffset;
	
	public CircularMovement(int roundTimeMillis, float radius, boolean clockwise, double angleOffset) {

		this.roundTimeMillis = roundTimeMillis;
		counter = roundTimeMillis;
		
		this.radius = radius;
		this.angleOffset = angleOffset;
		this.clockwise = clockwise;
	}

	public CircularMovement(int roundTimeMillis, float radius, boolean clockwise, double angleOffset, Movement child) {
		
		super(child);
		
		this.roundTimeMillis = roundTimeMillis;
		counter = roundTimeMillis;
		
		this.radius = radius;
		this.angleOffset = angleOffset;
		this.clockwise = clockwise;
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
