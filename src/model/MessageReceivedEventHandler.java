package model;

import java.util.Timer;

import controller.Driver;
import exceptions.BadCommandException;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class MessageReceivedEventHandler {
	private MessageReceivedEvent event;
	private Timer foodEmojiTimer;
	private Timer limitedFoodEmojiSpammer;
	private ChatStateManager states;
	private TwitterHandler twitter;
	private CurrencyRateHandler currency;

	public MessageReceivedEventHandler() {
		this.foodEmojiTimer = new Timer();
		this.limitedFoodEmojiSpammer = new Timer();
		this.states = new ChatStateManager();
		this.twitter = new TwitterHandler();
		this.currency = new CurrencyRateHandler();
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
			throw new BadCommandException();
			//this.foodOn(message);
		} else if (message.equalsIgnoreCase("!foodOff")) {
			throw new BadCommandException();
			//this.foodOff();
		} else if (message.equalsIgnoreCase("!states")) {
			this.states();
		} else if (message.contains("!food")) {
			throw new BadCommandException();
			//this.food(message);
		} else if (message.contains("!twitter")) {
			this.twitter(message);
		} else if (message.equalsIgnoreCase("!restartBot")) {
			this.restartBot(message);
		} else if (message.contains("!currency")) {
			this.currency(message);
		} else {
			throw new BadCommandException();
		}
	}

	private void commands() {
		this.event.getChannel().sendMessage("!mykey, !foodOn [seconds], !foodOff, !twitter [who], !currency [CUR]");
		/**
		 * Dev commands:
		 *  !states, !restartBot
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
		this.foodEmojiTimer = new Timer();
		this.event.getChannel().sendMessage("Food spammer is off... beepboop");
	}

	private void states() {
		this.event.getChannel().sendMessage(this.states.toString());
	}

	private void food(String message) {
		if (message.equalsIgnoreCase("!food")) {
			this.event.getChannel().sendMessage("Please indicate the amount of "
					+ "emojis you would like to produce... !food [repetitions] (maximum " + FoodEmojiSpammerLimited.MAXIMUM_REPETIIONS + ")");
			return;
		}
		int parsedRepetitions = -1;
		try {
			String rate = message.substring(6);
			parsedRepetitions = Integer.parseInt(rate);
			if (parsedRepetitions < 0) {
				parsedRepetitions = 0;
			}
			if (parsedRepetitions > FoodEmojiSpammerLimited.MAXIMUM_REPETIIONS) {
				parsedRepetitions = FoodEmojiSpammerLimited.MAXIMUM_REPETIIONS;
			}
			try {
			FoodEmojiSpammer fesl = new FoodEmojiSpammerLimited(this.event.getChannel(), this.limitedFoodEmojiSpammer, parsedRepetitions);
			this.limitedFoodEmojiSpammer.schedule(fesl, 0, 3000);
			} catch (IllegalStateException ise) {
				this.limitedFoodEmojiSpammer = new Timer();
				this.food(message);
			}
		} catch (Exception e) {
			this.event.getChannel().sendMessage("Something went wrong when "
					+ "trying to run the !food command with the repetitions: "
					+ parsedRepetitions + " (-1 indicates an internal error)");
			e.printStackTrace();
		}
	}

	private void twitter(String message) {
		String trimmedMessage = null;
		try {
			trimmedMessage = message.substring(9);
		} catch (IndexOutOfBoundsException ioobe) {
			this.event.getChannel().sendMessage("Please include a command at the end of !twiter... \"!twitter poe\"");
			return;
		}
		this.event.getChannel().sendMessage(this.twitter.latestTweet(trimmedMessage));
	}

	private void restartBot(String message) {
		Driver.restartApplication();
	}

	private void currency(String message) {
		String trimmedMessage = null;
		try {
			trimmedMessage = message.substring(10);
		} catch (IndexOutOfBoundsException ioobe) {
			this.event.getChannel().sendMessage("Please include a command at the end of !currency... \"!currency btc\"");
			return;
		}
		if (trimmedMessage.equalsIgnoreCase("update")) {
			this.event.getChannel().sendMessage(this.currency.reloadData());
			return;
		} else if (trimmedMessage.equalsIgnoreCase("commands")) {
			this.event.getChannel().sendMessage(this.currency.getAllCommands());
			return;
		} else if (trimmedMessage.split("\\s").length() = 3) {
			this.event.getChannel().sendMessage(this.currency.getCurrencyConversion(trimmedMessage));
			return;
		}
		String rate = this.currency.getCurrencyRate(trimmedMessage);
		if (rate == null) {
			throw new BadCommandException();
		}
		this.event.getChannel().sendMessage(rate);
	}

}
