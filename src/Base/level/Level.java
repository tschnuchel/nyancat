package Base.level;

import java.util.LinkedList;
import java.util.List;

import Base.logic.Cat;
import Base.logic.CatListener;
import Base.logic.CollidableBACKUP;
import Base.logic.CollisionDelegate;
import Base.logic.CollisionScene;
import Base.logic.Obstacle;
import Base.music.MusicManager;

public class Level implements ObstacleGeneratorListener, CollisionDelegate, CatListener {

	private ObstacleGenerator generator;
	private LinkedList<Obstacle> obstacles;
	private CollisionScene collisionScene;
	private Cat player;
	
	public Level(Cat player) {
		
		this.player = player;
		obstacles = new LinkedList<Obstacle>();
		collisionScene = new CollisionScene(player);
//		generator = new SimpleObstacleGenerator(500000000);
//		generator = new LinearObstaclegenerator();
		generator = new CrazyObstacleGenerator();
		generator.addListener(this);
		player.setListener(this);
	}
	
	public void update(int delta) {
		
		generator.update(delta);
		
		LinkedList<Obstacle> removeThese = new LinkedList<Obstacle>();
		for (Obstacle obstacle : obstacles) {
			
			obstacle.updatePosition(delta);
			
			// if obstacle is outdated, mark it as removed
			if (!obstacle.isValid()) {
				
				removeThese.add(obstacle);
			}
		}
		
		// remove all marked obstacles
		for (Obstacle obstacle : removeThese) {
			
			obstacles.remove(obstacle);
			collisionScene.removeCollidable(obstacle);
		}
	}
	
	public List<Obstacle> getObstacles() {
		
		return obstacles;
	}

	@Override
	public void generatorDidGenerateObstacle(ObstacleGenerator generator, Obstacle obstacle) {
		
		obstacles.add(obstacle);
		collisionScene.addCollidable(obstacle, this);
	}

	public CollisionScene getCollisionScene() {
		return collisionScene;
	}

	public Cat getPlayer() {
		return player;
	}
	
	@Override
	public void didCollideWithPlayer(CollidableBACKUP collidable) {

		collisionScene.removeCollidable(collidable);
		collidable.setCollided(true);
		
		player.collide(collidable);
	}
	
	public boolean isGameOver() {
		
		return player.isDead();
	}

	@Override
	public void didEnterMode(Cat cat, CatMode mode) {
		
		MusicManager.getDefaultMusicManager().change();
		// TODO slow level down
	}
}
