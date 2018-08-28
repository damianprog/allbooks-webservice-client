package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.FavouriteGenres;

public interface FavoriteGenresWebservice {

	void saveFavoriteGenres(FavouriteGenres favoriteGenres);

	FavouriteGenres getFavoriteGenresByReaderId(int readerId);

	FavouriteGenres getFavoriteGenresById(int favoriteGenresId);

}
