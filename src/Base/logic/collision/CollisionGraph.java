package Base.logic.collision;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.newdawn.slick.geom.Rectangle;

public class CollisionGraph {

	private class Collision {
		
		private Collidable collidable;
		private float minDistance;
		private CollisionDelegate delegate;

		public Collision(Collidable collidable, float minDistance, CollisionDelegate delegate) {

			this.collidable = collidable;
			this.minDistance = minDistance;
			this.delegate = delegate;
		}

		public Collidable getCollidable() {
			return collidable;
		}

		public float getMinDistance() {
			return minDistance;
		}
		
		public CollisionDelegate getDelegate() {
			return delegate;
		}
	}
	
	private LinkedHashMap<Collidable, Set<Collision>> matrix;
	
	public CollisionGraph() {
		
		this.matrix = new LinkedHashMap<Collidable, Set<Collision>>();
	}
	
	public void addVertex(Collidable collidable, CollisionDelegate delegate) {
		
		// remove any existing entry
		matrix.remove(collidable);

		// collect collisions here
		Set<Collision> collisions = new LinkedHashSet<CollisionGraph.Collision>();
		
		// update possible collisions
		Rectangle box1 = collidable.getBoundingBox();
		for (Collidable collidable2 : matrix.keySet()) {
			
			Rectangle box2 = collidable2.getBoundingBox();
			float minDistance = box1.getBoundingCircleRadius() + box2.getBoundingCircleRadius();

			if (collidable.shouldCollideWith(collidable2)) {
				
				collisions.add(new Collision(collidable2, minDistance, delegate));
			}
			
			if (collidable2.shouldCollideWith(collidable)) {
				
				matrix.get(collidable2).add(new Collision(collidable, minDistance, null));
			}
		}

		// add new entry
		matrix.put(collidable, collisions);
	}
	
	public void removeVertex(Collidable collidable) {

		if ((collidable == null) || (matrix.get(collidable) == null)) {
			
			return;
		}
		
		for (Collision collision : matrix.get(collidable)) {
			
			for (Collision collision2 : matrix.get(collision.getCollidable())) {
				
				if (collision2.getCollidable() == collidable) {
					
					matrix.get(collision.getCollidable()).remove(collision2);
					break;
				}
			}
		}

		matrix.remove(collidable);
	}
	
	public void checkCollisions() {
		
		List<Collidable> collided = new LinkedList<Collidable>();
		List<CollisionDelegate> delegates = new LinkedList<CollisionDelegate>();
		
		for (Collidable collidable : matrix.keySet()) {
			
			for (Collision collision : matrix.get(collidable)) {
				
				Rectangle b1 = collidable.getBoundingBox();
				Rectangle b2 = collision.getCollidable().getBoundingBox();
				float xDif = (b2.getCenterX() - b1.getCenterX()) * (b2.getCenterX() - b1.getCenterX());
				float yDif = (b2.getCenterY() - b1.getCenterY()) * (b2.getCenterY() - b1.getCenterY());
				float centerSquareDistance = xDif + yDif;
				
				// approx collision
				if (centerSquareDistance <= Math.pow(collision.getMinDistance(), 2)) {
					
					// exact collision
					if (collidable.getBoundingShape().intersects(collision.getCollidable().getBoundingShape())) {
						collidable.collidedWith(collision.getCollidable());
						
						CollisionDelegate delegate = collision.getDelegate();
						if (delegate != null) {
							
							collided.add(collidable);
							delegates.add(collision.getDelegate());
						}
					}
				}
			}
		}
		
		// inform delegates
		for (int i = 0; i < delegates.size(); i++) {
			
			delegates.get(i).didCollide(collided.get(i));
		}
	}
}
