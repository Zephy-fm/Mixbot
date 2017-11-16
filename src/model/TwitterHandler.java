package model;

import java.util.HashMap;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHandler {
	private Twitter twitter;
	
	private String consumerKey = "0jw28wAI80aJXiIps0Gc0hkOm";
	private String consumerSecret = "9iIKgnQ54ERPwmtQMH3tPNIn7b7V0UHqMHXeYmzjyvVbNqwktx";
	private String accessToken = "875025750-FS37cPVehYwXcVlvsBIgnkI3vligLMpjrKwKxwco";
	private String accessTokenSecret = "3v0PrnJA7H5Zu004hZoqUeYu3xstprjqq0ZphcmHtC3oJ";
	
	private HashMap<String, String> twitterUsers;
	
	public TwitterHandler() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(this.consumerKey);
		cb.setOAuthConsumerSecret(this.consumerSecret);
		cb.setOAuthAccessToken(this.accessToken);
		cb.setOAuthAccessTokenSecret(this.accessTokenSecret);
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		
		this.twitter = tf.getInstance();
		
		this.twitterUsers = new HashMap<String, String>();
		this.initiateTwitterUsers();
	}
	
	public String latestTweet(String user) {
		String userValue = this.twitterUsers.get(user);
		if (userValue == null) {
			return this.latestTweetWorker(user);
		} else {
			return this.latestTweet(userValue);
		}
	}
	
	private String latestTweetWorker(String user) {
		List<Status> statusList = null;
		try {
			statusList = this.twitter.getUserTimeline(user);
		} catch (TwitterException te) {
			te.printStackTrace();
			return "Error finding the latest @" + user + " tweet. User most likely doesn't exist.";
		}
		Status latestTweet = statusList.get(0);
		String accountName = latestTweet.getUser().getScreenName();
		String tweetID = Long.toString(latestTweet.getId());
		System.out.println(latestTweet);
		return "https://twitter.com/" + accountName + "/status/" + tweetID;
	}
	
	private void initiateTwitterUsers() {
		this.twitterUsers.put("poe", "@pathofexile");
		this.twitterUsers.put("blizz", "@BlizzardCS");
	}

}
