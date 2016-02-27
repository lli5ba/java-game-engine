package edu.virginia.engine.tween;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventDispatcher;

public class TweenEvent extends Event {
	final static String TWEEN_COMPLETE_EVENT = "tween_complete";
	Tween tween;
	
	public TweenEvent(String eventType, Tween tween) {
		super(eventType, tween);
		this.tween = tween;
	}
	
	public Tween getTween(){
		return this.getTween();
	}

}
