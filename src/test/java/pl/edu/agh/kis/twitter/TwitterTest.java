package pl.edu.agh.kis.twitter;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class TwitterTest {

	@Test
	public void userFollowsAnotherUser() {
	  //setup:
		Twitter t = new Twitter();
	  //when:
		t.follow(10,20);
	  //then:
		//nothing wrong
	}
	
	@Test
	public void userUnfollowsAnotherUserWhichNotExists() {
	  //setup:
		Twitter t = new Twitter();
	  //when:
		t.unfollow(10,20);
	  //then:
		//nothing wrong
	}
	
	@Test
	public void userPostTweet() {
	  //setup:
		Twitter t = new Twitter();
	  //when:
		t.postTweet(10,99);
	  //then:
		//nothing wrong
	}
	
	@Test
	public void userDoesNotFollowAnyoneAndHasNoTweets_NewsFeedIsEmpty() {
	  //setup:
		Twitter t = new Twitter();
	  //when:
		List<Integer> newsFeed = t.getNewsFeed(77);
	  //then:
		//assertEquals(1, newsFeed.size());
		//assertArrayEquals(new Object[]{1}, newsFeed.toArray());
		assertThat(newsFeed, hasSize(0));
	}
	
	@Test
	public void userDoesNotFollowAnyoneAndPosetedSingleTweet_NewsFeedHasSingleTweet() {
	  //setup:
		final int USER_ID = 77;
		Twitter t = new Twitter();
		t.postTweet(USER_ID, 999);
	  //when:
		List<Integer> newsFeed = t.getNewsFeed(USER_ID);
	  //then:
		assertThat(newsFeed, hasSize(1));
		assertThat(newsFeed, contains(999));
	}
	
	@Test
	public void userDoesNotFollowAnyoneAndPosetedFewTweets_NewsFeedTweets() {
	  //setup:
		final int USER_ID = 77;
		Twitter t = new Twitter(2);
		t.postTweet(USER_ID, 999);
		t.postTweet(USER_ID, 700);
		t.postTweet(USER_ID, 600);
	  //when:
		List<Integer> newsFeed = t.getNewsFeed(USER_ID);
	  //then:
		assertThat(newsFeed, hasSize(2));
		assertThat(newsFeed, containsInAnyOrder(600,700));
		assertThat(newsFeed, contains(600,700));
	}

}
