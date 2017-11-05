package model;

import java.util.Timer;

import exceptions.BadCommandException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class MessageReceivedEventHandler {
	private MessageReceivedEvent event;
	private Timer foodEmojiTimer;
	private ChatStateManager states;
	
	public MessageReceivedEventHandler() {
		this.foodEmojiTimer = new Timer();
		this.states = new ChatStateManager();
		System.out.println(this.states);
	}
	
	public MessageReceivedEventHandler(MessageReceivedEvent event) {
		this();
		if (event == null) {
			throw new IllegalArgumentException("event cannot be null");
		}
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
			this.event.getChannel().sendMessage("@" + this.event.getAuthor().getNicknameForGuild(this.event.getGuild()) + " SIT YOUR PUNK ASS DOWN");
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
			this.foodOff();
		} else if (message.equalsIgnoreCase("!states")) {
			this.states();
		} else {
			throw new BadCommandException();
		}
	}

	private void commands() {
		this.event.getChannel().sendMessage("!mykey, !foodOn [seconds], !foodOff");
		/**
		 * Dev commands:
		 *  !states
		 */
	}

	private void mykey() {
		event.getChannel().sendMessage("Mykey dicky biggy");
	}

	private void foodOn(String message) {
		if (this.states.isActive("foodSpam")) {
			this.event.getChannel().sendMessage("The food spammer is already active... FLAU");
			return;
		}
		if (message.equalsIgnoreCase("!foodOn")) {
			this.event.getChannel().sendMessage("Please indicate the amount of "
					+ "time in seconds you would like in between emojis... !foodOn [seconds] (minimum " + FoodEmojiSpammer.MINIMUM_SECONDS + ")");
			return;
		}
		int parsedRate = -1;
		try {
			String rate = message.substring(8);
			parsedRate = Integer.parseInt(rate);
			if (parsedRate < FoodEmojiSpammer.MINIMUM_SECONDS) {
				parsedRate = FoodEmojiSpammer.MINIMUM_SECONDS;
			}
			FoodEmojiSpammer fes = new FoodEmojiSpammer(this.event.getChannel());
			this.foodEmojiTimer.schedule(fes, 0, parsedRate * 1000);
			this.states.activate("foodSpam");
		} catch (Exception e) {
			this.event.getChannel().sendMessage("Something went wrong when"
					+ "trying to run the !foodOn command with the time: "
					+ parsedRate + " (-1 indicates an internal error)");
			e.printStackTrace();
		}
	}

	private void foodOff() {
		if (!this.states.isActive("foodSpam")) {
			this.event.getChannel().sendMessage("The food spammer isn't running...");
			return;
		}
		this.foodEmojiTimer.cancel();
		this.foodEmojiTimer.purge();
		this.states.deactivate("foodSpam");
		this.event.getChannel().sendMessage("Food spammer is off... beepboop");
	}

	private void states() {
		this.event.getChannel().sendMessage(this.states.toString());
	}

}
