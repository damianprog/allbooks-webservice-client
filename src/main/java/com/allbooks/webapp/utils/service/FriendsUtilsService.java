package com.allbooks.webapp.utils.service;

import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.PendingRequestResponseData;

public interface FriendsUtilsService {

	public Pending createPending(int recipentId);
	
	public void acceptOrAbort(PendingRequestResponseData responseData);
	
	public void deleteFriends(int readerId,int friendId);
}
