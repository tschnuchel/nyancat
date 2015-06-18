package Base.logic;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class CollisionScene {

	private CollidableBACKUP object;
	private LinkedHashMap<CollidableBACKUP, CollisionDelegate> obstacleMapping;
	
	public CollisionScene(CollidableBACKUP object) {
		
		this.object = object;
		obstacleMapping = new LinkedHashMap<CollidableBACKUP, CollisionDelegate>();
	}

	public void triggerCollisionDelegates() {
		
		LinkedList<CollidableBACKUP> collisions = new LinkedList<CollidableBACKUP>();
		
		for (CollidableBACKUP collidable : obstacleMapping.keySet()) {
			
			boolean boxCrash = object.getBoundingBox().intersects(collidable.getBoundingBox());
			if (boxCrash) {
				
				boolean shapeCrash = object.getBoundingShape().intersects(collidable.getBoundingShape());
				if (shapeCrash) {
					
					collisions.add(collidable);
				}
			}
		}
		
		for (CollidableBACKUP collidable : collisions) {
			
			CollisionDelegate delegate = obstacleMapping.get(collidable);
			delegate.didCollideWithPlayer(collidable);
		}
	}
	
	public void addCollidable(CollidableBACKUP collidable, CollisionDelegate delegate) {
		
		obstacleMapping.put(collidable, delegate);
	}
	
	public void removeCollidable(CollidableBACKUP collidable) {
		
		obstacleMapping.remove(collidable);
	}
}
