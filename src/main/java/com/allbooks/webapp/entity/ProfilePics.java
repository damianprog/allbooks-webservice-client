package com.allbooks.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfilePics {

	private int id;


	private byte[] pic;
	

	public ProfilePics(byte[] pic) {
		this.pic = pic;
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
