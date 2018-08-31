package com.allbooks.webapp.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.FavouriteGenres;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.FavoriteGenresService;
import com.allbooks.webapp.service.ReaderBookService;

@Component
public class RecommendationGetter {

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private FavoriteGenresService favoriteGenresService;

	@Autowired
	private BookService bookService;

	private int readerId;

	private FavouriteGenres favouriteGenres;

	private Book foundBook;

	public Book getRecommendedBook() {

		initializeThisFields();

		initializeRecommendedBookIfReaderHasFavoriteGenres();

		return foundBook;
	}

	private void initializeThisFields() {
		this.readerId = contextService.getLoggedReaderId();
		this.favouriteGenres = favoriteGenresService.getFavoriteGenresByReaderId(readerId);
	}

	private void initializeRecommendedBookIfReaderHasFavoriteGenres() {
		if (favouriteGenres != null)
			initializeRecommendedBook();

	}

	private void initializeRecommendedBook() {
		List<String> fGenresList = getShuffledFavouriteGenresList();

		for (String genre : fGenresList) {
			
			int[] excludedIds = readerBookService.getReaderBooksBooksIdsByReaderIdAndCategory(readerId, genre);

			foundBook = bookService.getBookByCategoryExceptBooksWithIds(genre, excludedIds);

			if (foundBook != null)
				break;
		}

		if (foundBook == null)
			foundBook = bookService.getBooksByCategory(fGenresList.get(0), 1).getContent().get(0);
	}

	private List<String> getShuffledFavouriteGenresList() {
		List<String> fGenresList = Arrays.asList(favouriteGenres.getFavoriteGenres().split(","));
		Collections.shuffle(fGenresList);

		return fGenresList;
	}

}
