package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.FavoriteGenres;

public interface FavoriteGenresWebservice {

	void saveFavoriteGenres(FavoriteGenres favoriteGenres);

	FavoriteGenres getFavoriteGenresByReaderId(int readerId);

	FavoriteGenres getFavoriteGenresById(int favoriteGenresId);

}
