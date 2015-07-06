package Base.logic;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import Base.logic.collision.Collidable;
import Base.movement.Movement;

public class LifeUpGoodie extends Obstacle {

	private Color[] rainbowColors;
	private boolean isValid;

	public LifeUpGoodie(Point position, Movement movement) {
		
		super(new Rectangle(position.getX(), position.getY(), 30, 30),new Rectangle(position.getX(), position.getY(), 30, 30) , movement);
		
		// init rainbow colors
		rainbowColors = new Color[6];
		rainbowColors[0] = new Color(237, 38, 34);
		rainbowColors[1] = new Color(242, 179, 16);
		rainbowColors[2] = new Color(250, 255, 0);
		rainbowColors[3] = new Color(113, 255, 1);
		rainbowColors[4] = new Color(92, 180, 255);
		rainbowColors[5] = new Color(137, 71, 255);
		
		isValid = true;
	}

	@Override
	public void draw(Graphics g) {
		
		float segmentheight = boundingBox.getHeight() / rainbowColors.length;
		for (int i = 0; i < rainbowColors.length; i++) {
			
			g.setColor(rainbowColors[i]);
			g.fillRect(boundingBox.getX(), boundingBox.getY() + i * segmentheight, boundingBox.getWidth(), segmentheight);
		}
	}

	@Override
	public boolean isValid() {

		return isValid;
	}

	@Override
	public boolean shouldCollideWith(Collidable collidable) {

		return collidable.getClass() == Cat.class;
	}

	@Override
	public void collidedWith(Collidable collidable) {
		
		collidable.acceptCollidable(this);
	}

	@Override
	public void acceptCollidable(Cat collidable) {
		
		isValid = false;
	}

}
