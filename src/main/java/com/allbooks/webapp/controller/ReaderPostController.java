package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.CommentData;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReadingChallangeComment;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.factories.BookActionDataObjectFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReadingChallangeCommentService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.bookactions.PostReviewHelper;
import com.allbooks.webapp.utils.bookactions.SubmitComment;
import com.allbooks.webapp.utils.service.SaveService;

@Controller
@RequestMapping("/readerPost")
public class ReaderPostController {

	@Autowired
	private SaveService saveService;

	@Autowired
	private SubmitComment submitComment;

	@Autowired
	private BookActionDataObjectFactory bookActionDataObjectFactory;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private PostReviewHelper postReviewHelper;

	@Autowired
	private ReadingChallangeCommentService challangeCommentService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private SecurityContextService contextService;

	@PostMapping("/postReview")
	public String postReview(@RequestParam("isItUpdateReaderBook") boolean isItUpdateReaderBook,
			@RequestParam("ratingId") int ratingId, @RequestParam("rate") int rate,
			@RequestParam("readerBookId") int readerBookId, @RequestParam("shelves") String shelves,
			@RequestParam("bookId") int bookId, @ModelAttribute("review") Review review, RedirectAttributes ra)
			throws IOException {

		Rating rating = postReviewHelper.getRating(ratingId, rate);

		saveService.saveRating(bookActionDataObjectFactory.createRatingData(rating, bookId));

		saveService.saveReview(bookActionDataObjectFactory.createReviewData(review, bookId));

		ra.addAttribute("bookId", bookId);

		return "redirect:/reader/showBook";
	}

	@PostMapping("/postComment")
	public String submitComment(@ModelAttribute("comment") Comment comment, @RequestParam Map<String, String> params,
			@RequestParam("reviewId") int reviewId, @RequestParam("bookId") int bookId, HttpSession session,
			Model theModel, Principal principal, RedirectAttributes ra) {

		CommentData commentData = bookActionDataObjectFactory.createCommentData(comment, bookId, reviewId);

		submitComment.submit(commentData);

		ra.addAttribute("reviewId", reviewId);

		return "redirect:/bookActions/reviewPage";
	}

	@PostMapping("/editReview")
	public String editReview(@RequestParam("reviewId") int reviewId, @RequestParam("reviewText") String reviewText,
			Model theModel, HttpSession session, Principal principal, RedirectAttributes ra) {

		Review review = reviewService.getReviewById(reviewId);

		review.setText(reviewText);

		reviewService.updateReview(review);

		ra.addAttribute("reviewId", reviewId);

		return "redirect:/bookActions/reviewPage";

	}

	@GetMapping("/deleteReview")
	public String deleteReview(@RequestParam("reviewId") int reviewId, RedirectAttributes ra, HttpSession session) {

		int readerId = (int) session.getAttribute("readerId");

		Review review = reviewService.getReviewById(reviewId);

		reviewService.deleteReviewByIdAndReaderId(reviewId, readerId);

		ra.addAttribute("bookId", review.getBook().getId());

		return "redirect:/reader/showBook";

	}

	@GetMapping("/deleteComment")
	public String deleteComment(@RequestParam("commentId") int commentId, RedirectAttributes ra, HttpSession session) {

		int readerId = (int) session.getAttribute("readerId");

		Comment comment = commentService.getCommentById(commentId);

		commentService.deleteCommentByIdAndReaderId(commentId, readerId);

		ra.addAttribute("reviewId", comment.getReview().getId());

		return "redirect:/bookActions/reviewPage";
	}

	@PostMapping("/editComment")
	public String editComment(@RequestParam("reviewId") int reviewId, @RequestParam("commentId") int commentId,
			@RequestParam("commentText") String commentText, Model theModel, HttpSession session, Principal principal,
			RedirectAttributes ra) {

		Comment comment = commentService.getCommentById(commentId);

		comment.setText(commentText);

		commentService.submitComment(comment);

		ra.addAttribute("reviewId", reviewId);

		return "redirect:/bookActions/reviewPage";

	}

	@PostMapping("editChallangeComment")
	public String editChallangeComment(RedirectAttributes ra, @RequestParam("commentText") String commentText,
			@RequestParam("commentId") int commentId) {

		ReadingChallangeComment challangeComment = challangeCommentService.getCommentById(commentId);

		challangeComment.setText(commentText);

		challangeCommentService.save(challangeComment);

		ra.addAttribute("readerId", challangeComment.getChallangeReader().getId());

		return "redirect:/loggedReader/showReadingChallange";
	}

	@PostMapping("/postReadingChallangeComment")
	public String postReadingChallangeComment(Model theModel, @RequestParam("text") String text,
			@RequestParam("challangeReaderId") int challangeReaderId, RedirectAttributes ra) {

		int readerId = contextService.getLoggedReaderId();

		Reader loggedReader = readerService.getReaderById(readerId);

		Reader challangeReader = readerService.getReaderById(challangeReaderId);

		ReadingChallangeComment challangeComment = new ReadingChallangeComment(text, loggedReader, challangeReader);

		challangeCommentService.save(challangeComment);

		ra.addAttribute("readerId", challangeReaderId);

		return "redirect:/loggedReader/showReadingChallange";
	}

	@GetMapping("/deleteReadingChallangeComment")
	public String deleteReadingChallangeComment(@RequestParam("commentId") int commentId,@RequestParam("challangeReaderId") int challangeReaderId,RedirectAttributes ra) {
		
		challangeCommentService.deleteCommentById(commentId);
		
		ra.addAttribute("readerId",challangeReaderId);
		
		return "redirect:/loggedReader/showReadingChallange";
		
	}

}
