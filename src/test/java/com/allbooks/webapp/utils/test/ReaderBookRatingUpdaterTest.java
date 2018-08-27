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
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.readerbook.ReaderBooksRatingSetter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReaderBookRatingUpdaterTest {

	@InjectMocks
	private ReaderBooksRatingSetter readerBookRatingUpdater;
	
	@Mock
	private ReaderBookService readerBookServiceMock;
	
	@Mock
	private Rating ratingMock;
	
	@Mock
	private Book bookMock;
	
	@Mock
	private Reader readerMock;
	
	@Mock
	private ReaderBook readerBookMock;
	
	private int bookId = 1;

	private int readerId = 1;
	
	@Test
	public void updateTest() {
		
		when(ratingMock.getBook()).thenReturn(bookMock);
		when(ratingMock.getReader()).thenReturn(readerMock);
		when(bookMock.getId()).thenReturn(bookId);
		when(readerMock.getId()).thenReturn(readerId);
		
		when(readerBookServiceMock.getReaderBook(bookId,readerId)).thenReturn(readerBookMock);
		
		when(readerBookMock.getReaderRating()).thenReturn(null);
		
		readerBookRatingUpdater.update(ratingMock);
		
		verify(ratingMock).getBook();
		verify(ratingMock).getReader();
		verify(bookMock).getId();
		verify(readerMock).getId();
		
		verify(readerBookServiceMock).getReaderBook(bookId,readerId);
		
		verify(readerBookMock).getReaderRating();
		
		verify(readerBookMock).setReaderRating(ratingMock);
		verify(readerBookServiceMock).saveReaderBook(readerBookMock);
		
	}
	
}
