package Base.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class ButtonAction implements Runnable {

	StateBasedGame game;
	GameContainer container;

	public ButtonAction(StateBasedGame game, GameContainer container) {
		
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
