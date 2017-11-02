package model;

import java.util.Random;
import java.util.TimerTask;

import sx.blah.discord.handle.obj.IChannel;

public class FoodEmojiSpammer extends TimerTask {
	private IChannel channel;
	
	private String[] emojis = 
		{":apple:", ":green_apple:", ":pear:", ":tangerine:", ":lemon:", ":banana:", ":watermelon:", 
			":grapes:", ":strawberry:", ":cherries:", ":peach:", ":pineapple:", ":kiwi:", ":tomato:",
			":eggplant:", ":avocado:", ":hot_pepper:", ":corn:", ":carrot:", ":potato:",
			":sweet_potato:", ":bread:", ":french_bread:", ":cheese:", ":egg:", ":bacon:",
			":poultry_leg:", ":meat_on_bone:", ":hotdog:", ":hamburger:", ":fries:", ":pizza:",
			":salad:", ":taco:", ":burrito:", ":spaghetti:", ":fried_shrimp:", ":curry:",
			":sushi:", ":rice_ball:", ":rice:", ":rice_cracker:", ":cooking:", ":icecream:",
			":fish_cake:", ":cake:", ":pancakes:", ":custard:", ":lollipop:", ":candy:",
			":chocolate_bar:", ":popcorn:", ":doughnut:", ":cookie:", ":chestnut:", ":peanuts:",
			":honey_pot:", ":milk:", ":tea:", ":coffee:", ":tropical_drink:", ":beer:", ":beers:", 
			":champagne_glass:", ":tumbler_glass:"};
	
	public FoodEmojiSpammer(IChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException("channel cannot be null");
		}
		this.channel = channel;
	}
	
	public void run() {
		int count = this.emojis.length;
		Random rand = new Random();
		int randomInt = rand.nextInt(count);
		this.channel.sendMessage(this.emojis[randomInt]);
	}
	
}
