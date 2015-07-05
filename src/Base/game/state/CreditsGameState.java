package Base.game.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Base.TWLSlick.BasicTWLGameState;
import Base.game.Constants;
import Base.game.ResourceManager;

public class CreditsGameState extends BasicTWLGameState {

	private int id;
	private int counter;
	
	public CreditsGameState(int uniqueID) {
		
		this.id = uniqueID;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		String s1 = "\"Nyan Game\" was designed in cooperation with:";
		String s2 = "Nick Bicko: Pixelartist";
		String s3 = "Garrit Uebelacker: Did this ...";
		String s4 = "Florian Knack: Story-design and level creation";
		String s5 = "Simon Walser and Wolfgang Pfeffer: Unsuccessful attemps to creating a database";
		
		float offset = counter * 30 / 1000f - 50;
		
		g.drawString(s1, 100, Constants.SCREEN_HEIGHT - offset);
		g.drawString(s2, 100, Constants.SCREEN_HEIGHT - offset + 50);
		g.drawString(s3, 100, Constants.SCREEN_HEIGHT - offset + 100);
		Image image = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.CAT_CREDITS);
		image.setFilter(Image.FILTER_NEAREST);
		g.drawImage(image.getScaledCopy(3), 400, Constants.SCREEN_HEIGHT - offset + 80);
		g.drawString(s4, 100, Constants.SCREEN_HEIGHT - offset + 150);
		g.drawString(s5, 100, Constants.SCREEN_HEIGHT - offset + 200);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		counter += delta;
	}

	@Override
	public int getID() {
		
		return id;
	}

	
}
