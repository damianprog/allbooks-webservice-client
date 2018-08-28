package com.allbooks.webapp.service;

import com.allbooks.webapp.entity.FavouriteGenres;

public interface FavoriteGenresService {

	void saveFavoriteGenres(FavouriteGenres favoriteGenres);
	
	FavouriteGenres getFavoriteGenresByReaderId(int readerId);
	
	FavouriteGenres getFavoriteGenresById(int favoriteGenresId);	
	
}
