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
import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.factories.ListFactory;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.utils.ReaderFriendsInitializer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ReaderFriendsInitializerTest {

	@InjectMocks
	private ReaderFriendsInitializer readerFriendsInitializer;
	
	@Mock
	private FriendsService friendsServiceMock;
	
	@Mock
	private ListFactory listFactoryMock;
	
	@Mock
	private List<Object> friendsReadersList;
	
	@Mock
	private Friends friendsMock;
	
	@Mock
	private Reader reader1Mock;
	
	@Mock
	private Reader reader2Mock;
	
	private int readerId = 1;
	
	@Test
	public void getReaderFriendsTest() {

		List<Friends> friendsList = new ArrayList<>();
		friendsList.add(friendsMock);
		
		when(friendsServiceMock.getAllReaderFriends(readerId)).thenReturn(friendsList);
		when(listFactoryMock.createArrayList()).thenReturn(friendsReadersList);
		
		when(friendsMock.getReader1()).thenReturn(reader1Mock);
		when(friendsMock.getReader2()).thenReturn(reader2Mock);
		
		when(reader1Mock.getId()).thenReturn(readerId);
		
		readerFriendsInitializer.getReaderFriends(readerId);
		
		verify(friendsServiceMock).getAllReaderFriends(readerId);
		verify(listFactoryMock).createArrayList();
		
		verify(friendsMock).getReader1();
		verify(friendsMock).getReader2();
		
		verify(reader1Mock).getId();

		verify(friendsReadersList).add(reader2Mock);
		
	}
	
}
