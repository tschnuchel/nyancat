package Base.logic.collision;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public interface Collidable {

	public Rectangle getBoundingBox();
	public Shape getBoundingShape();
	public boolean shouldCollideWith(Collidable collidable);
	public void collidedWith(Collidable collidable);
}
