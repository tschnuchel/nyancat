package Base.game.state;

import Base.game.Constants;
import Base.game.DataBaseManager;
import Base.menu.ButtonAction;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.ActionMap;
import de.matthiasmann.twl.Button;

import Base.TWLSlick.BasicTWLGameState;
import Base.TWLSlick.RootPane;

public class GameOverGameState extends BasicTWLGameState {

	private int id;
	private int score;
	private Button retryButton, menuButton;
	private GameContainer container;
	private StateBasedGame game;
	
	public GameOverGameState(int uniqueID) {
		
		this.id = uniqueID;
		this.score = 0;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		this.container = container;
		this.game = game;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		g.drawString(new Integer(score).toString(), 50, 50);
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
		
		retryButton = new Button("Retry");
		ButtonAction retryAction = new ButtonAction(game, container) {
			
			@Override
			public void run() {
				
				GameState gameViewGameState = game.getState(Constants.ID_GAMEVIEW);
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
		
		super.layoutRootPane();
	}
}
