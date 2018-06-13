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
import org.springframework.test.context.junit4.SpringRunner;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.ReaderBooksForMyBooksGetter;
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
	
	private int readerId = 1;
	
	private int bookId = 1;
	
	private double bookRating = 5;
	
	private byte[] bookPic = {};
	
	private String encodedBookPic = "";
	
	@Test
	public void getPreparedReaderBooksTest() {
		
		List<ReaderBook> readerBooks = new ArrayList<>();
		readerBooks.add(readerBookMock);
		
		when(readerBookServiceMock.getReaderBooks(readerId)).thenReturn(readerBooks);
		when(readerBookMock.getBook()).thenReturn(bookMock);
		when(bookMock.getId()).thenReturn(bookId);
		when(ratingServiceMock.getOverallRating(bookId)).thenReturn(bookRating);
		when(readerBookMock.getBookPic()).thenReturn(bookPic);
		when(photoServiceMock.getEncodedImage(bookPic)).thenReturn(encodedBookPic);
		
		readerBooksForMyBooksGetter.getPreparedReaderBooks(readerId);
		
		verify(readerBookServiceMock).getReaderBooks(readerId);
		verify(readerBookMock).getBook();
		verify(bookMock).getId();
		verify(ratingServiceMock).getOverallRating(bookId);
		verify(readerBookMock).getBookPic();
		verify(photoServiceMock).getEncodedImage(bookPic);

		verify(readerBookMock).setOverallRating(bookRating);
		verify(readerBookMock).setEncodedBookPic(encodedBookPic);
		
	}
	
}
