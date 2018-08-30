package com.allbooks.webapp.utils.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.factories.CommentFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.entity.CommentData;
import com.allbooks.webapp.utils.saver.CommentSaver;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class SubmitCommentTest {

	@InjectMocks
	private CommentSaver submitComment;

	@Mock
	private ReaderService readerServiceMock;

	@Mock
	private ReviewService reviewServiceMock;

	@Mock
	private RatingService ratingServiceMock;

	@Mock
	private CommentService commentServiceMock;

	@Mock
	private BookService bookServiceMock;

	@Mock
	private SecurityContextService contextServiceMock;

	@Mock
	private CommentData commentDataMock;

	@Mock
	private Comment commentMock;

	@Mock
	private Reader readerMock;

	@Mock
	private Book bookMock;

	@Mock
	private Rating ratingMock;

	@Mock
	private Review reviewMock;
	
	@Mock
	private CommentFactory commentFactoryMock;
	
	private int readerId;

	private int bookId;
	
	private int reviewId;

	private String commentText;
	
	@Test
	public void submitTest() {

		when(commentDataMock.getCommentText()).thenReturn(commentText);

		when(commentDataMock.getBookId()).thenReturn(bookId);

		when(commentDataMock.getReviewId()).thenReturn(reviewId);
		
		when(contextServiceMock.getLoggedReaderId()).thenReturn(readerId);

		when(commentFactoryMock.createReviewCommentInstance()).thenReturn(commentMock);
		
		when(readerServiceMock.getReaderById(readerId)).thenReturn(readerMock);

		when(bookServiceMock.getBookById(bookId)).thenReturn(bookMock);

		when(ratingServiceMock.getReaderRatingObject(readerId,bookId)).thenReturn(ratingMock);

		when(reviewServiceMock.getReviewById(reviewId)).thenReturn(reviewMock);
		
		submitComment.save(commentDataMock);
		
		verify(commentDataMock).getCommentText();
		verify(commentDataMock).getBookId();
		verify(commentDataMock).getReviewId();
		verify(contextServiceMock).getLoggedReaderId();
		
		
		verify(readerServiceMock).getReaderById(readerId);
		verify(bookServiceMock).getBookById(bookId);
		verify(ratingServiceMock).getReaderRatingObject(readerId,bookId);
		verify(reviewServiceMock).getReviewById(reviewId);
		
		verify(commentMock).setText(commentText);
		verify(commentMock).setRating(ratingMock);
		verify(commentMock).setPostingReader(readerMock);
		verify(commentMock).setReview(reviewMock);
		verify(commentMock).setBook(bookMock);

		verify(commentServiceMock).submitComment(commentMock);
		
	}

}
