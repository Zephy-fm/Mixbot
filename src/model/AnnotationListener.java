package model;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * AnnotationListener waits for events to
 * 	happen and then performs actions
 * 
 * @author FrankMinyon
 *
 * @version 11/2/17
 */
public class AnnotationListener {
	  
    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) { // This method is called when the ReadyEvent is dispatched
        System.out.println(event.toString());
    }
  
    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
    		System.out.println("Message received from: " + event.getAuthor().getName());
    		try {
    			new MessageReceivedEventHandler(event);
    		} catch (IllegalArgumentException iae) {
    			System.out.println("Null MessageReceivedEvent given to the MessageReceivedEventHandler");
    		}
    }

}
