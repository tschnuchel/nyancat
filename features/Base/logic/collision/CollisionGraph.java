package Base.logic.collision;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.newdawn.slick.geom.Rectangle;

import Base.logic.JazzGoodie;

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
	
	private class MatrixEntry {
		
		private List<Collision> collisions;
		private CollisionDelegate delegate;

		public MatrixEntry(CollisionDelegate delegate) {
			
			this.collisions = new LinkedList<CollisionGraph.Collision>();
			this.delegate = delegate;
		}
		
		public CollisionDelegate getDelegate() {
			
			return delegate;
		}
		
		public List<Collision> getCollisions() {
			
			return collisions;
		}
		
		public void addCollision(Collision collision) {
			
			collisions.add(collision);
		}

		public void removeCollision(Collision collision) {
			
			collisions.remove(collision);
		}
	}
	
	private LinkedHashMap<Collidable, MatrixEntry> matrix;
	
	public CollisionGraph() {
		
		this.matrix = new LinkedHashMap<Collidable, MatrixEntry>();
	}
	
	public void addVertex(Collidable collidable, CollisionDelegate delegate) {
		
		// remove any existing entry
		matrix.remove(collidable);

		// collect collisions here
		MatrixEntry entry = new MatrixEntry(delegate);
		
		// update possible collisions
		Rectangle box1 = collidable.getBoundingBox();
		for (Collidable collidable2 : matrix.keySet()) {
			
			Rectangle box2 = collidable2.getBoundingBox();
			float minDistance = box1.getBoundingCircleRadius() + box2.getBoundingCircleRadius();

			if (collidable.shouldCollideWith(collidable2)) {
				
				entry.addCollision(new Collision(collidable2, minDistance));
			}
			
			if (collidable2.shouldCollideWith(collidable)) {
				
				MatrixEntry otherEntry = matrix.get(collidable2);
				otherEntry.addCollision(new Collision(collidable, minDistance));
			}
		}

		// add new entry
		matrix.put(collidable, entry);
	}
	
	public void removeVertex(Collidable collidable) {

		
		if ((collidable == null) || (matrix.get(collidable) == null)) {
			
			return;
		}
		
		// remove entries in lists of other collidables
		for (Collision collision : matrix.get(collidable).getCollisions()) {
			
			MatrixEntry otherEntry = matrix.get(collision.getCollidable());
			if ((otherEntry != null) && (otherEntry.getCollisions() != null)) {
				
				for (Collision collision2 : otherEntry.getCollisions()) {
					
					if (collision2.getCollidable() == collidable) {
						
						otherEntry.removeCollision(collision2);
						break;
					}
				}
			}
		}

		// remove entries with key collidable
		matrix.remove(collidable);
	}
	
	public void checkCollisions() {
		
		List<Collidable> collided = new LinkedList<Collidable>();
		List<CollisionDelegate> delegates = new LinkedList<CollisionDelegate>();
		
		for (Collidable collidable : matrix.keySet()) {
			
			MatrixEntry entry = matrix.get(collidable);
			for (Collision collision : entry.getCollisions()) {
				
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
						
						CollisionDelegate delegate = entry.getDelegate();
						if (delegate != null) {
							
							collided.add(collidable);
							delegates.add(entry.getDelegate());
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
