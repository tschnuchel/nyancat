package Base.game.state;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import Base.TWLSlick.BasicTWLGameState;
import Base.TWLSlick.RootPane;
import Base.game.Constants;
import Base.game.DataBaseManager;
import Base.game.DataBaseManager.Scoring;
import Base.menu.ButtonAction;
import de.matthiasmann.twl.ActionMap;
import de.matthiasmann.twl.Button;

public class GameOverGameState extends BasicTWLGameState {

	private int id;
	private int score;
	private Button retryButton, menuButton, mainMenuButton;
	private GameContainer container;
	private StateBasedGame game;
	private List<Scoring> scores;
	
	public GameOverGameState(int uniqueID) {
		
		this.id = uniqueID;
		this.score = 0;
		this.scores = DataBaseManager.getDefaultManager().getTop(10);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		this.container = container;
		this.game = game;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		
		super.enter(container, game);
		
		this.scores = DataBaseManager.getDefaultManager().getTop(10);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		String scoreString = "Your score: "+score;
		g.drawString(scoreString, 50, 70);
		
		int i = 1;
		for (Scoring scoring : scores) {
			
			String line = i + ": " + scoring.getScore() + " (" + scoring.getNickname() + ")";
			g.drawString(line, 50, 100 + i * 20);
			i++;
		}
	}

	@Override
	public int getID() {
		
		return id;
	}
	
	public void setScore(int score) {
		
		this.score = score;
	}

	@Override
	protected RootPane createRootPane() {
		
		RootPane rootpane = super.createRootPane();
		
		
		mainMenuButton = new Button("Main Menu");
		ButtonAction mainMenuAction = new ButtonAction(game, container) {
			
			@Override
			public void run() {
				GameState gameState = game.getState(Constants.ID_MENU);
				try {
					gameState.init(container, game);
					game.init(container);
					game.enterState(Constants.ID_MENU);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				
			}
		};
		mainMenuButton.addCallback(mainMenuAction);
		rootpane.add(mainMenuButton);
		
		retryButton = new Button("Retry");
		ButtonAction retryAction = new ButtonAction(game, container) {
			
			@Override
			public void run() {
				
				GameViewGameState gameViewGameState = (GameViewGameState) game.getState(Constants.ID_GAMEVIEW);
				try {
					
					gameViewGameState.init(container, game);
					game.enterState(Constants.ID_GAMEVIEW);
					
				} catch (SlickException e) {

					e.printStackTrace();
				}
			}
		};
		retryButton.addCallback(retryAction);
		rootpane.add(retryButton);
        
		menuButton = new Button("Quit");
		ButtonAction quitAction = new ButtonAction(game, container) {
			
			@Override
			public void run() {
				
				DataBaseManager.getDefaultManager().close();
				container.exit();
			}
		};
		menuButton.addCallback(quitAction);
		rootpane.add(menuButton);

		// set actionmapping and focus
        ActionMap actionMap = rootpane.getOrCreateActionMap();
        actionMap.addMapping("tab", rootpane, "focusNextChild", new Object[0], ActionMap.FLAG_ON_PRESSED);
        actionMap.addMapping("shiftTab", rootpane, "focusPrevChild", new Object[0], ActionMap.FLAG_ON_PRESSED);

        return rootpane;
	}
	
	@Override
	protected void layoutRootPane() {
		
		int yPosition = 550;
		int buttonWidth = 200;
		int buttonHeight = 60;
		int horizontalSpace = 45;
		
		Button[] buttons = {retryButton, menuButton};
		
		for (int i = 0; i < buttons.length; i++) {
			
			Button button = buttons[i];
			button.setSize(buttonWidth, buttonHeight);
			int x = (Constants.SCREEN_WIDTH - buttons.length * buttonWidth - (buttons.length - 1) * horizontalSpace) / 2 + i * (buttonWidth + horizontalSpace);
			int y = yPosition;
			button.setPosition(x, y);
		}
		
		mainMenuButton.setSize(buttonWidth*2+horizontalSpace, buttonHeight);
		int x = (Constants.SCREEN_WIDTH - buttons.length * buttonWidth - (buttons.length - 1) * horizontalSpace) / 2;
		int verticalSpace = 60;
		int y = yPosition-verticalSpace;
		mainMenuButton.setPosition(x, y);
		
		super.layoutRootPane();
	}
}
