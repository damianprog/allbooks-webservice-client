package com.allbooks.webapp.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.FavouriteGenres;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FavoriteGenresService;
import com.google.gson.Gson;

@Component
public class FavouriteGenresJsonArrayGetter {

	@Autowired
	private ModelMapFactory modelMapFactory;
	
	@Autowired
	private FavoriteGenresService favoriteGenresService;
	
	@Autowired
	private SecurityContextService contextService;
	
	public ModelMap get() {

		int readerId = contextService.getLoggedReaderId();
		
		ModelMap modelMap = modelMapFactory.createInstance();
		
		String jsonArray = getJsonArrayOfFavouriteGenres(readerId);
		
		modelMap.addAttribute("favoriteGenres", jsonArray);
		
	
		return modelMap;
	}

	private String getJsonArrayOfFavouriteGenres(int readerId) {
		FavouriteGenres favoriteGenres = favoriteGenresService.getFavoriteGenresByReaderId(readerId);

		if (favoriteGenres != null) 
			return createJsonArrayOfFavouriteGenres(favoriteGenres);
		else
			return null;
	}

	private String createJsonArrayOfFavouriteGenres(FavouriteGenres favoriteGenres) {
		List<String> genres = Arrays.asList(favoriteGenres.getFavoriteGenres().split(","));

		return new Gson().toJson(genres);
	}

	
	
}
