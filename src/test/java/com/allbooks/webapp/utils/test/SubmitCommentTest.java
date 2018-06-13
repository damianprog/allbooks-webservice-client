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

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.CommentData;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.SubmitComment;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class SubmitCommentTest {

	@InjectMocks
	private SubmitComment submitComment;

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
	private HttpSession sessionMock;

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
	
	private int readerId;

	private int bookId;
	
	private int reviewId;

	@Test
	public void submitTest() {

		when(commentDataMock.getComment()).thenReturn(commentMock);

		when(sessionMock.getAttribute("readerId")).thenReturn(readerId);

		when(readerServiceMock.getReaderById(readerId)).thenReturn(readerMock);

		when(commentDataMock.getBookId()).thenReturn(bookId);

		when(bookServiceMock.getBook(bookId)).thenReturn(bookMock);

		when(ratingServiceMock.getReaderRatingObject(readerId,bookId)).thenReturn(ratingMock);

		when(reviewServiceMock.getReviewById(reviewId)).thenReturn(reviewMock);
		
		submitComment.submit(commentDataMock);
		
		verify(commentDataMock).getComment();
		verify(sessionMock).getAttribute("readerId");
		verify(readerServiceMock).getReaderById(readerId);
		verify(commentDataMock).getBookId();
		verify(bookServiceMock).getBook(bookId);
		verify(ratingServiceMock).getReaderRatingObject(readerId,bookId);
		verify(reviewServiceMock).getReviewById(reviewId);
		
		verify(commentMock).setRating(ratingMock);
		verify(commentMock).setReader(readerMock);
		verify(commentMock).setReview(reviewMock);
		verify(commentMock).setBook(bookMock);

		verify(commentServiceMock).submitComment(commentMock);
		
	}

}
