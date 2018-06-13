package com.allbooks.webapp.utils.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.ReaderBookAndRatingModelCreator;
import com.allbooks.webapp.utils.SecurityContextService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReaderBookAndRatingModelCreatorTest {

	@InjectMocks
	private ReaderBookAndRatingModelCreator readerBookAndRatingModelCreator;
	
	@Mock
	private HttpSession sessionMock;
	
	@Mock
	private SecurityContextService securityContextServiceMock;
	
	@Mock
	private ModelMapFactory modelMapFactoryMock;
	
	@Mock
	private RatingService ratingServiceMock;
	
	@Mock
	private ReaderBookService readerBookServiceMock;
	
	@Mock
	private ModelMap modelMapMock;
	
	@Mock
	private Rating ratingMock;
	
	@Mock
	private ReaderBook readerBookMock;
	
	private int bookId = 1;
	
	private int readerId = 1;
	
	@Test
	public void createModelTest() {
		
		when(modelMapFactoryMock.createInstance()).thenReturn(modelMapMock);
		when(securityContextServiceMock.isReaderAuthenticated()).thenReturn(true);
		when(sessionMock.getAttribute("readerId")).thenReturn(readerId);
		when(ratingServiceMock.getLoggedReaderRatingObject(bookId)).thenReturn(ratingMock);
		when(readerBookServiceMock.getReaderBook(bookId, readerId)).thenReturn(readerBookMock);
		
		readerBookAndRatingModelCreator.createModel(bookId);
		
		verify(modelMapFactoryMock).createInstance();
		verify(securityContextServiceMock).isReaderAuthenticated();
		verify(sessionMock).getAttribute("readerId");
		verify(ratingServiceMock).getLoggedReaderRatingObject(bookId);
		verify(readerBookServiceMock).getReaderBook(bookId, readerId);

		verify(modelMapMock).addAttribute("readerBook",readerBookMock);
		verify(modelMapMock).addAttribute("rating",ratingMock);
				
	}
	
}
