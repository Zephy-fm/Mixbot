package view;

import model.AnnotationListener;
import model.DiscordClient;
import model.InterfaceListener;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

/**
 * View contains the first methods called
 * 	inside the Mixbot program, and contains 
 * 	general instructions on how to run/startup.
 * 
 * @author FrankMinyon
 *
 * @version 11/2/17
 */
public class View {
	// Variables for bot
	private String clientID = "375677783130767361";
	private String clientSecret = "_F7Wh5kn4qRldaudRZophchgcBrFGR9o";
	private String token = "Mzc1Njc3NzgzMTMwNzY3MzYx.DNznjQ.ISnsiLzBbaYDEhxTjwemY97mqq8";

	/**
	 * Starts up the bot
	 * 
	 * @precondition 	none
	 * 
	 * @postcondition 	bot is listening
	 */
	public void run() {
		IDiscordClient client = DiscordClient.createClient(this.token, true);
		EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new InterfaceListener()); // Registers the IListener example class from above
        dispatcher.registerListener(new AnnotationListener()); // Registers the @EventSubscriber example class from above
	}
	
}
