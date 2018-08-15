package com.allbooks.webapp.service;

import com.allbooks.webapp.entity.FavoriteGenres;

public interface FavoriteGenresService {

	void saveFavoriteGenres(FavoriteGenres favoriteGenres);
	
	FavoriteGenres getFavoriteGenresByReaderId(int readerId);
	
	FavoriteGenres getFavoriteGenresById(int favoriteGenresId);	
	
}
