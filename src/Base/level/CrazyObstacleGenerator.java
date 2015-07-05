package Base.level;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

import Base.game.Constants;
import Base.game.ResourceManager;
import Base.logic.Difficulty;
import Base.logic.JazzGoodie;
import Base.logic.LifeUpGoodie;
import Base.logic.Planet;
import Base.movement.CircularMovement;
import Base.movement.LinearMovement;
import Base.movement.Movement;

public class CrazyObstacleGenerator extends ObstacleGenerator {

	int counter = 500;
	int lifeUpCounter = 10000;
	int jazzCounter = 3000;

	/* if[Schwierigkeitsgrade] */
	private Difficulty difficulty = Difficulty.MIDDLE;

	public CrazyObstacleGenerator(Difficulty difficulty) {
		this.difficulty=difficulty;
	}
	/*end[Schwierigkeitsgrade]*/
	
	
	
	@Override
	public void update(int delta) {
		
		counter -= delta;
		if (counter <= 0) {
			float radius = (float) (10 + Math.random() * 50);
			Image image = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_IMAGE);
			Image crashImage1 = (Image) ResourceManager.getDefaultManager()
					.getResourceNamed(ResourceManager.PLANET_CRASH1);
			Image crashImage2 = (Image) ResourceManager.getDefaultManager()
					.getResourceNamed(ResourceManager.PLANET_CRASH2);
			float x = Constants.SCREEN_WIDTH + radius;
			float y = (float) (Math.random() * Constants.SCREEN_HEIGHT);
			Point center = new Point(x, y);
			float speedX = (float) (-100 - Math.random() * 350);
			float speedY = (float) (Math.random() * 300 - 150);
			Point speedVector = new Point(speedX, speedY);
			Movement linearMovement = new LinearMovement(speedVector);
			Movement movement = null;

			if (Math.random() >= 0.5) {

				int roundTimeMillis = (int) (2000 + Math.random() * 2000);
				float circleRadius = (float) (80 + Math.random() * 50);
				boolean clockwise = (Math.random() > 0.5);
				System.out.println("DAVOR: "+roundTimeMillis+", "+circleRadius+" at leve "+difficulty);
				/* if[Schwierigkeitsgrade] */
				roundTimeMillis = (int) (roundTimeMillis /  difficulty.getDifficulty());
				circleRadius = circleRadius / (float) difficulty.getDifficulty();
				/* end[Schwierigkeitsgrade] */
				System.out.println("DANACH: "+roundTimeMillis+", "+circleRadius);
				System.out.println("-------------------------------------------------");
				movement = new CircularMovement(roundTimeMillis, circleRadius, clockwise, 0, linearMovement);

			} else {

				movement = linearMovement;
			}

			Planet planet = new Planet(radius, image, crashImage1, crashImage2, center, movement);
			// counter = (int) (100 + Math.random() * 1000);
			counter = 500;

			notifyListeners(planet);
		}

		lifeUpCounter -= delta;
		if (lifeUpCounter <= 0) {

			Point position = new Point(Constants.SCREEN_WIDTH + 15,
					(float) (Constants.SCREEN_HEIGHT * Math.random()) - 15);
			float x = (float) (-100 - Math.random() * 200);
			float y = (float) (Math.random() * 100 - 50);
			Movement movement = new LinearMovement(new Point(x, y));
			LifeUpGoodie goodie = new LifeUpGoodie(position, movement);

			notifyListeners(goodie);

			lifeUpCounter = 30000;
		}

		jazzCounter -= delta;
		if (jazzCounter <= 0) {

			Point position = new Point(Constants.SCREEN_WIDTH + 20,
					(float) (Constants.SCREEN_HEIGHT * Math.random()) - 20);
			float x = (float) (-100 - Math.random() * 200);
			float y = (float) (Math.random() * 100 - 50);
			Movement movement = new LinearMovement(new Point(x, y));
			JazzGoodie goodie = new JazzGoodie(position, movement);

			notifyListeners(goodie);

			jazzCounter = 3000;

		}
	}
	
	@Override
	public void setDifficulty(Difficulty difficulty){
		this.difficulty = difficulty;
	}
}
