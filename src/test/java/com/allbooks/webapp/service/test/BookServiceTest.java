package com.allbooks.webapp.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.BookServiceImpl;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.AverageRating;
import com.allbooks.webapp.utils.Sorter;
import com.allbooks.webapp.utils.webservice.UtilsWebservice;
import com.allbooks.webapp.webservice.BookWebservice;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookServiceTest {

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	BookServiceImpl bookService;

	@Mock
	ReaderService readerServiceMock;

	@Mock
	BookWebservice bookWebserviceMock;

	@Mock
	UtilsWebservice utilsWebserviceMock;

	@Mock
	AverageRating averageRatingMock;

	@Mock
	Sorter sorterMock;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getBookIdTest() throws Exception {

		when(utilsWebserviceMock.getBookId("bookname")).thenReturn(1);

		bookService.getBookId("bookname");

		verify(utilsWebserviceMock, times(1)).getBookId("bookname");

		assertEquals(1, bookService.getBookId("bookname"));

	}

	@Test
	public void getBookNameTest() throws Exception {

		when(utilsWebserviceMock.getBookName(1)).thenReturn("bookname");

		bookService.getBookName(1);

		verify(utilsWebserviceMock, times(1)).getBookName(1);

		assertEquals("bookname", bookService.getBookName(1));

	}

	@Test
	public void deleteReaderBookByIdTest() throws Exception {

		Reader reader = Mockito.mock(Reader.class);

		when(reader.getId()).thenReturn(1);
		when(readerServiceMock.getReaderByUsername("username")).thenReturn(reader);

		bookService.deleteReaderBookById(1, "username");

		verify(readerServiceMock, times(1)).getReaderByUsername("username");
		verify(bookWebserviceMock, times(1)).deleteReaderBookByReaderIdAndBookId(1, 1);

	}

	@Test
	public void getReaderRatingObjectTest() {

		Reader reader = Mockito.mock(Reader.class);

		when(reader.getId()).thenReturn(1);
		when(readerServiceMock.getReaderByUsername("username")).thenReturn(reader);
		when(utilsWebserviceMock.getBookId("bookname")).thenReturn(1);

		bookService.getReaderRatingObject("bookname", "username");

		verify(readerServiceMock, times(1)).getReaderByUsername("username");
		verify(bookWebserviceMock, times(1)).getReaderRatingObject(1, 1);

	}

	@Test
	public void getReaderRatingObjectNullCaseTest() {

		Reader reader = Mockito.mock(Reader.class);

		when(reader.getId()).thenReturn(1);

		when(readerServiceMock.getReaderByUsername("username")).thenReturn(reader);
		when(utilsWebserviceMock.getBookId("bookname")).thenReturn(1);

		when(bookWebserviceMock.getReaderRatingObject(1, 1)).thenReturn(null);

		assertEquals(null, bookWebserviceMock.getReaderRatingObject(1, 1));
		assertEquals(Rating.class, bookService.getReaderRatingObject("bookname", "username").getClass());

	}

	@Test
	public void submitRatingTest() {

		Rating rating = Mockito.mock(Rating.class);

		bookService.submitRating(rating);

		verify(bookWebserviceMock, times(1)).submitRating(rating);

	}

	@Test
	public void getOverallRating() {

		Rating[] ratings = { new Rating() };

		when(utilsWebserviceMock.getBookId("bookname")).thenReturn(1);
		when(bookWebserviceMock.getBookRatings(1)).thenReturn(ratings);
		when(averageRatingMock.getAverageRating(ratings)).thenReturn(1);

		bookService.getOverallRating("bookname");

		verify(bookWebserviceMock, times(1)).getBookRatings(1);
		verify(averageRatingMock).getAverageRating(ratings);

	}

	//the whole method is done when gets invocated in test
	
	@Test
	public void getBookReviewsTest() {

		Review[] reviewsArray = { new Review() };

		when(utilsWebserviceMock.getBookId("bookname")).thenReturn(1);
		when(bookWebserviceMock.getBookReviews(1)).thenReturn(reviewsArray);

		bookService.getBookReviews("bookname");

		verify(utilsWebserviceMock, times(1)).getBookId("bookname");
		verify(bookWebserviceMock, times(1)).getBookReviews(1);
		verify(sorterMock, times(1)).sortReviewsDescending(Arrays.asList(reviewsArray));

	}

	@Test
	public void getReaderRatingTest() {
		
		Rating rating = Mockito.mock(Rating.class);
		Reader reader = new Reader();
		reader.setId(1);
		
		when(readerServiceMock.getReaderByUsername("username")).thenReturn(reader);
		when(utilsWebserviceMock.getBookId("bookname")).thenReturn(1);
		when(bookWebserviceMock.getReaderRatingObject(1,1)).thenReturn(rating);
		
		bookService.getReaderRating("username","bookname");
		
		verify(readerServiceMock,times(1)).getReaderByUsername("username");
		verify(utilsWebserviceMock,times(1)).getBookId("bookname");
		verify(bookWebserviceMock,times(1)).getReaderRatingObject(1,1);
		
	}
	
	@Test
	public void dropLikeTest() {
		
		Review review = new Review();
		
		when(bookWebserviceMock.getReviewById(1)).thenReturn(review);
		
	}
	
}
