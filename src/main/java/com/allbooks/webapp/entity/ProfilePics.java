package com.allbooks.webapp.entity;

public class ProfilePics {

	private int id;

	private int readerId;

	private byte[] pic;
	
	public int getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public ProfilePics(byte[] pic,int readerId) {
		this.pic = pic;
		this.readerId = readerId;
	}

	public ProfilePics() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

}
