package com.allbooks.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.FavoriteGenres;
import com.allbooks.webapp.webservice.FavoriteGenresWebservice;

@Service
public class FavoriteGenresServiceImpl implements FavoriteGenresService{

	@Autowired
	private FavoriteGenresWebservice favoriteGenresWebservice;
	
	@Override
	public void saveFavoriteGenres(FavoriteGenres favoriteGenres) {
		favoriteGenresWebservice.saveFavoriteGenres(favoriteGenres);
	}

	@Override
	public FavoriteGenres getFavoriteGenresByReaderId(int readerId) {
		return favoriteGenresWebservice.getFavoriteGenresByReaderId(readerId);
	}

	@Override
	public FavoriteGenres getFavoriteGenresById(int favoriteGenresId) {
		return favoriteGenresWebservice.getFavoriteGenresById(favoriteGenresId);
	}

	
	
}
