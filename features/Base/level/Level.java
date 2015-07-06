package Base.level;

import java.util.LinkedList;
import java.util.List;

import Base.logic.Cat;
import Base.logic.CatListener;
import Base.logic.Difficulty;
import Base.logic.Obstacle;
import Base.logic.collision.Collidable;
import Base.logic.collision.CollisionDelegate;
import Base.logic.collision.CollisionGraph;
import Base.music.MusicManager;

public class Level implements ObstacleGeneratorListener, CatListener, CollisionDelegate {

	private CrazyObstacleGenerator generator;
	private LinkedList<Obstacle> obstacles;
	private CollisionGraph collisionGraph;
	private Cat player;
	/* if[Schwierigkeitsgrade] */
	private Difficulty difficulty = Difficulty.MIDDLE;
	/* end[Schwierigkeitsgrade] */

	public Level(Cat player) {

		this.player = player;
		obstacles = new LinkedList<Obstacle>();
		collisionGraph = new CollisionGraph();
		collisionGraph.addVertex(player, null);
		// generator = new SimpleObstacleGenerator(1000);
		// generator = new LinearObstaclegenerator();

		/* if[Schwierigkeitsgrade] */
		generator = new CrazyObstacleGenerator(difficulty);
		/* end[Schwierigkeitsgrade] */
		// TODO if not

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

	/*if[JazzMode]*/
	@Override
	public void didEnterMode(Cat cat, CatMode mode) {
		if(mode.equals(CatMode.JAZZ)){
		MusicManager.getDefaultMusicManager().loadSoundEffect("OGG", "/res/sound/nyan_loop_jazz.ogg");
		MusicManager.getDefaultMusicManager().playMusic("/res/sound/nyan_loop_jazz.ogg");//TODO Konstante
		}else{
			MusicManager.getDefaultMusicManager().loadSoundEffect("OGG", "/res/sound/nyan_loop_original.ogg");
			MusicManager.getDefaultMusicManager().playMusic("/res/sound/nyan_loop_original.ogg");//TODO Konstante
		}
		generator.setMode(mode);
	}
	/*end[JazzMode]*/

	/*if[Noten_schiessen]*/
	@Override
	public void didShoot(Obstacle obstacle) {

		obstacles.add(obstacle);
		collisionGraph.addVertex(obstacle, this);
	}
	/*end[Noten_schiessen]*/

	@Override
	public void didCollide(Collidable collidable) {

		this.collisionGraph.removeVertex(collidable);
	}

	public Difficulty getDifficulty() {
		return this.difficulty;
	}

	public void toggleDifficulty() {
		switch (difficulty) {
		case EASY:
			difficulty = Difficulty.MIDDLE;
			break;
		case MIDDLE:
			difficulty = Difficulty.HARD;
			break;
		case HARD:
			difficulty = Difficulty.EASY;
			break;
		default:
		}
		System.out.println("Generator ID: "+generator);
		this.generator.setDifficulty(difficulty);
	}
}
