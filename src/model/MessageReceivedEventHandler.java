package model;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class MessageReceivedEventHandler {
	private MessageReceivedEvent event;
	
	public MessageReceivedEventHandler(MessageReceivedEvent event) {
		if (event == null) {
			throw new IllegalArgumentException("event cannot be null");
		}
		this.event = event;
		this.parseMessage(this.event.getMessage().toString());
	}
	
	public void parseMessage(String message) {
		if (message.equalsIgnoreCase("!mykey")) {
			this.mykey();
		}
	}

	private void mykey() {
		event.getChannel().sendMessage("Mykey dicky biggy");
	}

}
