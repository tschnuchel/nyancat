package Base.logic;

import Base.movement.Movement;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

public class Planet extends Obstacle {

	private Image image, collisionImage1, collisionImage2;
	private Color color;
	private boolean collided = false;
	private Point collisionSpeed1, collisionSpeed2;
	private Point centerHalf1, centerHalf2;
	private int deathCounter = 2000;
	
	public Planet(float radius, Image image, Image crashImage1, Image crashImage2, Point center, Movement movement) {
		
		super(new Rectangle(center.getX() - radius, center.getY() - radius, 2 * radius, 2 * radius), new Circle(center.getX(), center.getY(), radius), movement);

		float scale = 2 * radius / image.getHeight();
		this.image = image.getScaledCopy(scale);
		this.collisionImage1 = crashImage1.getScaledCopy(scale);
		this.collisionImage2 = crashImage2.getScaledCopy(scale);
		
		float r = (float) (Math.random() * 0.5 + 0.5);
		float g = (float) (Math.random() * 0.5 + 0.5);
		float b = (float) (Math.random() * 0.5 + 0.5);
		this.color = new Color(r, g, b);
	}

	@Override
	public void draw(Graphics g) {
		
		if (!collided) {
			
			color.a = 1;
			g.setColor(color);
			g.drawImage(image, boundingBox.getX(), boundingBox.getY(), color);
//			g.setColor(Color.red);
//			g.draw(boundingBox);
//			g.setColor(Color.green);
//			g.draw(boundingShape);
			
		} else {
			
			color.a = deathCounter / 2000f;
			g.drawImage(collisionImage1, centerHalf1.getX(), centerHalf1.getY(), color);
			g.drawImage(collisionImage2, centerHalf2.getX(), centerHalf2.getY(), color);
		}
	}
	
	@Override
	public void updatePosition(int delta) {
		
		super.updatePosition(delta);
		
		if (collided) {
			
			centerHalf1.setX(centerHalf1.getX() + collisionSpeed1.getX() * delta / 1000f);
			centerHalf1.setY(centerHalf1.getY() + collisionSpeed1.getY() * delta / 1000f);
			centerHalf2.setX(centerHalf2.getX() + collisionSpeed2.getX() * delta / 1000f);
			centerHalf2.setY(centerHalf2.getY() + collisionSpeed2.getY() * delta / 1000f);
			
			float rpm = 60;
			float rotationDelta = (float) (2 * Math.PI * rpm * delta / 60000f);
			collisionImage1.rotate((float) (360 * rotationDelta / (2 * Math.PI)));
			collisionImage2.rotate((float) (-360 * rotationDelta / (2 * Math.PI)));
			
			deathCounter -= delta;
		}
	}

	@Override
	public boolean isValid() {
		return (boundingBox.getX() >= -boundingBox.getWidth()) && (deathCounter >= 0);
	}

	@Override
	public void setCollided(boolean collided) {
		
		if (collided) {
			
			Point collisionSpeed = movement.getCurrentSpeed();
			
			float cX = collisionSpeed.getX();
			float cY = collisionSpeed.getY();
			collisionSpeed1 = new Point((float)(Math.random() * 0.5 + 0.5) * cX, (float)(Math.random() * 0.5 + 0.5) * cY);
			collisionSpeed2 = new Point((float)(Math.random() * 0.5 + 0.5) * cX, (float)(Math.random() * 0.5 + 0.5) * cY);
			
			centerHalf1 = new Point(boundingBox.getX(), boundingBox.getY());
			centerHalf2 = new Point(boundingBox.getX(), boundingBox.getY());
			
			this.collided = collided;
		}
	}

	@Override
	public void acceptPlayer(Cat player) {

		player.removeLife();
	}
}
