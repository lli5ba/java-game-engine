package edu.virginia.lab1test;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;

public class QuestManager implements IEventListener{

	public QuestManager(){
		
	}
	
	@Override
	public void handleEvent(Event event) {
		if (event.getEventType() == "Key Picked Up") {
			System.out.println("Quest is complete!");
		}
		
	}
	

}
