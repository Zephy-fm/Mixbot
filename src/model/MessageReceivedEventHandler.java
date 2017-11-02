package model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import exceptions.BadCommandException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class MessageReceivedEventHandler {
	private MessageReceivedEvent event;
	private Timer foodEmojiTimer;
	
	public MessageReceivedEventHandler() {
		this.foodEmojiTimer = new Timer();
	}
	
	public MessageReceivedEventHandler(MessageReceivedEvent event) {
		if (event == null) {
			throw new IllegalArgumentException("event cannot be null");
		}
		this.foodEmojiTimer = new Timer();
		this.event = event;
	}
	
	public void setEvent(MessageReceivedEvent event) {
		if (event == null) {
			throw new IllegalArgumentException("event cannot be null");
		}
		this.event = event;
	}
	
	public void parse() {
		this.parseMessage(this.event.getMessage().toString());
		this.messageLengthEdits();
	}
	
	private void messageLengthEdits() {
		String message = this.event.getMessage().toString();
		if (message.length() > 1000) {
			this.event.getChannel().sendMessage(this.event.getAuthor().getNicknameForGuild(this.event.getGuild()) + " fuck off with your wall of text");
		}
	}
	
	private void parseMessage(String message) {
		if (!message.substring(0, 1).equals("!")) {
			return;
		}
		if (message.equalsIgnoreCase("!commands")) {
			commands();
		} else if (message.equalsIgnoreCase("!mykey")) {
			this.mykey();
		} else if (message.contains("!foodOn")) {
			this.foodOn(message);
		} else if (message.equalsIgnoreCase("!foodOff")) {
			foodOff();
		} else {
			throw new BadCommandException();
		}
	}

	private void commands() {
		this.event.getChannel().sendMessage("!mykey, !foodOn [minutes], !foodOff");
	}

	private void mykey() {
		event.getChannel().sendMessage("Mykey dicky biggy");
	}

	private void foodOn(String message) {
		if (message.equalsIgnoreCase("!foodOn")) {
			this.event.getChannel().sendMessage("Please indicate the amount of "
					+ "time in minutes you would like in between emojis... !foodOn [minutes]");
			return;
		}
		int parsedRate = -1;
		try {
			String rate = message.substring(8);
			parsedRate = Integer.parseInt(rate);
			if (parsedRate < 60) {
				parsedRate = 60;
			}
			FoodEmojiSpammer fes = new FoodEmojiSpammer(this.event.getChannel());
			foodEmojiTimer.schedule(fes, 0, parsedRate * 60000);
		} catch (Exception e) {
			this.event.getChannel().sendMessage("Something went wrong when"
					+ "trying to run the !foodOn command with the time: "
					+ parsedRate + " (-1 indicates an internal error)");
			e.printStackTrace();
		}
	}

	private void foodOff() {
		this.foodEmojiTimer.cancel();
		this.foodEmojiTimer.purge();
	}

}
