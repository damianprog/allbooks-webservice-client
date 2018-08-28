package com.allbooks.webapp.utils.saver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.FavouriteGenres;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FavoriteGenresService;
import com.allbooks.webapp.service.ReaderService;

@Component
public class FavouriteGenresSaver {

	@Autowired
	private FavoriteGenresService favoriteGenresService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private SecurityContextService contextService;

	private FavouriteGenres fGenres;

	private Reader loggedReader;
	
	public void save(List<String> categoriesChecked) {

		setLoggedReader();
		
		setLoggedReaderFavouriteGenres(categoriesChecked);

		favoriteGenresService.saveFavoriteGenres(fGenres);
	}

	private void setLoggedReader() {
		
		int readerId = contextService.getLoggedReaderId();
		
		this.loggedReader = readerService.getReaderById(readerId);
		
	}

	private void setLoggedReaderFavouriteGenres(List<String> categoriesChecked) {

		fGenres = favoriteGenresService.getFavoriteGenresByReaderId(loggedReader.getId());

		initializeFavouriteGenresIfNotExists();

		fGenres.setFavoriteGenres(String.join(",", categoriesChecked));
	}

	private void initializeFavouriteGenresIfNotExists() {
		if (fGenres == null) {
			fGenres = new FavouriteGenres();
			fGenres.setReader(loggedReader);
		}
	}

}
