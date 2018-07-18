package com.allbooks.webapp.utils.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.bookactions.CommentsRatingUpdater;
import com.allbooks.webapp.utils.bookactions.RatingSaver;
import com.allbooks.webapp.utils.readerbook.ReaderBookRatingUpdater;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class RatingSaverTest {

	@InjectMocks
	private RatingSaver ratingSaver;
	
	@Mock
	private SecurityContextService contextServiceMock;

	@Mock
	private RatingService ratingServiceMock;
	
	@Mock
	private ReaderService readerServiceMock;

	@Mock
	private BookService bookServiceMock;
	
	@Mock
	private RatingData ratingDataMock;
	
	@Mock
	private Rating ratingMock;
	
	@Mock
	private Reader readerMock;
	
	@Mock
	private Book bookMock;
	
	@Mock
	private ReaderBookRatingUpdater readerBookRatingUpdaterMock;
	
	@Mock
	private CommentsRatingUpdater commentsRatingUpdaterMock;
	
	private int readerId = 1;
	
	private int bookId = 1;
	
	@Test
	public void saveTest() {
		
		when(contextServiceMock.getLoggedReaderId()).thenReturn(readerId);
		when(ratingDataMock.getRating()).thenReturn(ratingMock);
		when(readerServiceMock.getReaderById(readerId)).thenReturn(readerMock);
		when(ratingDataMock.getBookId()).thenReturn(bookId);
		when(bookServiceMock.getBook(bookId)).thenReturn(bookMock);
		when(ratingServiceMock.submitRating(ratingMock)).thenReturn(ratingMock);
		
		ratingSaver.save(ratingDataMock);
		
		verify(contextServiceMock).getLoggedReaderId();
		verify(ratingDataMock).getRating();
		verify(readerServiceMock).getReaderById(readerId);
		verify(ratingDataMock).getBookId();
		verify(bookServiceMock).getBook(bookId);
		verify(ratingMock).setReader(readerMock);
		verify(ratingMock).setBook(bookMock);
		verify(readerBookRatingUpdaterMock).update(ratingMock);
		verify(commentsRatingUpdaterMock).update(ratingMock);
		
		
		
	}
	
}
