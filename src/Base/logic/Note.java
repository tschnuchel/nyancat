package Base.logic;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import Base.game.ResourceManager;
import Base.logic.collision.Collidable;
import Base.movement.Movement;

public class Note extends Obstacle {

	private boolean isValid;
	private Image image;
	private float rpm;
	
	public Note(Point position, Movement movement) {
		super(new Rectangle(position.getX(), position.getY(), 40, 40), new Circle(position.getX() + 20, position.getY() + 20, 20), movement);
		
		Image note = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.NOTE);
		image = note.getScaledCopy(40f/ 128f);
		
		rpm = 180f;
		isValid = true;
	}

	@Override
	public void draw(Graphics g) {

		g.drawImage(image, boundingBox.getX(), boundingBox.getY());
	}

	@Override
	public boolean isValid() {
		
		return isValid;
	}

	@Override
	public boolean shouldCollideWith(Collidable collidable) {

		return collidable.getClass() == Planet.class;
	}

	@Override
	public void collidedWith(Collidable collidable) {

		collidable.acceptCollidable(this);	
	}

	@Override
	public void acceptCollidable(Planet collidable) {

		isValid = false;
	}
	
	@Override
	public void updatePosition(int delta) {
		
		float rotationDelta = (float) (2 * Math.PI * rpm * delta / 60000f);
		Transform rotation = Transform.createRotateTransform(rotationDelta);
		boundingBox.transform(rotation);
		boundingShape.transform(rotation);
		image.rotate((float) (360 * rotationDelta / (2 * Math.PI)));
		
		super.updatePosition(delta);
	}
}
