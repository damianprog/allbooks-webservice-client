package com.allbooks.webapp.utils;

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
	
	public List<Reader> getReaderFriends(int readerId) {

		List<Friends> friendsObjects = friendsService.getAllReaderFriends(readerId);

		List<Reader> friendsReaders = listFactory.createArrayList();

		for (Friends f : friendsObjects) {

			if (f.getReader1().getId() == readerId)
				friendsReaders.add(f.getReader2());
			else if (f.getReader2().getId() == readerId)
				friendsReaders.add(f.getReader1());

		}

		return friendsReaders;

	}

}
