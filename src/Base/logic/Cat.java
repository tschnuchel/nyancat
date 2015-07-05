package Base.logic;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

import Base.game.Constants;
import Base.game.Drawable;
import Base.game.KeyboardHandler;
import Base.game.ResourceManager;
import Base.logic.CatListener.CatMode;
import Base.logic.collision.Collidable;
import Base.movement.LinearMovement;

public class Cat extends Movable implements Drawable, KeyboardHandler {

	private Float[] rainbow;
	private Color[] rainbowColors;
	private boolean rainbowAlternate = false;
	private float rainbowCounter = 400;
	private float maxSpeedX = Constants.CAT_SPEED_X, maxSpeedY = Constants.CAT_SPEED_Y;
	private Animation originalAnimation;
	private boolean[] arrows;
	private Rectangle boundingBox;
	private Shape boundingShape;
	private float speedX = 0, speedY = 0;
	private int lifes = 7;
	private int jazzCount = 0;
	private CatListener listener;
	private CatMode mode;
	
	public Cat() {
		
		// init mode
		mode = CatMode.ORIGINAL;
		
		// init boundingBox
		boundingBox = new Rectangle(0, Constants.SCREEN_HEIGHT / 2f, 32 * Constants.CAT_SCALE_FACTOR, 20 * Constants.CAT_SCALE_FACTOR);
		Polygon poly = new Polygon();
		int scale = Constants.CAT_SCALE_FACTOR;
		poly.addPoint(8 * scale, 0 * scale);
		poly.addPoint(6 * scale, 4 * scale);
		poly.addPoint(6 * scale, 20 * scale);
		poly.addPoint(27 * scale, 20 * scale);
		poly.addPoint(32 * scale, 15 * scale);
		poly.addPoint(32 * scale, 11 * scale);
		poly.addPoint(31 * scale, 7 * scale);
		poly.addPoint(25 * scale, 2 * scale);
		poly.addPoint(23 * scale, 0 * scale);
		boundingShape = poly;
		boundingShape.setY(Constants.SCREEN_HEIGHT / 2f);
		
		// init rainbow
		rainbow = new Float[lifes];
		for (int i = 0; i < rainbow.length; i++) {
			
			rainbow[i] = boundingBox.getY();
		}

		// init rainbow colors
		rainbowColors = new Color[6];
		rainbowColors[0] = new Color(237, 38, 34);
		rainbowColors[1] = new Color(242, 179, 16);
		rainbowColors[2] = new Color(250, 255, 0);
		rainbowColors[3] = new Color(113, 255, 1);
		rainbowColors[4] = new Color(92, 180, 255);
		rainbowColors[5] = new Color(137, 71, 255);
		
		// init input array
		arrows = new boolean[4];
		
		// start cat animation
		originalAnimation = (Animation) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.CAT_ANIMATION_ORIGINAL);
		originalAnimation.start();
	}
	
	@Override
	public boolean shouldCollideWith(Collidable collidable) {

		return true;
	}
	
	@Override
	public void collidedWith(Collidable collidable) {
		
		collidable.acceptCollidable(this);
	}
	
	@Override
	public void acceptCollidable(Planet planet) {
		
		this.removeLife();
	}
	
	@Override
	public void acceptCollidable(LifeUpGoodie collidable) {
		
		this.setLifes(this.getLifes() + 1);
	}

	@Override
	public void acceptCollidable(JazzGoodie collidable) {

		this.setJazzCount(this.getJazzCount() + 1);
	}

	public void removeLife() {
		
		lifes--;
		if (lifes >= 0) {
			
			Float[] newRainbow = new Float[lifes];
			for (int i = 0; i < newRainbow.length; i++) {
				
				newRainbow[i] = rainbow[i];
			}
			
			rainbow = newRainbow;
		}
	}
	
	public int getLifes() {
		
		return lifes;
	}

	public void setLifes(int lifes) {
		if ((lifes >= 0) && (lifes <= 7)) {
			
			Float[] newRainbow = new Float[lifes];
			this.lifes = lifes;
			for (int i = 0; i < lifes; i++) {
				
				if (i < rainbow.length) {
					
					newRainbow[i] = rainbow[i];
					
				} else {
					
					newRainbow[i] = rainbow[rainbow.length - 1];
				}
			}
			
			rainbow = newRainbow;
		}
	}
	
	public boolean isDead() {
		
		return lifes <= 0;
	}
	
	public int getJazzCount() {
		
		return jazzCount;
	}

	public void setJazzCount(int jazzCount) {
		
		this.jazzCount = jazzCount;
		if ((this.jazzCount >= 5) && (this.mode != CatMode.JAZZ)) {
			
			this.mode = CatMode.JAZZ;
			listener.didEnterMode(this, CatMode.JAZZ);
			
			this.jazzCount = 0;
		}
	}
	
	public void setListener(CatListener lsitener) {
		
		this.listener = lsitener;
	}
	
	public CatMode getMode() {
		
		return mode;
	}

	@Override
	public Rectangle getBoundingBox() {
		
		return boundingBox;
	}

	@Override
	public Shape getBoundingShape() {

		return boundingShape;
	}

	@Override
	public void updatePosition(int delta) {
		
		// update animation
		// TODO move this to central animation update method
		originalAnimation.update(delta);
		
		float accelerationX = maxSpeedX * 5;
		float accelerationY = maxSpeedY * 5;
		
		// update cat
		if (arrows[0]) {
			
			speedY -= accelerationY * delta / 1000f;
			speedY = Math.max(speedY, -maxSpeedY);
			
		} else if(arrows[2]) {
			
			speedY += accelerationY * delta / 1000f;
			speedY = Math.min(speedY, maxSpeedY);

		} else {
			
			if (speedY > 0) {
				
				speedY -= accelerationY * delta / 1000f;
				speedY = Math.max(speedY, 0);
				
			} else {
				
				speedY += accelerationY * delta / 1000f;
				speedY = Math.min(speedY, 0);
			}
		}

		float difY = speedY * delta / 1000f;

		// check for screen bounds
		if (boundingBox.getY() + difY <= 0) {
			
			difY = -Math.min(-difY, boundingBox.getY());
			speedY = 0;
			
		} else if (boundingBox.getY() + boundingBox.getHeight() + difY >= Constants.SCREEN_HEIGHT) {
			
			difY = Math.min(difY, Constants.SCREEN_HEIGHT - boundingBox.getHeight() - boundingBox.getY());
			speedY = 0;
		}
		
		float newY = boundingBox.getY() + difY;
		boundingBox.setY(newY);
		boundingShape.setY(boundingShape.getY() + difY);
		
		if (arrows[1]) {
			
			speedX += accelerationX * delta / 1000f;
			speedX = Math.min(speedX, maxSpeedX);
			
		} else if(arrows[3]) {
			
			speedX -= accelerationX * delta / 1000f;
			speedX = Math.max(speedX, -maxSpeedX);

		} else {
			
			if (speedX > 0) {
				
				speedX -= accelerationX * delta / 1000f;
				speedX = Math.max(speedX, 0);
				
			} else {
				
				speedX += accelerationX * delta / 1000f;
				speedX = Math.min(speedX, 0);
			}
		}

		float difX = speedX * delta / 1000f;

		// check for screen bounds
		if (boundingBox.getX() + difX <= 0) {
			
			difX = -Math.min(-difX, boundingBox.getX());
			speedX = 0;
			
		} else if (boundingBox.getX() + boundingBox.getWidth() + difX >= Constants.SCREEN_WIDTH) {
			
			difX = Math.min(difX, Constants.SCREEN_WIDTH - boundingBox.getWidth() - boundingBox.getX());
			speedX = 0;
		}
		
		float newX = boundingBox.getX() + difX;
		boundingBox.setX(newX);
		boundingShape.setX(boundingShape.getX() + difX);
		
		int maxDif = 20;
		// update rainbow
		for (int i = 0; i < rainbow.length; i++) {
			
			// dont get away too far
			Float f = rainbow[i];
			if (f < boundingBox.getY() - (i + 1) * maxDif) {
				
				rainbow[i] = boundingBox.getY() - (i + 1) * maxDif;
				
			} else if (f > boundingBox.getY() + (i + 1) * maxDif) {
				
				rainbow[i] = boundingBox.getY() + (i + 1) * maxDif;
			}
			
			float dif = rainbow[i] - boundingBox.getY();
			float factor = 2f + 5f * (rainbow.length - i) / rainbow.length;
			rainbow[i] -= dif * factor * delta / 1000f;
		}
		
		rainbowCounter -= delta;
		if (rainbowCounter <= 0) {
			
			rainbowAlternate = !rainbowAlternate;
			rainbowCounter = 400;
		}
	}

	public float getMaxSpeedX() {
		
		return maxSpeedX;
	}

	public float getMaxSpeedY() {
		
		return maxSpeedY;
	}

	@Override
	public void handle(GameContainer container, StateBasedGame game, int key, boolean released) {
	
		switch (key) {
		case Input.KEY_UP:
			
			arrows[0] = !released;
			break;
			
		case Input.KEY_RIGHT:
			
			arrows[1] = !released;
			break;
			
		case Input.KEY_DOWN:
			
			arrows[2] = !released;
			break;
			
		case Input.KEY_LEFT:
			
			arrows[3] = !released;
			break;

		case Input.KEY_SPACE:
			
			if (released) {
				
				Note note = new Note(new Point(getBoundingBox().getMaxX(), getBoundingBox().getCenterY()), new LinearMovement(new Point(getMaxSpeedX(), speedY)));
				listener.didShoot(note);
			}
			break;
			
		default:
			break;
		}
	}

	@Override
	public void draw(Graphics g) {
		
		// draw rainbow
		float segmentWidth = 40;
		float segmentHeight = boundingBox.getHeight() / 6f;
		float alternateOffset = segmentHeight / 2f;

		for (int i = 0; i < rainbow.length; i++) {
			
			// draw segment
			float x = boundingBox.getX() - (i + 1) * segmentWidth + 8;
			
			for (int j = 0; j < 6; j++) {
				
				float y = boundingBox.getY() + (rainbow[i] - boundingBox.getY()) + j * segmentHeight;
				
				if ((rainbowAlternate && (i%2 == 0)) || (!rainbowAlternate && (i%2 == 1))) {
					y += alternateOffset;
				}

				Color color = null;
				if (mode == CatMode.ORIGINAL) {
					
					color = rainbowColors[j];
					
				} else if (mode == CatMode.JAZZ) {
					
					Color rC= rainbowColors[j];
					float grey = rC.getRed() * 0.3f + rC.getGreen() * 0.584f + rC.getBlue() * 0.114f;
					color = new Color(grey, grey, grey);
				}
				color.a = 1f - i * (1f / rainbow.length);
				g.setColor(color);
				g.fillRect(x, y, segmentWidth, segmentHeight);
			}
		}
		
		// draw cat
		Image image = originalAnimation.getCurrentFrame();
		g.drawImage(image, boundingBox.getX(), boundingBox.getY());
		
		// debug
//		g.setColor(Color.red);
//		g.draw(boundingBox);
//		g.setColor(Color.green);
//		g.draw(boundingShape);
	}

	@Override
	public boolean isValid() {
		
		return true;
	}
}
