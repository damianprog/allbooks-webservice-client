package com.allbooks.webapp.utils.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.PendingService;
import com.allbooks.webapp.utils.model.LoggedReaderModelProfileCreator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class LoggedUserModelProfileCreatorTest {

	@InjectMocks
	private LoggedReaderModelProfileCreator creator;

	@Mock
	private FriendsService friendsServiceMock;

	@Mock
	private PendingService pendingServiceMock;
	
	@Mock
	private ModelMapFactory modelMapFactoryMock;
	
	@Mock
	private SecurityContextService securityContextServiceMock;
	
	@Mock
	private Reader currentReaderMock; 
	
	@Mock
	private ModelMap modelMapMock;
	
	@Mock
	private List<Pending> friendsInvitesMock;
	
	String loggedReaderName = "loggedReaderName";
	
	int loggedReaderId = 1;
	
	@Test
	public void guestProfileModelTest() {
		
		int currentReaderId = 2;
		
		when(modelMapFactoryMock.createInstance()).thenReturn(modelMapMock);
		when(securityContextServiceMock.getLoggedReaderUserName()).thenReturn(loggedReaderName);
		when(securityContextServiceMock.isReaderAuthenticated()).thenReturn(true);
		when(securityContextServiceMock.getLoggedReaderId()).thenReturn(loggedReaderId);
		when(currentReaderMock.getId()).thenReturn(currentReaderId);
		
		when(friendsServiceMock.areTheyFriends(loggedReaderId,currentReaderId)).thenReturn(true);
		when(pendingServiceMock.checkPending(loggedReaderId,currentReaderId)).thenReturn(false);
		
		creator.createModel(currentReaderMock);
		
		verify(modelMapFactoryMock).createInstance();
		verify(securityContextServiceMock).getLoggedReaderUserName();
		verify(securityContextServiceMock).isReaderAuthenticated();
		verify(securityContextServiceMock).getLoggedReaderId();
		verify(currentReaderMock).getId();
		verify(friendsServiceMock).areTheyFriends(loggedReaderId,currentReaderId);
		verify(pendingServiceMock).checkPending(loggedReaderId,currentReaderId);
		
		verify(modelMapMock).addAttribute("areTheyFriends",true);
		verify(modelMapMock).addAttribute("pending",false);
		verify(modelMapMock).addAttribute("invite",true);
		
		verify(modelMapMock).addAttribute("principalName",loggedReaderName);
	
	}
	
	@Test
	public void loggedReaderProfileModelTest() {
		
		int currentReaderId = 1;
		
		when(modelMapFactoryMock.createInstance()).thenReturn(modelMapMock);
		when(securityContextServiceMock.getLoggedReaderUserName()).thenReturn(loggedReaderName);
		when(securityContextServiceMock.isReaderAuthenticated()).thenReturn(true);
		when(securityContextServiceMock.getLoggedReaderId()).thenReturn(loggedReaderId);
		when(currentReaderMock.getId()).thenReturn(currentReaderId);

		when(pendingServiceMock.getFriendsInvitesByReaderId(currentReaderId)).thenReturn(friendsInvitesMock);
		
		creator.createModel(currentReaderMock);
		
		verify(modelMapFactoryMock).createInstance();
		verify(securityContextServiceMock).getLoggedReaderUserName();
		verify(securityContextServiceMock).isReaderAuthenticated();
		verify(securityContextServiceMock).getLoggedReaderId();
		verify(currentReaderMock).getId();
		
		verify(modelMapMock).addAttribute("friendsInvites",friendsInvitesMock);
		verify(modelMapMock).addAttribute("principalName",loggedReaderName);
	
	}
	
}
