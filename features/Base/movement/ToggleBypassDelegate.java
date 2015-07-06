package Base.movement;

import java.util.ArrayList;

public class ToggleBypassDelegate implements BypassDelegate {

	private boolean shouldBypass;
	private int counter;
	private ArrayList<Integer> timings;
	private int next;
	private boolean rewind;
	
	public ToggleBypassDelegate(int[] timings, boolean rewind) {

		this.timings = new ArrayList<Integer>();
		for (int i = 0; i < timings.length; i++) {
			
			this.timings.add(timings[i]);
		}

		if (!this.timings.isEmpty()) {
			
			this.counter = this.timings.get(0);
			
		} else {
			
			this.counter = 0;
		}
		
		next = 1;
		
		this.rewind = rewind;
		this.shouldBypass = false;
	}
	
	@Override
	public void update(int delta) {
		
		counter -= delta;
		if (counter <= 0) {
			
			if (next < timings.size()) {
				
				shouldBypass = !shouldBypass;
				counter = timings.get(next);
				next++;
				if (rewind && (next >= timings.size())) {
					
					next = 0;
				}
				
			} else {
				
				shouldBypass = false;
			}
		}
	}

	@Override
	public boolean shouldBypass() {

		return shouldBypass;
	}
	
	
}
