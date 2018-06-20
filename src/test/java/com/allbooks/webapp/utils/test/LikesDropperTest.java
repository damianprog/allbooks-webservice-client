package com.allbooks.webapp.utils.test;

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

import com.allbooks.webapp.entity.Like;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.factories.LikeFactory;
import com.allbooks.webapp.service.LikeService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.LikesDropper;
import com.allbooks.webapp.utils.SecurityContextService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LikesDropperTest {

	@InjectMocks
	private LikesDropper likesDropper;
	
	@Mock
	private SecurityContextService contextServiceMock;

	@Mock
	private LikeService likeServiceMock;

	@Mock
	private ReviewService reviewServiceMock;

	@Mock
	private ReaderService readerServiceMock;

	@Mock
	private LikeFactory likeFactoryMock;
	
	@Mock
	private Reader readerMock;
	
	@Mock
	private Like likeMock;
	
	@Mock
	private Review reviewMock;
	
	@Mock
	private List<Like> likesListMock;
	
	private int readerId = 2;

	private int reviewId = 1;
	
	@Test
	public void dropLikeTest() {
		
		when(contextServiceMock.getLoggedReaderId()).thenReturn(readerId);
		when(readerServiceMock.getReaderById(readerId)).thenReturn(readerMock);
		when(likeServiceMock.getLikeByReviewIdAndReaderId(reviewId,readerId)).thenReturn(null);
		when(reviewServiceMock.getReviewById(reviewId)).thenReturn(reviewMock);
		when(reviewMock.getLikes()).thenReturn(likesListMock);
		when(likeFactoryMock.createInstance(readerMock)).thenReturn(likeMock);

		likesDropper.dropLike(reviewId);
		
		verify(contextServiceMock).getLoggedReaderId();
		verify(readerServiceMock).getReaderById(readerId);
		verify(likeServiceMock).getLikeByReviewIdAndReaderId(reviewId,readerId);
		verify(reviewServiceMock).getReviewById(reviewId);
		verify(reviewMock).getLikes();
		verify(likeFactoryMock).createInstance(readerMock);
		
		verify(likesListMock).add(likeMock);
		verify(reviewServiceMock).updateReview(reviewMock);

	}
	
	@Test
	public void dropLikeNullTest() {
		
		int likeMockId = 3;
		
		when(contextServiceMock.getLoggedReaderId()).thenReturn(readerId);
		when(readerServiceMock.getReaderById(readerId)).thenReturn(readerMock);
		when(likeServiceMock.getLikeByReviewIdAndReaderId(reviewId,readerId)).thenReturn(likeMock);
		when(likeMock.getId()).thenReturn(likeMockId);

		likesDropper.dropLike(reviewId);
		
		verify(contextServiceMock).getLoggedReaderId();
		verify(readerServiceMock).getReaderById(readerId);
		verify(likeServiceMock).getLikeByReviewIdAndReaderId(reviewId,readerId);
		
		verify(likeServiceMock).deleteLikeById(likeMockId);
		
	}
	
}
