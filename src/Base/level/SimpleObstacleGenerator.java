package Base.level;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

import Base.game.Constants;
import Base.game.ResourceManager;
import Base.logic.Planet;
import Base.movement.CircularMovement;
import Base.movement.LinearMovement;
import Base.movement.Movement;

public class SimpleObstacleGenerator extends ObstacleGenerator {

	private int rate;
	private int counter;

	public SimpleObstacleGenerator(int rate) {

		this.rate = rate;
		counter = 0;
	}

	@Override
	public void update(int delta) {

		counter -= delta;
		
		if (counter <= 0) {
			
			counter = rate;
			
			/*
			float radius = 40;
			Image image = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_IMAGE2);
			Image crashImage1 = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_CRASH1);
			Image crashImage2 = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_CRASH2);
			Point center1 = new Point(600, 250);
			Point center2 = new Point(750, 350);
			Movement movement = new LinearMovement(new Point(0, 0));
			Planet planet1 = new Planet(radius, image, crashImage1, crashImage2, center1, movement);
			Planet planet2 = new Planet(radius, image, crashImage1, crashImage2, center2, movement);
			
			Movement gravityMovement1 = new GravitationMovement(planet1, planet2, 5, new Point(-60, 50));
			gravityMovement1.setChild(planet2.getMovement());
			planet2.setMovement(gravityMovement1);
			notifyListeners(planet1);
			notifyListeners(planet2);

			Movement gravityMovement2 = new GravitationMovement(planet2, planet1, 10, new Point(+30, -50));
			gravityMovement2.setChild(planet1.getMovement());
			planet1.setMovement(gravityMovement2);
			notifyListeners(planet2);
			notifyListeners(planet1);
			*/
			
			float radius = 60;
			Point center = new Point(Constants.SCREEN_WIDTH + radius, Constants.SCREEN_HEIGHT / 2f);
			int planetCount = 5;
			int cycleMillis = 5000;
			boolean clockwise = false;
			Movement movement = new LinearMovement(new Point(-100, 0));
//			List<Obstacle> galaxy = ObstacleGenerator.createGalaxy(center, planetCount, radius, cycleMillis, clockwise, movement);
//			for (Obstacle obstacle : galaxy) {
//				
//				notifyListeners(obstacle);
//			}
			
			float planetRadius = 40;
			Image image = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_IMAGE2);
			Image crashImage1 = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_CRASH1);
			Image crashImage2 = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_CRASH2);
			Movement planetMovement = new CircularMovement(cycleMillis, radius, clockwise, 0, movement);
			Planet planet = new Planet(planetRadius, image, crashImage1, crashImage2, center, planetMovement);
			notifyListeners(planet);
		}
	}
}
