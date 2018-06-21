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
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderRole;
import com.allbooks.webapp.factories.ReaderRoleFactory;
import com.allbooks.webapp.utils.reader.ReaderRoleSaver;
import com.allbooks.webapp.webservice.ReaderWebservice;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReaderRoleSaverTest {

	@InjectMocks
	private ReaderRoleSaver readerRoleSaver;
	
	@Mock
	private ReaderWebservice readerWebserviceMock;
	
	@Mock
	private ReaderRoleFactory readerRoleFactoryMock;
	
	@Mock
	private ReaderRole readerRoleMock;
	
	@Mock
	private Reader readerMock;
	
	private int readerId = 1;
	
	private int roleId = 2;
	
	@Test
	public void saveTest() {
		
		when(readerRoleFactoryMock.createInstance()).thenReturn(readerRoleMock);
		when(readerMock.getId()).thenReturn(readerId);
		
		readerRoleSaver.save(readerMock);
		
		verify(readerRoleFactoryMock).createInstance();
		verify(readerMock).getId();
		
		verify(readerRoleMock).setReaderId(readerId);
		verify(readerRoleMock).setRoleId(roleId);

		verify(readerWebserviceMock).saveReaderRole(readerRoleMock);
		
	} 
	
}
