package com.allbooks.webapp.utils.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesStates;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.readerbook.ReaderBooksForMyBooksGetter;
import com.allbooks.webapp.utils.service.PhotoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReaderBooksForMyBooksGetterTest {

	@InjectMocks
	private ReaderBooksForMyBooksGetter readerBooksForMyBooksGetter;
	
	@Mock
	private PhotoService photoServiceMock;
	
	@Mock
	private RatingService ratingServiceMock;
	
	@Mock
	private ReaderBookService readerBookServiceMock;
	
	@Mock
	private ReaderBook readerBookMock;
	
	@Mock
	private Book bookMock;
	
	@Mock
	private Page<ReaderBook> readerBooksPageMock;
	
	private int readerId = 1;
	
	private int bookId = 1;
	
	private double bookRating = 5;
	
	private ShelvesStates shelvesStates = ShelvesStates.READ;
	
	private int page = 1;
	
	@Test
	public void getPreparedReaderBooksTest() {
		
		List<ReaderBook> readerBooks = new ArrayList<>();
		readerBooks.add(readerBookMock);
		
		when(readerBookServiceMock.getReaderBooksByShelvesPages(readerId,shelvesStates , page)).thenReturn(readerBooksPageMock);
		when(readerBooksPageMock.getContent()).thenReturn(readerBooks);
		
		when(readerBookMock.getBook()).thenReturn(bookMock);
		when(bookMock.getId()).thenReturn(bookId);
		when(ratingServiceMock.getOverallRating(bookId)).thenReturn(bookRating);
		
		readerBooksForMyBooksGetter.getPreparedReaderBooks(readerId, shelvesStates, page);
		
		verify(readerBookServiceMock).getReaderBooksByShelvesPages(readerId,shelvesStates , page);
		verify(readerBooksPageMock).getContent();
		verify(readerBookMock).getBook();
		verify(ratingServiceMock).getOverallRating(bookId);
		
		verify(photoServiceMock).encodeAndResizeBookPhotoInBookChildren(readerBooks,100 , 160);
		verify(readerBookMock).setOverallRating(bookRating);
		
	}
	
}
