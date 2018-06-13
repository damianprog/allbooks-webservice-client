package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Pending;

public interface PendingWebservice {

	public Pending[] getReaderPendings(int id);

	public void savePending(Pending pending);

	public Pending getReadersPending(int reader1Id, int reader2Id);

	public void deletePending(int pendingIdInt);

	public Pending getPendingByRecipentIdAndSenderId(int recipentId, int senderId);

}
