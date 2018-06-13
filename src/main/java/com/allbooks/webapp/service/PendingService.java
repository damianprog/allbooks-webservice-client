package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.Pending;

public interface PendingService {

	public List<Pending> getFriendsInvites(int id);
	
	public void savePending(Pending pending);

	public Pending getPending(int reader1Id, int reader2Id);

	public boolean checkPending(int reader1Id, int reader2Id);

	public void deletePending(int pendingIdInt);

	public boolean isItSenderProfile(int recipentId, int senderId);
	
}
