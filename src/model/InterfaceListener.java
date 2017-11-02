package model;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;

/**
 * InterfaceListner is used for something
 * 	that I am not too sure of yet
 * 
 * @author FrankMinyon
 *
 * @version 11/2/17
 */
public class InterfaceListener implements IListener<ReadyEvent> { // The event type in IListener<> can be any class which extends Event
	  
    @Override
    public void handle(ReadyEvent event) { // This is called when the ReadyEvent is dispatched
        // Fun stuff
    }
    
}
