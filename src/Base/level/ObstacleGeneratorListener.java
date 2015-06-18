package Base.level;

import Base.logic.Obstacle;

public interface ObstacleGeneratorListener {

	public void generatorDidGenerateObstacle(ObstacleGenerator generator, Obstacle obstacle);
}
