package model;

import exceptions.BadCommandException;
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
	public static final String zephyID = "170593868440535040";
	
	private MessageReceivedEventHandler messageEventHandler;
	
	public AnnotationListener() {
		this.messageEventHandler = new MessageReceivedEventHandler();
	}
	  
    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) { // This method is called when the ReadyEvent is dispatched
        System.out.println(event.toString());
    }
  
    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
    		System.out.println("Message received from: " + event.getAuthor().getName());
    		try {
    			this.messageEventHandler.setEvent(event);
    			this.messageEventHandler.parse();
    		} catch (IllegalArgumentException iae) {
    			System.out.println("Something went wrong internally...");
    			iae.printStackTrace();
    		} catch (BadCommandException bce) {
    			event.getChannel().sendMessage("\"" + event.getMessage().toString()
    					+ "\" is not a valid command.");
    		}
    }

}
