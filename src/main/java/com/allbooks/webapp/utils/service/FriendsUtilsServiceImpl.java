package com.allbooks.webapp.utils.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.PendingService;
import com.allbooks.webapp.service.ReaderService;

@Service
public class FriendsUtilsServiceImpl implements FriendsUtilsService {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private PendingService pendingService;
	
	@Autowired
	private FriendsService friendsService;
	
	@Autowired
	private SecurityContextService contextService;
	
	@Override
	public Pending createPending(Map<String, String> params) {

		Pending pending = new Pending();
		
		Reader recipent = readerService.getReaderById(Integer.parseInt(params.get("recipentId"))); 

		Reader sender = readerService.getReaderById(contextService.getLoggedReaderId());
		
		pending.setRecipent(recipent);
		pending.setSender(sender);
		
		return pending;
	}

	@Override
	public void acceptOrAbort(Map<String, String> params) {
		
		int senderIdInt = Integer.valueOf(params.get("senderId"));
		int pendingIdInt = Integer.valueOf(params.get("pendingId"));
		int recipentIdInt = contextService.getLoggedReaderId();
		
		Reader sender = readerService.getReaderById(senderIdInt);

		Reader recipent = readerService.getReaderById(recipentIdInt);
		
		if (params.get("acceptOrAbort").equals("accept")) {
			Friends friends = new Friends(sender, recipent);
			friendsService.saveFriends(friends);
			pendingService.deletePending(pendingIdInt);

		} else {
			pendingService.deletePending(pendingIdInt);
		}
		
	}

	@Override
	public void deleteFriends(int readerId,int friendId) {
		friendsService.deleteFriends(readerId, friendId);
		
	}

}
