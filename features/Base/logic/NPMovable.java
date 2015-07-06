package Base.logic;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import Base.movement.Movement;

public abstract class NPMovable extends Movable {

	Rectangle boundingBox;
	Shape boundingShape;
	Movement movement;
	
	public NPMovable(Rectangle boundingBox, Shape boundingShape, Movement movement) {

		this.boundingBox = boundingBox;
		this.boundingShape = boundingShape;
		this.movement = movement;
	}

	@Override
	public Rectangle getBoundingBox() {

		return boundingBox;
	}

	@Override
	public Shape getBoundingShape() {

		return boundingShape;
	}

	@Override
	public void updatePosition(int delta) {
		
		Point deltaMovement = movement.getDeltaMovement(delta);
		
		boundingBox.setX(boundingBox.getX() + deltaMovement.getX());
		boundingBox.setY(boundingBox.getY() + deltaMovement.getY());
		boundingShape.setX(boundingShape.getX() + deltaMovement.getX());
		boundingShape.setY(boundingShape.getY() + deltaMovement.getY());
	}

	public Movement getMovement() {
		return movement;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}
	
}
