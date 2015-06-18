package Base.level;

import Base.game.ResourceManager;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

import Base.logic.Obstacle;
import Base.logic.Planet;
import Base.movement.CircularMovement;
import Base.movement.Movement;

public abstract class ObstacleGenerator {

	private LinkedList<ObstacleGeneratorListener> listeners;
	
	public ObstacleGenerator() {
		
		listeners = new LinkedList<ObstacleGeneratorListener>();
	}
	
	public void addListener(ObstacleGeneratorListener listener) {
		
		listeners.add(listener);
	}

	public void removeListener(ObstacleGeneratorListener listener) {
		
		listeners.remove(listener);
	}
	
	void notifyListeners(Obstacle obstacle) {
		
		for (ObstacleGeneratorListener listener : listeners) {
			
			listener.generatorDidGenerateObstacle(this, obstacle);
		}
	}
	
	public abstract void update(int delta);
	
	public static List<Obstacle> createGalaxy(Point center, int planetCount, float radius, int cycleMillis, boolean clockwise, Movement movement) {
		
		LinkedList<Obstacle> result = new LinkedList<Obstacle>();
		
		for (int i = 0; i < planetCount; i++) {
			
			float planetRadius = 40;
			Image image = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_IMAGE2);
			Image crashImage1 = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_CRASH1);
			Image crashImage2 = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.PLANET_CRASH2);
			double angleOffset = i * 2 * Math.PI / planetCount;
			Movement planetMovement = new CircularMovement(cycleMillis, radius, clockwise, angleOffset, movement);
			Planet planet = new Planet(planetRadius, image, crashImage1, crashImage2, center, planetMovement);
			result.add(planet);
		}
		
		return result;
	}
}
