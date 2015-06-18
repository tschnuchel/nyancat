package Base.movement;

import org.newdawn.slick.geom.Point;

public class BypassMovement extends Movement {

	private Movement bypass;
	private BypassDelegate delegate;
	
	public BypassMovement(Movement bypass, BypassDelegate delegate) {
		
		this.bypass = bypass;
		this.delegate = delegate;
	}

	public BypassMovement(Movement bypass, BypassDelegate delegate, Movement child) {
		
		super(child);
		
		this.bypass = bypass;
		this.delegate = delegate;
	}

	@Override
	public Point getDeltaMovement(int delta) {
		
		delegate.update(delta);
		
		Point result;
		
		if (delegate.shouldBypass()) {
			
			if (bypass != null) {
				
				result = bypass.getDeltaMovement(delta);
			} else {
				
				result = new Point(0, 0);
			}
			
		} else {
			
			Movement child = getChild();
			if (child != null) {
				
				result = child.getDeltaMovement(delta);
				
			} else {
				
				result = new Point(0, 0);
			}
		}
		
		return result;
	}

	@Override
	public Point getCurrentSpeed() {
		
		Point result;
		
		if (delegate.shouldBypass()) {
			
			result = bypass.getCurrentSpeed();
			
		} else {
			
			Movement child = getChild();
			if (child != null) {
				
				result = child.getCurrentSpeed();
				
			} else {
				
				result = new Point(0, 0);
			}
		}
		
		return result;
	}

}
