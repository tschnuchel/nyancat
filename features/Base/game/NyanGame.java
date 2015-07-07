package Base.game;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import Base.TWLSlick.TWLStateBasedGame;
import Base.game.state.CreditsGameState;
import Base.game.state.GameOverGameState;
import Base.game.state.GameViewGameState;
import Base.game.state.MenuGameState;
import Base.music.MusicManager;

public class NyanGame extends TWLStateBasedGame {

	public static void main(String[] args) {
		
		
		// load natives dynamically
		String file = new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath();
		System.out.println(file);
		System.setProperty("org.lwjgl.librarypath", file);
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		
		StateBasedGame game = new NyanGame("Nyan");
		
		AppGameContainer container = null;
		
		try {
			
			container = new AppGameContainer(game);
			
			container.setDisplayMode(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, false);
			container.setAlwaysRender(true);
			container.start();
			
		} catch (SlickException e) {

			e.printStackTrace();
		}
	}
	
	public NyanGame(String name) {
		
		super(name);
		/*if[Highscore]*/
		DataBaseManager.getDefaultManager();
		/*end[Highscore]*/
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {

		GameState loadingState = new MenuGameState(Constants.ID_MENU);
		GameState gameState = new GameViewGameState(Constants.ID_GAMEVIEW);
		gameState.pauseUpdate();
		GameState gameOverState = new GameOverGameState(Constants.ID_GAMEOVER);
		GameState creditsGameState = new CreditsGameState(Constants.ID_CREDITS);
		this.addState(loadingState);
		this.addState(gameState);
		this.addState(gameOverState);
		this.addState(creditsGameState);
	}

	@Override
	protected URL getThemeURL() {
		
		URL result = null;
		try {
			
			result = new File("res/layout/Nyan.xml").toURI().toURL();
			
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		return result;
	}
	
}
