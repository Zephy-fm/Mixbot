package model;

import java.util.Random;
import java.util.Timer;

import sx.blah.discord.handle.obj.IChannel;

public class FoodEmojiSpammerLimited extends FoodEmojiSpammer {
	public static final int MAXIMUM_REPETIIONS = 10;
	
	private int repetitions;
	private int counter;
	private Timer timer;

	public FoodEmojiSpammerLimited(IChannel channel, Timer timer, int repetitions) {
		super(channel);
		if (timer == null) {
			throw new IllegalArgumentException("timer cannot be null");
		}
		if (repetitions > FoodEmojiSpammerLimited.MAXIMUM_REPETIIONS) {
			throw new IllegalArgumentException("repetitions cannot be greater than " + FoodEmojiSpammerLimited.MAXIMUM_REPETIIONS);
		}
		if (repetitions < 0) {
			throw new IllegalArgumentException("repetitions cannot be negative");
		}
		this.repetitions = repetitions;
		this.timer = timer;
	}
	
	@Override
	public void run() {
		int count = super.getEmojis().length;
		Random rand = new Random();
		int randomInt = rand.nextInt(count);
		super.getChannel().sendMessage(super.getEmojis()[randomInt]);
		this.counter++;
		
		if (this.counter >= repetitions) {
			this.timer.cancel();
			this.timer.purge();
		}
	}
	
}
