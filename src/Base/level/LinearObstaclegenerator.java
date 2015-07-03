package Base.level;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

import Base.game.Constants;
import Base.game.ResourceManager;
import Base.logic.Planet;
import Base.movement.LinearMovement;
import Base.movement.Movement;

public class LinearObstaclegenerator extends ObstacleGenerator {

	private int counter = 500;
	
	@Override
	public void update(int delta) {
		
		counter -= delta;
		if (counter <= 0) {
			
			float radius = (float) (10 + Math.random() * 50);
			Image image = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_IMAGE);
			Image crashImage1 = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_CRASH1);
			Image crashImage2 = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_CRASH2);
			float x = Constants.SCREEN_WIDTH + radius;
			float y = (float) (Math.random() * Constants.SCREEN_HEIGHT);
			Point center = new Point(x, y);
			float speedX = (float) (-100 - Math.random() * 500);
			float speedY = (float) (Math.random() * 300 - 150);
			Point speedVector = new Point(speedX, speedY);
			Movement movement = new LinearMovement(speedVector );
			Planet planet = new Planet(radius, image, crashImage1, crashImage2, center, movement);
			counter = (int) (100 + Math.random() * 500);
			
			notifyListeners(planet);
		}
	}

}
