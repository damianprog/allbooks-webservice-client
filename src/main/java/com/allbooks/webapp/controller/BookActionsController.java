package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.CommentData;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.factories.BookActionDataObjectFactory;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.bookactions.LikesDropper;
import com.allbooks.webapp.utils.bookactions.PostReviewHelper;
import com.allbooks.webapp.utils.bookactions.SubmitComment;
import com.allbooks.webapp.utils.model.LoggedReviewPageModelCreator;
import com.allbooks.webapp.utils.readerbook.ReaderBookAndRatingModelCreator;
import com.allbooks.webapp.utils.service.PhotoService;
import com.allbooks.webapp.utils.service.SaveService;

@Controller
@RequestMapping("/bookActions")
public class BookActionsController {

	@Autowired
	private BookService bookService;

	@Autowired
	private SaveService saveService;

	@Autowired
	private SubmitComment submitComment;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private LoggedReviewPageModelCreator loggedReviewPageModelCreator;

	@Autowired
	private BookActionDataObjectFactory bookActionDataObjectFactory;

	@Autowired
	private ReaderBookAndRatingModelCreator readerBookAndRatingModelCreator;

	@Autowired
	private LikesDropper likesDropper;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private PostReviewHelper postReviewHelper;

	@PutMapping("/rate")
	public String rate(@ModelAttribute("rating") Rating rating, BindingResult resultRating,
			@RequestParam("bookId") int bookId, Model theModel, HttpSession session, Principal principal,
			RedirectAttributes ra) {

		saveService.saveRating(bookActionDataObjectFactory.createRatingData(rating, bookId));

		ra.addAttribute("bookId", bookId);

		return "redirect:/reader/showBook";
	}

	@PostMapping("/dropLike")
	public String dropLike(@RequestParam("reviewId") int reviewId, @RequestParam("bookId") int bookId,
			HttpSession session, HttpServletRequest request, RedirectAttributes ra) {

		likesDropper.dropLike(reviewId);

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

	@PostMapping("/dropLikeReview")
	public String dropLikeReview(@RequestParam("reviewId") int reviewId, Model theModel, HttpSession session,
			RedirectAttributes ra) {

		likesDropper.dropLike(reviewId);

		ra.addAttribute("reviewId", reviewId);

		return "redirect:/bookActions/reviewPage";

	}

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

	@GetMapping("/postReviewPage")
	public String postReviewPage(@RequestParam("bookId") int bookId, Model theModel, Principal principal)
			throws IOException {

		Book book = bookService.getBook(bookId);

		byte[] bookPicBytes = photoService.resize(book.getBookPhoto(), 80, 120);

		theModel.addAttribute("book", book);
		theModel.addAttribute("review", new Review());
		theModel.addAttribute("bookPic", photoService.getEncodedImage(bookPicBytes));
		theModel.addAllAttributes(readerBookAndRatingModelCreator.createModel(bookId));

		return "postreview";
	}

	@GetMapping("/reviewPage")
	public String reviewPage(@RequestParam("reviewId") int reviewId, Model theModel, HttpSession session,
			Principal principal) {

		Review review = reviewService.getReviewById(reviewId);

		Book book = review.getBook();

		List<Comment> reviewComments = commentService.getReviewComments(reviewId);

		theModel.addAllAttributes(loggedReviewPageModelCreator.createModel(review));
		theModel.addAttribute("book", review.getBook());
		theModel.addAttribute("review", review);
		theModel.addAttribute("bookPic", photoService.getEncodedImage(book.getBookPhoto()));
		theModel.addAttribute("rating", review.getRating());
		theModel.addAttribute("comment", new Comment());
		theModel.addAttribute("reviewComments", reviewComments);

		return "review";
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
	public String deleteReview(@RequestParam("reviewId") int reviewId,RedirectAttributes ra,HttpSession session) {
		
		int readerId = (int) session.getAttribute("readerId");
		
		Review review = reviewService.getReviewById(reviewId);
		
		reviewService.deleteReviewByIdAndReaderId(reviewId, readerId);
		
		ra.addAttribute("bookId", review.getBook().getId());
		
		return "redirect:/reader/showBook";
		
	}
	
	@GetMapping("/deleteComment")
	public String deleteComment(@RequestParam("commentId") int commentId,RedirectAttributes ra,HttpSession session) {
		
		int readerId = (int) session.getAttribute("readerId");
		
		Comment comment = commentService.getCommentById(commentId);
		
		commentService.deleteCommentByIdAndReaderId(commentId,readerId);
		
		ra.addAttribute("reviewId",comment.getReview().getId());
		
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
	
}
