package Base.logic.collision;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import Base.logic.Cat;
import Base.logic.JazzGoodie;
import Base.logic.LifeUpGoodie;
import Base.logic.Note;
import Base.logic.Planet;

public abstract class Collidable {

	public abstract Rectangle getBoundingBox();
	public abstract Shape getBoundingShape();
	public abstract boolean shouldCollideWith(Collidable collidable);
	public abstract void collidedWith(Collidable collidable);
	public void acceptCollidable(Cat collidable) {};
	public void acceptCollidable(Planet collidable) {};
	public void acceptCollidable(LifeUpGoodie collidable) {};
	public void acceptCollidable(JazzGoodie collidable) {};
	public void acceptCollidable(Note collidable) {};
}
