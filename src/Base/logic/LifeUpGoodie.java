package Base.logic;

import Base.movement.Movement;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

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
	public void setCollided(boolean collided) {
		
		if (collided) {
			
			isValid = false;
		}
	}

	@Override
	public void acceptPlayer(Cat player) {

		player.setLifes(player.getLifes() + 1);
	}

}
