package Base.music;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class MusicManager {

	private static MusicManager defaultMusicManager = null;
	
	private Audio intro, loopOriginal, loopJazz;
	private boolean isPlayingOriginal = true;
	private HashMap<String, Audio> cache = new HashMap<String, Audio>();
	
	public static MusicManager getDefaultMusicManager() {
		
		if (defaultMusicManager == null) {

			defaultMusicManager = new MusicManager();
		}
		
		return defaultMusicManager;
	}
	
	private MusicManager() {
		
		/*if[Hintergrundmusik]*/
		intro = createAudio("OGG", "/res/sound/nyan_intro_original.ogg");
		loopOriginal = createAudio("OGG", "/res/sound/nyan_loop_original.ogg");
		loopJazz = createAudio("OGG", "/res/sound/nyan_loop_jazz.ogg");
		loadSoundEffect("OGG", "/res/sound/nyan_loop_jazz.ogg");
		loadSoundEffect("OGG", "/res/sound/nyan_loop_original.ogg");
		loopOriginal.playAsMusic(1.0f, 1.0f, true);
		/*end[Hintergrundmusik]*/
		/*if[Soundeffekte]*/
		loadSoundEffect("OGG", "res/sound/effect_sax_jazz.ogg");
		/*end[Soundeffekte]*/
	}
	/*if[Hintergrundmusik]*/
	public void playOriginal(){//TODO evtl. einbauen, ich finde nur nicht, wo...
		if(!isPlayingOriginal){
		float position = loopJazz.getPosition() * 27f / 48f;
		loopOriginal.playAsMusic(1.0f, 1.0f, true);
		loopOriginal.setPosition(position);
		isPlayingOriginal = true;
		}
	}
	/*if[JazzMode]*/
	public void playJazz(){
		float position = loopOriginal.getPosition() * 48f / 27f;
		loopJazz.playAsMusic(1.0f, 1.0f, true);
		loopJazz.setPosition(position);
		isPlayingOriginal = false;
	}
	/*end[JazzMode]*/
	/*end[Hintergrundmusik]*/
	
	private Audio createAudio(String format, String path) {
		
		Audio audio = null;
		
		try {
			
			audio = AudioLoader.getAudio(format, ResourceLoader.getResourceAsStream(path));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return audio;
	}
	
	/*if[Soundeffekte]*/
	public boolean loadSoundEffect(String format, String ref) {
		
		if (this.cache.containsKey(ref)) {
			return true;
		}
		
		Audio effect = createAudio(format, ref);
		
		if (effect != null) {
			
			this.cache.put(ref, effect);
		}
		
		return effect != null;
	}
	/*else[Hintergrundmusik]*/
public boolean loadSoundEffect(String format, String ref) {
		
		if (this.cache.containsKey(ref)) {
			return true;
		}
		
		Audio effect = createAudio(format, ref);
		
		if (effect != null) {
			
			this.cache.put(ref, effect);
		}
		
		return effect != null;
	}
/*end[Hintergrundmusik]*/
/*if[Soundeffekte]*/
	public void playSoundEffect(String ref) {
		
		Audio effect = this.cache.get(ref);
		effect.playAsSoundEffect(1.0f, 1.0f, false);
	}
	/*end[Soundeffekte]*/
}
