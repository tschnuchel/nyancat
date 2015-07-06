package Base.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface KeyboardHandler {

	
	public void handle(GameContainer container, StateBasedGame game, int key, boolean released);
}
