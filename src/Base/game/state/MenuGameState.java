package Base.game.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Base.TWLSlick.BasicTWLGameState;
import Base.TWLSlick.RootPane;
import Base.game.Constants;
import Base.game.DataBaseManager;
import Base.game.NyanGame;
import Base.menu.ButtonAction;
import Base.menu.CallbackAction;
import Base.music.MusicManager;
import de.matthiasmann.twl.ActionMap;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.TextArea;
import de.matthiasmann.twl.ToggleButton;

public class MenuGameState extends BasicTWLGameState {

	private int id;
	private Button newGameButton, quitButton, toggleDifficultyButton;
	private NyanGame game;
	private GameContainer container;
	private EditField editField;
	private MusicManager musicManager = MusicManager.getDefaultMusicManager();
	
	public MenuGameState(int uniqueID) {

		this.id = uniqueID;

		


	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		this.game = (NyanGame) game;
		this.container = container;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

	}

	@Override
	public int getID() {

		return id;
	}

	@Override
	protected RootPane createRootPane() {

		final RootPane rootpane = super.createRootPane();

		// add newGameButton
		newGameButton = new Button("New Game");
		ButtonAction newGameAction = new ButtonAction(game, container) {

			@Override
			public void run() {
				



				FadeOutTransition leave = new FadeOutTransition(Color.black,
						100);
				FadeInTransition enter = new FadeInTransition(Color.black, 100);

				game.enterState(Constants.ID_GAMEVIEW, leave, enter);
				game.getState(Constants.ID_GAMEVIEW).setUpdatePaused(false);
			}
		};
		newGameButton.addCallback(newGameAction);
		rootpane.add(newGameButton);

		// add toggleDifficultyButton
		
		



































		// add quitButton
		quitButton = new Button("Quit");
		ButtonAction quitAction = new ButtonAction(game, container) {

			@Override
			public void run() {
				


				container.exit();
			}
		};
		quitButton.addCallback(quitAction);
		rootpane.add(quitButton);

		// add editField
		

















		
		// set actionMapping and focus
		newGameButton.requestKeyboardFocus();
		ActionMap actionMap = rootpane.getOrCreateActionMap();
		actionMap.addMapping("tab", rootpane, "focusNextChild", new Object[0],
				ActionMap.FLAG_ON_PRESSED);
		actionMap.addMapping("shiftTab", rootpane, "focusPrevChild",
				new Object[0], ActionMap.FLAG_ON_PRESSED);

		return rootpane;
	}

	@Override
	protected void layoutRootPane() {

		int buttonWidth = 500;
		int buttonHeight = 60;
		int verticalSpace = 20;

		Button[] buttons = { newGameButton, quitButton };

		for (int i = 0; i < buttons.length; i++) {

			Button button = buttons[i];

			if (button == newGameButton) {
				
















				
				

				button.setSize(buttonWidth, buttonHeight);
				int y = (Constants.SCREEN_HEIGHT - buttons.length
						* (buttonHeight + verticalSpace) - verticalSpace)
						/ 2 + i * (buttonHeight + verticalSpace);
				button.setPosition((Constants.SCREEN_WIDTH - buttonWidth) / 2, y);
				
				
			} else {
				  
				quitButton.setSize(buttonWidth, buttonHeight);
				quitButton
						.setPosition(
								(Constants.SCREEN_WIDTH - buttonWidth) / 2,
								(Constants.SCREEN_HEIGHT - buttons.length
										* (buttonHeight + verticalSpace) - verticalSpace)
										/ 2
										+ i
										* (buttonHeight + verticalSpace));
				  

				 
















 
				
				
			}
		}

		super.layoutRootPane();
	}

}
