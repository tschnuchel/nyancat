package Base.logic.collision;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.newdawn.slick.geom.Rectangle;

public class CollisionGraph {

	private class Collision {
		
		private Collidable collidable;
		private float minDistance;

		public Collision(Collidable collidable, float minDistance) {

			this.collidable = collidable;
			this.minDistance = minDistance;
		}

		public Collidable getCollidable() {
			return collidable;
		}

		public float getMinDistance() {
			return minDistance;
		}
	}
	
	private LinkedHashMap<Collidable, Set<Collision>> matrix;
	
	public CollisionGraph() {
		
		this.matrix = new LinkedHashMap<Collidable, Set<Collision>>();
	}
	
	public void addVertex(Collidable collidable) {
		
		// remove any existing entry
		matrix.remove(collidable);
		
		// add new entry
		Set<Collision> collisions = new LinkedHashSet<CollisionGraph.Collision>();
		matrix.put(collidable, collisions);
		
		// update possible collisions
		Rectangle box1 = collidable.getBoundingBox();
		for (Collidable collidable2 : matrix.keySet()) {
			
			Rectangle box2 = collidable2.getBoundingBox();
			float minDistance = box1.getBoundingCircleRadius() + box2.getBoundingCircleRadius();

			if (collidable.shouldCollideWith(collidable2)) {
				
				collisions.add(new Collision(collidable2, minDistance));
			}
			
			if (collidable2.shouldCollideWith(collidable)) {
				
				matrix.get(collidable2).add(new Collision(collidable2, minDistance));
			}
		}
	}
	
	public void removeVertex(Collidable collidable) {
		
		matrix.remove(collidable);
	}
	
	public void checkCollisions() {
		
		for (Collidable collidable : matrix.keySet()) {
			
			for (Collision collision : matrix.get(collidable)) {
				
				Rectangle b1 = collidable.getBoundingBox();
				Rectangle b2 = collision.getCollidable().getBoundingBox();
				float xDif = (b2.getCenterX() - b1.getCenterX()) * (b2.getCenterX() - b1.getCenterX());
				float yDif = (b2.getCenterY() - b1.getCenterY()) * (b2.getCenterY() - b1.getCenterY());
				float centerSquareDistance = xDif + yDif;
				
				// approx collision
				if (centerSquareDistance <= collision.getMinDistance()) {
					
					// exact collision
					if (collidable.getBoundingShape().intersects(collision.getCollidable().getBoundingShape())) {
						
						collidable.collidedWith(collision.getCollidable());
					}
				}
			}
		}
	}
}
