package com.allbooks.webapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.webservice.PendingWebservice;

@Service
public class PendingServiceImpl implements PendingService {

	@Autowired
	private PendingWebservice pendingWebservice;
	
	@Override
	public void savePending(Pending pending) {
		pendingWebservice.savePending(pending);
	}

	@Override
	public Pending getPending(int reader1, int reader2) {

		return pendingWebservice.getReadersPending(reader1, reader2);

	}

	@Override
	public void deletePending(int pendingIdInt) {
		pendingWebservice.deletePending(pendingIdInt);

	}

	@Override
	public boolean checkPending(int reader1, int reader2) {

		Pending pending = getPending(reader1, reader2);

		if (pending == null)
			return false;
		else
			return true;
	}
	
	@Override
	public List<Pending> getFriendsInvitesByReaderId(int readerId) {

		return Arrays.asList(pendingWebservice.getReaderPendings(readerId));
	}

	@Override
	public boolean isItSenderProfile(int recipentId, int senderId) {
		
		Pending pending = getPendingByRecipentIdAndSenderId(recipentId,senderId);
		
		if(pending == null)
			return false;
		else
			return true;
	}

	
	
	@Override
	public List<Pending> getReaderAsSenderPendings(int readerId) {
		return new ArrayList<Pending>(Arrays.asList(pendingWebservice.getReaderAsSenderPendings(readerId)));
	}

	@Override
	public Pending getPendingByRecipentIdAndSenderId(int recipentId, int senderId) {
		
		return pendingWebservice.getPendingByRecipentIdAndSenderId(recipentId,senderId);
		
	}
	
}
