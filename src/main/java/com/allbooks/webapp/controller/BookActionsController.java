package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
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
import com.allbooks.webapp.factories.ReviewFactory;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.LikesDropper;
import com.allbooks.webapp.utils.ReaderBookAndRatingModelCreator;
import com.allbooks.webapp.utils.SubmitComment;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;
import com.allbooks.webapp.utils.service.SaveService;

@Controller
@RequestMapping("/bookActions")
public class BookActionsController {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private BookService bookService;

	@Autowired
	private SaveService saveService;

	@Autowired
	private SubmitComment submitComment;

	@Autowired
	private PhotoServiceImpl photoService;

	@Autowired
	private BookActionDataObjectFactory bookActionDataObjectFactory;

	@Autowired
	private ReaderBookAndRatingModelCreator readerBookAndRatingModelCreator;

	@Autowired
	private ReviewFactory reviewFactory;

	@Autowired
	private LikesDropper likesDropper;
	
	@PutMapping("/rate")
	public String rate(@ModelAttribute("rating") Rating rating, BindingResult resultRating,
			@RequestParam Map<String, String> params, Model theModel, HttpSession session, Principal principal,
			RedirectAttributes ra) {

		saveService.saveRating(bookActionDataObjectFactory.createRatingData(rating, params));

		ra.addAttribute("bookId", params.get("bookId"));

		return "redirect:/reader/showBook";
	}

	@PostMapping("/postComment")
	public String submitComment(@ModelAttribute("comment") Comment comment, @RequestParam Map<String, String> params,
			@RequestParam("reviewId") int reviewId, HttpSession session, Model theModel, Principal principal,
			RedirectAttributes ra) {

		CommentData commentData = bookActionDataObjectFactory.createCommentData(comment, params);

		submitComment.submit(commentData);

		ra.addAttribute("reviewId", reviewId);

		return "redirect:/reader/reviewPage";
	}

	@GetMapping("/dropLike")
	public String dropLike(@RequestParam("reviewId") int reviewId, @RequestParam("bookId") int bookId,
			HttpSession session, HttpServletRequest request, RedirectAttributes ra) {

		likesDropper.dropLike(reviewId);
		
		ra.addAttribute("bookId", bookId);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/dropLikeReview")
	public String dropLikeReview(@RequestParam("reviewId") int reviewId, Model theModel, HttpSession session,
			RedirectAttributes ra) {

		
		ra.addAttribute("reviewId", reviewId);

		return "redirect:/reader/reviewPage";

	}

	@PostMapping("/saveReaderBook")
	public String readState(@RequestParam Map<String, String> params,
			@ModelAttribute("readerBook") ReaderBook readerBook, HttpSession session, Principal principal,
			RedirectAttributes ra) throws IOException {

		saveService.saveReaderBook(bookActionDataObjectFactory.createReaderBookData(readerBook, params));

		ra.addAttribute("bookId", params.get("bookId"));

		return "redirect:/reader/showBook";
	}

	@PostMapping("/postReview")
	public String postReview(@RequestParam Map<String, String> params, @ModelAttribute("rating") Rating rating,
			@ModelAttribute("readerBook") ReaderBook readerBook, RedirectAttributes ra) throws IOException {

		readerBook.setId(Integer.valueOf(params.get("readerBookId")));

		Review review = reviewFactory.createInstanceFromParams(params);

		saveService.saveRating(bookActionDataObjectFactory.createRatingData(rating, params));

		saveService.saveReaderBook(bookActionDataObjectFactory.createReaderBookData(readerBook, params));

		saveService.saveReview(bookActionDataObjectFactory.createReviewData(review, params));

		ra.addAttribute("bookId", params.get("bookId"));

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

}
