package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

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
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.enumeration.Information;
import com.allbooks.webapp.factories.BookActionDataObjectFactory;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.bookactions.LikesDropper;
import com.allbooks.webapp.utils.model.LoggedReviewPageModelCreator;
import com.allbooks.webapp.utils.photos.ReaderPostsWithPreparedReadersPhotoGetter;
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
	private ReviewService reviewService;

	@Autowired
	private ReaderPostsWithPreparedReadersPhotoGetter reviewReaderPhotoPreparer;

	@PutMapping("/rate")
	public String rate(@ModelAttribute("rating") Rating rating, BindingResult resultRating,
			@RequestParam("bookId") int bookId, Model theModel, HttpSession session, Principal principal,
			RedirectAttributes ra) {

		saveService.saveRating(bookActionDataObjectFactory.createRatingData(rating, bookId));

		ra.addAttribute("bookId", bookId);

		return "redirect:/visitor/showBook";
	}

	@PostMapping("/dropLike")
	public String dropLike(@RequestParam("reviewId") int reviewId, @RequestParam("bookId") int bookId,
			@RequestParam("pageName") String pageName, HttpSession session, HttpServletRequest request,
			RedirectAttributes ra,Model theModel) {

		likesDropper.dropLike(reviewId);
		
		ra.addAttribute("bookId", bookId);

		switch(pageName) {
		case "book":
			ra.addAttribute("bookId",bookId);
			return "redirect:/visitor/showBook";
		case "review":
			ra.addAttribute("reviewId",reviewId);
			return "redirect:/bookActions/showReview";
		default:
			theModel.addAttribute(Information.NOT_FOUND);
			return "information";	
		}
		
		
	}

	@GetMapping("/showPostReview")
	public String postReviewPage(@RequestParam("bookId") int bookId, Model theModel, Principal principal)
			throws IOException {

		Book book = bookService.getBookById(bookId);

		byte[] bookPicBytes = photoService.resize(book.getBookPhoto(), 80, 120);

		theModel.addAttribute("book", book);
		theModel.addAttribute("review", new Review());
		theModel.addAttribute("bookPic", photoService.getEncodedImage(bookPicBytes));
		theModel.addAllAttributes(readerBookAndRatingModelCreator.createModel(bookId));

		return "postreview";
	}

	@GetMapping("/showReview")
	public String reviewPage(@RequestParam("reviewId") int reviewId, Model theModel, HttpSession session,
			Principal principal) {

		Review review = reviewService.getReviewById(reviewId);

		Book book = review.getBook();

		List<ReaderPost> reviewComments = reviewReaderPhotoPreparer.getPreparedReviewComments(reviewId);

		theModel.addAllAttributes(loggedReviewPageModelCreator.createModel(review));
		theModel.addAttribute("book", review.getBook());
		theModel.addAttribute("review", review);
		theModel.addAttribute("bookPic", photoService.getEncodedImage(book.getBookPhoto()));
		theModel.addAttribute("rating", review.getRating());
		theModel.addAttribute("comment", new Comment());
		theModel.addAttribute("reviewComments", reviewComments);

		return "review";
	}

}
