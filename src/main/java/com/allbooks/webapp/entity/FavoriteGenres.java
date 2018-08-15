package com.allbooks.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FavoriteGenres {

	private int id;

	private Reader reader;

	private String favoriteGenres;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public String getFavoriteGenres() {
		return favoriteGenres;
	}

	public void setFavoriteGenres(String favoriteGenres) {
		this.favoriteGenres = favoriteGenres;
	}

}
