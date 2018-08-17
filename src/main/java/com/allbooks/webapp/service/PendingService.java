package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.Pending;

public interface PendingService {

	List<Pending> getFriendsInvitesByReaderId(int readerId);

	void savePending(Pending pending);

	Pending getPending(int reader1Id, int reader2Id);

	boolean checkPending(int reader1Id, int reader2Id);

	void deletePending(int pendingIdInt);

	boolean isItSenderProfile(int recipentId, int senderId);

	List<Pending> getReaderAsSenderPendings(int readerId);

	Pending getPendingByRecipentIdAndSenderId(int recipentId,int senderId);
	
}
