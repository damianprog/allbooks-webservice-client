package com.allbooks.webapp.utils.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.ReaderBooksQuantitiesGetter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReaderBookQuantitiesGetterTest {

	@InjectMocks
	private ReaderBooksQuantitiesGetter readerBooksQuantitiesGetter;
	
	@Mock
	private ReaderBookService readerBookService;
	
	@Mock
	private ReaderBook readerBookMock1;
	
	@Mock
	private ReaderBook readerBookMock2;
	
	@Mock
	private ReaderBook readerBookMock3;
	
	private int readerId = 1;
	
	@Test
	public void getByReaderIdTest() {
		
		List<ReaderBook> readerBooks = new ArrayList<>();
		readerBooks.add(readerBookMock1);
		readerBooks.add(readerBookMock2);
		readerBooks.add(readerBookMock3);
		
		when(readerBookService.getReaderBooks(readerId)).thenReturn(readerBooks);
		
		when(readerBookMock1.getShelves()).thenReturn("Read");
		when(readerBookMock2.getShelves()).thenReturn("Currently Reading");
		when(readerBookMock3.getShelves()).thenReturn("Want To Read");
		
		Map<String,Integer> quantitiesMap = readerBooksQuantitiesGetter.getByReaderId(readerId);
		
		assertEquals(quantitiesMap.get("read"),1,0);
		assertEquals(quantitiesMap.get("wantToRead"),1,0);
		assertEquals(quantitiesMap.get("currentlyReading"),1,0);
		
	}
	
}
