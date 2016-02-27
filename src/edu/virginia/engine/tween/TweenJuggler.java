package edu.virginia.engine.tween;

import java.util.ArrayList;
import java.util.Iterator;


/*
 * Singleton class that handles all of the tweens in the game and updates them
 * appropriately once per frame.
*/
public class TweenJuggler {
	private static volatile TweenJuggler instance;
	ArrayList<Tween> tweens;
	
	 public static TweenJuggler getInstance(){
               if(instance == null)
                         instance = new TweenJuggler();
               return instance;
     }
	 
	public TweenJuggler() {
		instance = this;
		tweens = new ArrayList<Tween>();
	}
	
	public void add(Tween tween) {
		tweens.add(tween);
	}
	
	//invoked every frame by Game, 
	//calls update() on every Tween 
	//and cleans up old complete tweens
	public void nextFrame() { 
		for (Iterator<Tween> iterator = tweens.iterator(); iterator.hasNext();) {
		    Tween t = iterator.next();
		    if (t.isComplete()) {
		        iterator.remove();
		    } else {
		    	t.update();
		    }
		}
	}
}
