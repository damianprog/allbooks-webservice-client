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
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.entity.ReviewData;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.SecurityContextService;
import com.allbooks.webapp.utils.bookactions.ReviewSaver;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReviewSaverTest {

	@InjectMocks
	private ReviewSaver reviewSaver;
	
	@Mock
	private BookService bookServiceMock;
	
	@Mock
	private SecurityContextService contextServiceMock;
	
	@Mock
	private ReaderService readerServiceMock;
	
	@Mock
	private ReviewService reviewServiceMock;

	@Mock
	private RatingService ratingServiceMock;
	
	@Mock
	private ReviewData reviewDataMock;
	
	@Mock
	private Reader readerMock;
	
	@Mock
	private Book bookMock;
	
	@Mock
	private Review reviewMock;
	
	@Mock
	private Rating ratingMock;
	
	private int readerId = 1;
	
	private int bookId = 2;
	
	@Test
	public void saveTest() {
		
		when(contextServiceMock.getLoggedReaderId()).thenReturn(readerId);
		when(reviewDataMock.getBookId()).thenReturn(bookId);
		when(readerServiceMock.getReaderById(readerId)).thenReturn(readerMock);
		when(bookServiceMock.getBook(bookId)).thenReturn(bookMock);

		when(reviewDataMock.getReview()).thenReturn(reviewMock);
		when(ratingServiceMock.getReaderRatingObject(readerId, bookId)).thenReturn(ratingMock);
		
		reviewSaver.save(reviewDataMock);
		
		verify(contextServiceMock).getLoggedReaderId();
		verify(reviewDataMock).getBookId();
		verify(readerServiceMock).getReaderById(readerId);
		verify(bookServiceMock).getBook(bookId);
		
		verify(reviewDataMock).getReview();
		verify(ratingServiceMock).getReaderRatingObject(readerId, bookId);
		
		verify(reviewMock).setBook(bookMock);
		verify(reviewMock).setReader(readerMock);
		verify(reviewMock).setRating(ratingMock);
		
	}
	
}
