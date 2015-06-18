package Base.game;

import java.util.LinkedHashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ResourceManager {

	public static String CAT_ANIMATION_ORIGINAL = "cat animation original";
	public static String CAT_ANIMATION_JAZZ = "cat animation jazz";
	public static String PLANET_IMAGE = "planet image";
	public static String PLANET_IMAGE2 = "planet image2";
	public static String PLANET_CRASH1 = "planet crash1";
	public static String PLANET_CRASH2 = "planet crash2";
	public static String BACKGROUND = "background";
	public static String CAT_CREDITS = "cat credits";
	public static String SAXOPHON = "saxophon";
	
	private LinkedHashMap<String, Object> resources;
	
	private static ResourceManager defaultManager = null;
	
	public static ResourceManager getDefaultManager() {
		
		if (defaultManager == null) {
			
			defaultManager = new ResourceManager();
		}
		
		return defaultManager;
	}
	
	private ResourceManager() {
		
		resources = new LinkedHashMap<String, Object>();
	}
	
	public void loadAll() throws SlickException {
		
		int factor = Constants.CAT_SCALE_FACTOR;
		Image[] frames = new Image[6];
		frames[0] = new Image("res/image/Nyan_original_1POT.png", Image.FILTER_NEAREST).getScaledCopy(factor);
		frames[1] = new Image("res/image/Nyan_original_2POT.png", Image.FILTER_NEAREST).getScaledCopy(factor);
		frames[2] = new Image("res/image/Nyan_original_3POT.png", Image.FILTER_NEAREST).getScaledCopy(factor); 
		frames[3] = new Image("res/image/Nyan_original_4POT.png", Image.FILTER_NEAREST).getScaledCopy(factor);
		frames[4] = new Image("res/image/Nyan_original_5POT.png", Image.FILTER_NEAREST).getScaledCopy(factor);
		frames[5] = new Image("res/image/Nyan_original_6POT.png", Image.FILTER_NEAREST).getScaledCopy(factor);
		Animation animation = new Animation(frames, 66, false);
		resources.put(CAT_ANIMATION_ORIGINAL, animation);
		
		resources.put(PLANET_IMAGE, new Image("res/image/planet.png"));
		resources.put(PLANET_IMAGE2, new Image("res/image/planet2.png"));
		resources.put(PLANET_CRASH1, new Image("res/image/planet_crash_1.png"));
		resources.put(PLANET_CRASH2, new Image("res/image/planet_crash_2.png"));
		resources.put(BACKGROUND, new Image("res/image/background.png"));
		resources.put(CAT_CREDITS, new Image("res/image/Nyan_credits.png"));
		resources.put(SAXOPHON, new Image("res/image/saxophon.png"));
	}
	
	public Object getResourceNamed(String name) {
		
		return resources.get(name);
	}
}
