package pl.edu.agh.kis.twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.joda.time.LocalDateTime;

public class Twitter {

	/** Initialize your data structure here. */
	public Twitter() {

	}

	/** Compose a new tweet. */
	public void postTweet(int userId, int tweetId) {
		Tweet newTweet = Tweet.createTweetNow(tweetId);
		createUserIfNecessary(userId);
		tweetsByUser.get(userId).add(newTweet);
	}

	private void createUserIfNecessary(int userId) {
		if (!tweetsByUser.containsKey(userId)) {
			tweetsByUser.put(userId, createUserTweetsContainer());
		}
		if (!usersFollowedByUser.containsKey(userId)) {
			usersFollowedByUser.put(userId, createFolloweeContainer());
		}
	}

	/**
	 * Retrieve the 10 most recent tweet ids in the user's news feed. Each item
	 * in the news feed must be posted by users who the user followed or by the
	 * user herself. Tweets must be ordered from most recent to least recent.
	 */
	public List<Integer> s(int userId) {
		final int LAST_TWEETS_NUMBER = 2;
		createUserIfNecessary(userId);
		PriorityQueue<Tweet> lastMessages = new PriorityQueue<>();
		for (List<Tweet> tweets : allFollowingAndMyTweets(userId)) {
			for (Tweet tweet : tweets) {
				if (lastMessages.size() < LAST_TWEETS_NUMBER) {
					lastMessages.add(tweet);
				} else if (lastMessages.peek().compareTo(tweet) > 0){
					lastMessages.add(tweet);
				} else {
					continue;
				}
			}
		}
		
		return toLastMessageList(lastMessages);
	}

	private List<Integer> toLastMessageList(PriorityQueue<Tweet> lastMessages) {
		List<Integer> tweets = new ArrayList<>();
		for (Tweet t : lastMessages) {
			tweets.add(t.tweetId);
		}
		return tweets;
	}

	/**
	 * Follower follows a followee. If the operation is invalid, it should be a
	 * no-op.
	 */
	public void follow(int followerId, int followeeId) {
		createUserIfNecessary(followerId);
		createUserIfNecessary(followeeId);
		usersFollowedByUser.get(followerId).add(followeeId);
	}

	/**
	 * Follower unfollows a followee. If the operation is invalid, it should be
	 * a no-op.
	 */
	public void unfollow(int followerId, int followeeId) {
		createUserIfNecessary(followerId);
		createUserIfNecessary(followeeId);
		usersFollowedByUser.get(followerId).remove(followeeId);
	}
	

	private List<Tweet> createUserTweetsContainer() {
		return new ArrayList<>();
	}

	private List<Integer> createFolloweeContainer() {
		return new ArrayList<>();
	}

	private Iterable<List<Tweet>> allFollowingAndMyTweets(int userId) {
		List<List<Tweet>> tweets = new ArrayList<>();
		tweets.add(tweetsByUser.get(userId));
		for(Integer followee : usersFollowedByUser.get(userId)) {
			tweets.add(tweetsByUser.get(followee));
		}
		return tweets;
	}
	
	private Map<Integer,List<Tweet>> tweetsByUser = new HashMap<>();
	private Map<Integer,List<Integer>> usersFollowedByUser = new HashMap<>();
	
	private static class Tweet implements Comparable<Tweet>{
		public final int tweetId;
		public final LocalDateTime createdAt;
		
		private Tweet(int tweetId,LocalDateTime createdAt) {
			this.tweetId = tweetId;
			this.createdAt = createdAt;
		}
		
		public static Tweet createTweetNow(int tweetId) {
			LocalDateTime currentTime = LocalDateTime.now();
			return new Tweet(tweetId,currentTime);
		}

		@Override
		public int compareTo(Tweet o) {
			return -this.createdAt.compareTo(o.createdAt);
		}
	}
}

