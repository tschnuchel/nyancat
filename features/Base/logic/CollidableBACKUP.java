package Base.logic;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class CollidableBACKUP {

	public abstract Rectangle getBoundingBox();
	public abstract Shape getBoundingShape();
	public abstract void setCollided(boolean collided);
	public abstract void acceptPlayer(Cat player);
}
