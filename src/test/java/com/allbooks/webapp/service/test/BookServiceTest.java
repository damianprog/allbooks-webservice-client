package com.allbooks.webapp.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.BookServiceImpl;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.webservice.UtilsWebservice;
import com.allbooks.webapp.webservice.BookWebservice;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookServiceTest {

	@Autowired
	MockMvc mockMvc;

	BookService bookService;

	ReaderService readerServiceMock;

	BookWebservice bookWebserviceMock;

	UtilsWebservice utilsWebserviceMock;

	@Before
	public void setupMock() {
		readerServiceMock = mock(ReaderService.class);
		bookWebserviceMock = mock(BookWebservice.class);
		utilsWebserviceMock = mock(UtilsWebservice.class);
		bookService = new BookServiceImpl(bookWebserviceMock, readerServiceMock, utilsWebserviceMock);
	}

	@Test
	public void getBookIdTest() throws Exception {

		when(utilsWebserviceMock.getBookId("bookname")).thenReturn(1);

		bookService.getBookId("bookname");

		verify(utilsWebserviceMock).getBookId("bookname");
		
		assertEquals(1,bookService.getBookId("bookname"));

	}

	@Test
	public void getBookNameTest() throws Exception {

		when(utilsWebserviceMock.getBookName(1)).thenReturn("bookname");

		bookService.getBookName(1);

		verify(utilsWebserviceMock).getBookName(1);
		
		assertEquals("bookname",bookService.getBookName(1));

	}

	@Test
	public void deleteReaderBookById() throws Exception {

		Reader reader = new Reader();
		reader.setId(1);

		when(readerServiceMock.getReaderByUsername("username")).thenReturn(reader);

		bookService.deleteReaderBookById(1, "username");

		verify(readerServiceMock).getReaderByUsername("username");
		verify(bookWebserviceMock).deleteReaderBookByReaderIdAndBookId(1, 1);


	}

}
