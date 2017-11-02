package model;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

/**
 * ObjectDecipher is used to get quick
 * 	information about an event. This object
 * 	is mostly for debugging purposes.
 * 
 * @author FrankMinyon
 *
 * @version 11/2/17
 */
public class ObjectDecipher {
	
	public static String messageReceivedAuthor(MessageReceivedEvent event) {
		IUser author = event.getAuthor();
		return "[Name: " + author.getName()
				+ ", Nickname: " + author.getNicknameForGuild(event.getGuild())
				+ ", userID: " + author.getStringID() + "]";
	}

}
