package Base.level;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

import Base.game.Constants;
import Base.game.ResourceManager;
import Base.logic.Difficulty;
import Base.logic.JazzGoodie;
import Base.logic.LifeUpGoodie;
import Base.logic.Planet;

import Base.logic.CatListener.CatMode;

import Base.movement.CircularMovement;
import Base.movement.CircularToggleMovement;
import Base.movement.GravitationMovement;
import Base.movement.LinearMovement;
import Base.movement.Movement;

public class CrazyObstacleGenerator extends ObstacleGenerator {

	int counter = 500;
	int lifeUpCounter = 10000;
	int jazzCounter = 3000;
	  
	CatMode mode = CatMode.ORIGINAL;
	  
	// TODO featurizen
	private Planet planet;

	 






 

	@Override
	public void update(int delta) {
		counter -= delta;
		if (counter <= 0) {

			float radius = (float) (10 + Math.random() * 50);
			Image image = (Image) ResourceManager.getDefaultManager()
					.getResourceNamed(ResourceManager.PLANET_IMAGE);
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

			  
			switch (mode) {
			case ORIGINAL:
			  

				  
				 











 
				if (Math.random() >= 0.5) {

					movement = new CircularMovement(roundTimeMillis,
							circleRadius, clockwise, 0, linearMovement);

				} else {

					movement = linearMovement;
				}
				
				// Creating Planet for circular and linear movements
				
				planet = new Planet(radius, image, crashImage1, crashImage2,
						center, movement);
				notifyListeners(planet);
				
				  

				
				  
				
				// Creating Gravety movements and 2 Planets
				
				Image image1 = (Image) ResourceManager.getDefaultManager()
						.getResourceNamed(ResourceManager.PLANET_IMAGE2);
				Image crashImage11 = (Image) ResourceManager
						.getDefaultManager().getResourceNamed(
								ResourceManager.PLANET_CRASH1);
				Image crashImage21 = (Image) ResourceManager
						.getDefaultManager().getResourceNamed(
								ResourceManager.PLANET_CRASH2);

				float radius1 = (float) (10 + Math.random() * 50);
				float radius2 = (float) (10 + Math.random() * 50);
				float x1 = Constants.SCREEN_WIDTH + radius1;
				float y1 = (float) (Math.random() * Constants.SCREEN_HEIGHT);
				float x2 = Constants.SCREEN_WIDTH + radius2;
				float y2 = (float) (Math.random() * Constants.SCREEN_HEIGHT);

				Point center1 = new Point(x1, y1);
				Point center2 = new Point(x2, y2);
				// TODO hier evtl. bei Schwierigkeitsgrad anpassen
				float speedX1 = (float) (-100 - Math.random() * 150) ;
				float speedY1 = (float) (Math.random() * 300 - 150) ;

				float speedX2 = (float) (-100 - Math.random() * 150) ;
				float speedY2 = (float) (Math.random() * 300 - 150) ;

				Point speedVector1 = new Point(speedX1, speedY1);
				Point speedVector2 = new Point(speedX2, speedY2);

				Movement movement1 = new LinearMovement(speedVector1);
				Movement movement2 = new LinearMovement(speedVector2);

				Planet planet1 = new Planet(radius1, image1, crashImage11,
						crashImage21, center1, movement1);
				Planet planet2 = new Planet(radius2, image1, crashImage11,
						crashImage21, center2, movement2);

				float mass1 = radius1 / 12;
				float mass2 = radius2 / 12;

				
				// Setting Gravity Movement for Planet 2
				
				Movement gravityMovement1 = new GravitationMovement(planet1,
						planet2, mass1, new Point(-90, 80));
				gravityMovement1.setChild(planet2.getMovement());
				planet2.setMovement(gravityMovement1);
				notifyListeners(planet1);
				notifyListeners(planet2);

				// Setting Gravity Movement for Planet 1
				
				Movement gravityMovement2 = new GravitationMovement(planet2,
						planet1, mass2, new Point(+30, -50));
				gravityMovement2.setChild(planet1.getMovement());
				planet1.setMovement(gravityMovement2);
				notifyListeners(planet2);
				notifyListeners(planet1);

				  

				
				  
				break;

			case JAZZ:

				float sx = (float) (speedVector.getX() / 2f);
				float sy = (float) (speedVector.getY() / 2f);
				linearMovement.setSpeedVector(new Point(sx, sy));

				image = (Image) ResourceManager.getDefaultManager()
						.getResourceNamed(ResourceManager.PLANET_JAZZ_IMAGE);
				int jazzRoundTimeMillis = (int) (4921f);
				int jazzToggleTime = (int) (4921 / (2f));
				movement = new CircularToggleMovement(jazzRoundTimeMillis,
						circleRadius, Math.PI / 2, jazzToggleTime,
						linearMovement);
				
				planet = new Planet(radius, image, crashImage1, crashImage2,
						center, movement);
				notifyListeners(planet);

				break;

			default:
				break;
			}
			 

			counter = 500;
			
			
			counter = 1500;
			
			if(mode==CatMode.JAZZ)
				counter = 500;
			
			
			
			}// end IF Block
		
		lifeUpCounter -= delta;
		if (lifeUpCounter <= 0) {

			Point position = new Point(Constants.SCREEN_WIDTH + 15,
					(float) (Constants.SCREEN_HEIGHT * Math.random()) - 15);
			float LGSpeedVectorX = (float) (-100 - Math.random() * 200);
			float LGSpeedVectorY = (float) (Math.random() * 100 - 50);
			Movement lifeUpGoodieMovement = new LinearMovement(new Point(LGSpeedVectorX, LGSpeedVectorY));
			LifeUpGoodie lifeUpGoodie = new LifeUpGoodie(position, lifeUpGoodieMovement);

			notifyListeners(lifeUpGoodie);

			lifeUpCounter = 30000;
		}

		  
		jazzCounter -= delta;
		if (jazzCounter <= 0) {

			Point jazzGoodiePosition = new Point(Constants.SCREEN_WIDTH + 20,
					(float) (Constants.SCREEN_HEIGHT * Math.random()) - 20);
			float JGSpeedVectorX = (float) (-100 - Math.random() * 200);
			float JGSpeedVectorY = (float) (Math.random() * 100 - 50);
			Movement jazzGoodieMovement = new LinearMovement(new Point(JGSpeedVectorX, JGSpeedVectorY));
			JazzGoodie jazzGoodie = new JazzGoodie(jazzGoodiePosition, jazzGoodieMovement);

			notifyListeners(jazzGoodie);

			jazzCounter = 3000;
		}
		  
		
	} //end Update Methode

	 




 

	  
	public void setMode(CatMode mode) {

		this.mode = mode;
	}
	  

}
