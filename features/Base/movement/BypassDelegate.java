package Base.movement;

public interface BypassDelegate {

	public void update(int delta);
	public boolean shouldBypass();
}
