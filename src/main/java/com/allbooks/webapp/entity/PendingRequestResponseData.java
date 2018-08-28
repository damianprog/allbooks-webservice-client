package com.allbooks.webapp.entity;

public class PendingRequestResponseData {

	private int senderId;

	private int recipentId;

	private int pendingId;

	private String acceptOrAbort;

	public PendingRequestResponseData(int senderId, int recipentId, int pendingId, String acceptOrAbort) {
		this.senderId = senderId;
		this.recipentId = recipentId;
		this.pendingId = pendingId;
		this.acceptOrAbort = acceptOrAbort;
	}

	public PendingRequestResponseData() {
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getRecipentId() {
		return recipentId;
	}

	public void setRecipentId(int recipentId) {
		this.recipentId = recipentId;
	}

	public int getPendingId() {
		return pendingId;
	}

	public void setPendingId(int pendingId) {
		this.pendingId = pendingId;
	}

	public String getAcceptOrAbort() {
		return acceptOrAbort;
	}

	public void setAcceptOrAbort(String acceptOrAbort) {
		this.acceptOrAbort = acceptOrAbort;
	}

}
