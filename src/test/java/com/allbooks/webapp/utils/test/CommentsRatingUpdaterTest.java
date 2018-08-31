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
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.utils.bookactions.ReviewCommentsRatingSetter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CommentsRatingUpdaterTest {

	@InjectMocks
	private ReviewCommentsRatingSetter commentsRatingUpdater;
	
	@Mock
	private CommentService commentServiceMock;
	
	@Mock
	private Rating ratingMock;
	
	@Mock
	private Reader readerMock;
	
	@Mock
	private Book bookMock;
	
	@Mock
	private Comment commentMock;
	
	private int readerId = 1;
	
	private int bookId = 1;
	
	@Test
	public void updateTest() {
		
		List<Comment> comments = new ArrayList<>();
		comments.add(commentMock);
		
		when(ratingMock.getReader()).thenReturn(readerMock);
		when(readerMock.getId()).thenReturn(readerId);
		
		when(ratingMock.getBook()).thenReturn(bookMock);
		when(bookMock.getId()).thenReturn(bookId);
		
		when(commentServiceMock.getCommentsByReaderIdAndBookId(readerId, bookId)).thenReturn(comments);
		
		when(commentMock.getRating()).thenReturn(null);
		
		commentsRatingUpdater.updateRatingInReaderReviewComments(ratingMock);
		
		verify(ratingMock).getReader();
		verify(readerMock).getId();
		
		verify(ratingMock).getBook();
		verify(bookMock).getId();
		
		verify(commentServiceMock).getCommentsByReaderIdAndBookId(readerId, bookId);
		
		verify(commentMock).getRating();
		
		verify(commentMock).setRating(ratingMock);
		verify(commentServiceMock).submitComment(commentMock);
		
	}
	
}
