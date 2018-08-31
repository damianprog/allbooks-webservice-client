package com.allbooks.webapp.utils.reader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.factories.ListFactory;
import com.allbooks.webapp.service.FriendsService;

@Component
public class ReaderFriendsInitializer {

	@Autowired
	private FriendsService friendsService;

	@Autowired
	private ListFactory listFactory;

	private int readerId;

	private List<Friends> friendsObjects;

	private List<Reader> friendsReaders;

	public List<Reader> getReaderFriends(int readerId) {

		initializeThisFields(readerId);

		for (Friends f : friendsObjects)
			addReaderFriendToFriendsReaderList(f);

		return friendsReaders;

	}

	private void initializeThisFields(int readerId) {
		this.readerId = readerId;
		this.friendsObjects = friendsService.getAllReaderFriends(readerId);
		this.friendsReaders = listFactory.createArrayList();
	}
	
	private void addReaderFriendToFriendsReaderList(Friends friends) {
		if (isReader2ThisReaderFriend(friends))
			friendsReaders.add(friends.getReader2());
		else if (isReader1ThisReaderFriend(friends))
			friendsReaders.add(friends.getReader1());
	}

	private boolean isReader2ThisReaderFriend(Friends friends) {
		return friends.getReader1().getId() == readerId;
	}

	private boolean isReader1ThisReaderFriend(Friends friends) {
		return friends.getReader2().getId() == readerId;
	}

}
