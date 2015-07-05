package Base.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.EditField.Callback;

public abstract class CallbackAction implements Callback {

	StateBasedGame game;
	GameContainer container;

	public CallbackAction(StateBasedGame game, GameContainer container) {
		
		this.game = game;
		this.container = container;
	}
	
	public StateBasedGame getGame() {
		return game;
	}

	public void setGame(StateBasedGame game) {
		this.game = game;
	}
}
