package com.allbooks.webapp.utils.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;

@Service
public class FriendsServiceImpl implements FriendsService {

	@Autowired
	ReaderService readerService;
	
	@Autowired
	ProfileService profileService;

	@Override
	public Pending createPending(Map<String, String> params) {

		Pending pending = new Pending();
		pending.setRecipentId(Integer.valueOf(params.get("recipentId")));
		pending.setSenderId(Integer.valueOf(params.get("senderId")));
		pending.setRecipentLogin(params.get("recipentLogin"));
		pending.setSenderLogin(params.get("senderLogin"));

		return pending;
	}

	@Override
	public void acceptOrAbort(Map<String, String> params) {
		
		int senderIdInt = Integer.valueOf(params.get("senderId"));
		int recipentIdInt = Integer.valueOf(params.get("recipentId"));
		int pendingIdInt = Integer.valueOf(params.get("pendingId"));

		if (params.get("acceptOrAbort").equals("accept")) {
			Friends friends = new Friends(senderIdInt, recipentIdInt);
			profileService.saveFriends(friends);
			profileService.deletePending(pendingIdInt);

		} else {
			profileService.deletePending(pendingIdInt);
		}
		
	}

	@Override
	public void deleteFriends(int readerId,int friendId) {
		profileService.deleteFriends(readerId, friendId);
		
	}

}
