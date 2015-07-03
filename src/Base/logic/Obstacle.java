package Base.logic;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import Base.game.Drawable;
import Base.movement.Movement;

public abstract class Obstacle extends NPMovable implements Drawable {

	public Obstacle(Rectangle boundingBox, Shape boundingShape, Movement movement) {
		
		super(boundingBox, boundingShape, movement);
	}

}
