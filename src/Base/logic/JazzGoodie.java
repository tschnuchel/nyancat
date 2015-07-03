package Base.logic;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import Base.game.ResourceManager;
import Base.logic.collision.Collidable;
import Base.movement.Movement;
import Base.music.MusicManager;

public class JazzGoodie extends Obstacle {

	private static final String SOUND_EFFECT_SAX = "res/sound/effect_sax_jazz.ogg";
	
	private boolean isValid;
	private Image image;
	private float rpm;
	
	public JazzGoodie(Point position, Movement movement) {
		
		super(new Rectangle(position.getX(), position.getY(), 40, 40), new Rectangle(position.getX(), position.getY(), 40, 40), movement);
		
		MusicManager.getDefaultMusicManager().loadSoundEffect("OGG", SOUND_EFFECT_SAX);
		
		Image sax = (Image) ResourceManager.getDefaultManager().getResourceNamed(ResourceManager.SAXOPHON);
		image = sax.getScaledCopy(40 / 128f);

		rpm = 60f;
		isValid = true;
	}

	@Override
	public void draw(Graphics g) {
		
		g.drawImage(image, boundingBox.getX(), boundingBox.getY());
	}

	@Override
	public boolean isValid() {
		
		return isValid;
	}

	@Override
	public boolean shouldCollideWith(Collidable collidable) {

		return collidable.getClass() == Cat.class;
	}
	
	@Override
	public void collidedWith(Collidable collidable) {
		
		collidable.acceptCollidable(this);
	}

	@Override
	public void acceptCollidable(Cat collidable) {
		
		isValid = false;
		
		MusicManager.getDefaultMusicManager().playSoundEffect(SOUND_EFFECT_SAX);
	}

	@Override
	public void updatePosition(int delta) {
		
		float rotationDelta = (float) (2 * Math.PI * rpm * delta / 60000f);
		Transform rotation = Transform.createRotateTransform(rotationDelta);
		boundingBox.transform(rotation);
		boundingShape.transform(rotation);
		image.rotate((float) (360 * rotationDelta / (2 * Math.PI)));
		
		super.updatePosition(delta);
	}


}
