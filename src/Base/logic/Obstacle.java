package Base.logic;

import Base.movement.Movement;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import Base.game.Drawable;

public abstract class Obstacle extends NPMovable implements Drawable {

	public Obstacle(Rectangle boundingBox, Shape boundingShape, Movement movement) {
		
		super(boundingBox, boundingShape, movement);
	}

}
