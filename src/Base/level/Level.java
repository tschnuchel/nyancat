package Base.level;

import java.util.LinkedList;
import java.util.List;

import Base.logic.Cat;
import Base.logic.CatListener;
import Base.logic.Obstacle;
import Base.logic.collision.Collidable;
import Base.logic.collision.CollisionDelegate;
import Base.logic.collision.CollisionGraph;
import Base.music.MusicManager;

public class Level implements ObstacleGeneratorListener, CatListener, CollisionDelegate {

	private ObstacleGenerator generator;
	private LinkedList<Obstacle> obstacles;
	private CollisionGraph collisionGraph;
	private Cat player;
	
	public Level(Cat player) {
		
		this.player = player;
		obstacles = new LinkedList<Obstacle>();
		collisionGraph = new CollisionGraph();
		collisionGraph.addVertex(player, null);
//		generator = new SimpleObstacleGenerator(100000000);
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
			collisionGraph.removeVertex(obstacle);
		}
	}
	
	public List<Obstacle> getObstacles() {
		
		return obstacles;
	}

	@Override
	public void generatorDidGenerateObstacle(ObstacleGenerator generator, Obstacle obstacle) {
		
		obstacles.add(obstacle);
		collisionGraph.addVertex(obstacle, this);
	}

	public CollisionGraph getCollisionGraph() {
		return collisionGraph;
	}

	public Cat getPlayer() {
		return player;
	}
	
	public boolean isGameOver() {
		
		return player.isDead();
	}

	@Override
	public void didEnterMode(Cat cat, CatMode mode) {
		
		MusicManager.getDefaultMusicManager().change();
		// TODO slow level down
	}

	@Override
	public void didShoot(Obstacle obstacle) {
		
		obstacles.add(obstacle);
		collisionGraph.addVertex(obstacle, this);
	}
	
	@Override
	public void didCollide(Collidable collidable) {
		
		this.collisionGraph.removeVertex(collidable);
	}
}
