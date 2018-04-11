package com.allbooks.webapp.utils.service;

import java.util.Map;

import com.allbooks.webapp.entity.Pending;

public interface FriendsService {

	public Pending createPending(Map<String,String> params);
	
	public void acceptOrAbort(Map<String,String> params);
	
	public void deleteFriends(Map<String,Integer> params);
}
