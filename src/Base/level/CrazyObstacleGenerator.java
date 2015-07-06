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
import Base.movement.CircularToggleMovement;
import Base.movement.LinearMovement;
import Base.movement.Movement;

public class CrazyObstacleGenerator extends ObstacleGenerator {

	int counter = 500;
	int lifeUpCounter = 10000;
	int jazzCounter = 3000;
	



	
	  
	private Difficulty difficulty = Difficulty.MIDDLE;

	public CrazyObstacleGenerator(Difficulty difficulty) {
		this.difficulty=difficulty;
	}
	
	
	
	
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
			LinearMovement linearMovement = new LinearMovement(speedVector);
			Movement movement = null;

			
			int roundTimeMillis = (int) (2000 + Math.random() * 2000);
			float circleRadius = (float) (80 + Math.random() * 50);
			boolean clockwise = (Math.random() > 0.5);

			



				//TODO Easy: weniger planeten generieren
				//TODO JazzMode bei easy: seeeeehr schnell...
				//System.out.println("DAVOR: "+roundTimeMillis+", "+circleRadius+" at leve "+difficulty);
				  
				roundTimeMillis = (int) (roundTimeMillis *  difficulty.getDifficulty());
				circleRadius = circleRadius * (float)difficulty.getDifficulty();
				
				float dx = (float) (speedVector.getX() / (difficulty.getDifficulty()));
				float dy = (float) (speedVector.getY() / (difficulty.getDifficulty()));
				linearMovement.setSpeedVector(new Point(dx, dy));
				
				
				  
				//System.out.println("DANACH: "+roundTimeMillis+", "+circleRadius);
				//System.out.println("-------------------------------------------------");
				if (Math.random() >= 0.5) {
					
					movement = new CircularMovement(roundTimeMillis, circleRadius, clockwise, 0, linearMovement);
					
				} else {
					
					movement = linearMovement;
				}
				
			




















			counter = 500;
			Planet planet = new Planet(radius, image, crashImage1, crashImage2, center, movement);
			// counter = (int) (100 + Math.random() * 1000);

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

		















	}
	
	
	public void setDifficulty(Difficulty difficulty){
		this.difficulty = difficulty;
		System.out.println("CrazyObstacleGenerator: Difficulty set to "+this.difficulty);
	}
	
	
	





	
}
