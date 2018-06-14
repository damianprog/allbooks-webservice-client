package com.allbooks.webapp.utils.service;

import java.util.Map;

import com.allbooks.webapp.entity.Pending;

public interface FriendsUtilsService {

	public Pending createPending(Map<String,String> params);
	
	public void acceptOrAbort(Map<String,String> params);
	
	public void deleteFriends(int readerId,int friendId);
}