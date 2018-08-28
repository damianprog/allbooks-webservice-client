package com.allbooks.webapp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.FavouriteGenres;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.FavoriteGenresService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.service.PhotoService;

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
	
	@Autowired
	private PhotoService photoService;
	
	public Book getRecommendedBook() {
		
		int readerId = contextService.getLoggedReaderId();
		
		FavouriteGenres fGenres = favoriteGenresService.getFavoriteGenresByReaderId(readerId);
		
		if(fGenres == null)
			return null;
		
		List<String> fGenresList = Arrays.asList(fGenres.getFavoriteGenres().split(","));
		
		Book foundBook = null;
		
		Collections.shuffle(fGenresList);
		
		for(String category : fGenresList) {	
			
			int[] excludedIds = readerBookService.getReaderBooksBooksIdsByReaderIdAndCategory(readerId, category);
			
			foundBook = bookService.getBookByCategoryExceptBooksWithIds(category,excludedIds);
			
			if(foundBook != null)
				break;
		}
			
		if(foundBook == null)
			foundBook = bookService.getBooksByCategory(fGenresList.get(0), 1).getContent().get(0);
		
		byte[] resizedPhoto = photoService.resize(foundBook.getBookPhoto(),95 , 150);
		
		foundBook.setEncodedBookPhoto(photoService.getEncodedImage(resizedPhoto));
		
		return foundBook;
	}
	
}
