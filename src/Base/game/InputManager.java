package Base.game;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class InputManager {

	private LinkedHashMap<Integer, List<KeyboardHandler>> handlers;
	private StateBasedGame game;
	private GameContainer container;
	
	public InputManager(GameContainer container, StateBasedGame game) {
		
		handlers = new LinkedHashMap<Integer, List<KeyboardHandler>>();
		this.game = game;
		this.container = container;
	}
	
	public void register(KeyboardHandler handler, int key) {
		
		if (!handlers.containsKey(key)) {
			
			List<KeyboardHandler> list = new LinkedList<KeyboardHandler>();
			list.add(handler);
			handlers.put(key, list);
			
		} else {
			
			List<KeyboardHandler> list = handlers.get(key);
			list.add(handler);
		}
	}
	
	public void register(KeyboardHandler handler, int[] keys) {
		
		if (keys != null) {
			
			for (int key : keys) {
				
				register(handler, key);
			}
		}
	}
	
	public void processKey(int key, boolean released) {
	
		if (handlers.containsKey(key)) {
			
			List<KeyboardHandler> list = handlers.get(key);
			for (KeyboardHandler keyboardHandler : new LinkedList<KeyboardHandler>(list)) {
				
				keyboardHandler.handle(container, game, key, released);
			}
		}
	}
}
