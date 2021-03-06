package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;

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
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.factories.DataObjectFactory;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.utils.bookactions.LikesDropper;
import com.allbooks.webapp.utils.model.ReaderBookAndRatingModelCreator;
import com.allbooks.webapp.utils.model.ReviewPageModelCreator;
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
	private DataObjectFactory bookActionDataObjectFactory;

	@Autowired
	private ReaderBookAndRatingModelCreator readerBookAndRatingModelCreator;

	@Autowired
	private LikesDropper likesDropper;

	@Autowired
	private ReviewPageModelCreator reviewPageModelCreator;

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
			RedirectAttributes ra, Model theModel) {

		likesDropper.dropLike(reviewId);

		ra.addAttribute("bookId", bookId);

		if(pageName.equals("book")) {
			ra.addAttribute("bookId", bookId);
			return "redirect:/visitor/showBook";
		}
		else {
			ra.addAttribute("reviewId", reviewId);
			return "redirect:/bookActions/showReview";
		}
	}

	@GetMapping("/showPostReview")
	public String postReviewPage(@RequestParam("bookId") int bookId, Model theModel, Principal principal)
			throws IOException {

		Book book = bookService.getBookById(bookId);

		theModel.addAttribute("book", book);
		theModel.addAttribute("review", new Review());
		theModel.addAllAttributes(readerBookAndRatingModelCreator.createModel(bookId));

		return "book/postreview";
	}

	@GetMapping("/showReview")
	public String showReview(@RequestParam("reviewId") int reviewId, Model theModel, HttpSession session,
			Principal principal) {

		theModel.addAllAttributes(reviewPageModelCreator.create(reviewId));

		return "book/review";
	}

}
