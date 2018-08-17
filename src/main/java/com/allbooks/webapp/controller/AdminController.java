package com.allbooks.webapp.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allbooks.webapp.entity.Ban;
import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Notification;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.entity.ReadingChallangeComment;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.BanService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.NotificationService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReadingChallangeCommentService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.admin.BanningExecutor;
import com.allbooks.webapp.utils.admin.PostsRemover;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private BookService bookService;

	@Autowired
	private PhotoServiceImpl photoService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private BanningExecutor banningExecutor;

	@Autowired
	private BanService banService;

	@Autowired
	private PostsRemover postsRemover;

	@Autowired
	private ReadingChallangeCommentService challangeCommentService;

	@GetMapping("/showAddBook")
	public String addBook(Model theModel) {

		theModel.addAttribute("book", new Book());

		return "addbook";
	}

	@PostMapping("/addBook")
	public String addBook(@RequestParam("bookPhotoTemp") MultipartFile mfBookPhoto,
			@RequestParam("authorPhotoTemp") MultipartFile mfAuthorPhoto, @ModelAttribute("book") Book book,
			Model theModel) throws IOException {

		byte[] bookPhotoBytes = photoService.convertMultipartImageToBytes(mfBookPhoto);

		byte[] authorPhotoBytes = photoService.convertMultipartImageToBytes(mfAuthorPhoto);
		
		book.setBookPhoto(photoService.resize(bookPhotoBytes, 150, 228));
		book.setAuthorPhoto(photoService.resize(authorPhotoBytes,50,66));

		bookService.saveBook(book);

		theModel.addAttribute("book", book);

		return "addbook";
	}

	@GetMapping("/adminAction")
	public String adminAction(RedirectAttributes ra,
			@RequestParam(value = "reviewId", required = false) Integer reviewId,
			@RequestParam(value = "commentId", required = false) Integer commentId,
			@RequestParam(value = "readerId", required = false) Integer readerId,
			@RequestParam(value = "commentType", required = false) String commentType,
			@RequestParam("adminAction") String adminAction) {

		switch (adminAction) {

		case "deleteReview":
			ra.addAttribute("reviewId", reviewId);
			return "redirect:/admin/showDeleteReview";
		case "deleteComment":
			ra.addAttribute("commentId", commentId);
			ra.addAttribute("commentType", commentType);
			return "redirect:/admin/showDeleteComment";
		case "sendNotice":
			ra.addAttribute("readerId", readerId);
			return "redirect:/admin/showSendNotice";

		}

		return "redirect:/reader/main";
	}

	@GetMapping("/showSendNotice")
	public String showSendNotice(Model theModel, @RequestParam("readerId") int readerId) throws IOException {

		Reader reader = readerService.getReaderById(readerId);

		theModel.addAttribute("reader", reader);

		return "sendNotice";
	}

	@GetMapping("/showDeleteReview")
	public String showDeleteReview(Model theModel, @RequestParam("reviewId") int reviewId) throws IOException {

		Review review = reviewService.getReviewById(reviewId);

		theModel.addAttribute("bookPic", photoService.getEncodedImage(review.getBook().getBookPhoto()));

		theModel.addAttribute("review", review);

		return "deleteReview";
	}

	@GetMapping("/deleteReview")
	public String deleteReview(@RequestParam("reviewId") int reviewId, @RequestParam("reasonText") String reasonText,
			RedirectAttributes ra) {

		Review review = reviewService.getReviewById(reviewId);

		postsRemover.deleteReview(reviewId, reasonText);

		ra.addAttribute("bookId", review.getBook().getId());

		return "redirect:/reader/showBook";
	}

	@GetMapping("/showDeleteComment")
	public String showDeleteComment(@RequestParam("commentId") int commentId,
			@RequestParam("commentType") String commentType, Model theModel) {

		ReaderPost comment;

		if (commentType.equals("REVIEW_COMMENT"))
			comment = commentService.getCommentById(commentId);
		else
			comment = challangeCommentService.getCommentById(commentId);

		byte[] readerPhoto = comment.getPostingReader().getProfilePhoto();

		String encodedReaderPhoto = photoService.getEncodedImage(photoService.resize(readerPhoto, 80, 80));

		comment.getPostingReader().setEncodedProfilePhoto(encodedReaderPhoto);

		theModel.addAttribute("commentType", commentType);
		theModel.addAttribute("comment", comment);

		return "deleteComment";
	}

	@GetMapping("/deleteComment")
	public String deleteComment(@RequestParam("commentId") int commentId, @RequestParam("reasonText") String reasonText,
			@RequestParam("commentType") String commentType, Model theModel, RedirectAttributes ra) {

		ra.addAttribute("commentId",commentId);
		ra.addAttribute("reasonText",reasonText);
		
		switch (commentType) {
		case "REVIEW_COMMENT":
			return "redirect:/admin/deleteReviewComment";
			
		case "CHALLANGE_COMMENT":
			return "redirect:/admin/deleteReadingChallangeComment";
		}

		return "redirect:/reader/main";

	}

	@GetMapping("/deleteReviewComment")
	public String deleteReviewComment(@RequestParam("commentId") int commentId,
			@RequestParam("reasonText") String reasonText, RedirectAttributes ra) {
		Comment comment = commentService.getCommentById(commentId);

		postsRemover.deleteComment(commentId, reasonText);

		ra.addAttribute("reviewId", comment.getReview().getId());

		return "redirect:/bookActions/reviewPage";
	}

	@GetMapping("/deleteReadingChallangeComment")
	public String deleteReadingChallangeComment(@RequestParam("commentId") int commentId,
			@RequestParam("reasonText") String reasonText, Model theModel, RedirectAttributes ra) {
		ReadingChallangeComment challangeComment = challangeCommentService.getCommentById(commentId);

		postsRemover.deleteReadingChallangeComment(commentId, reasonText);

		ra.addAttribute("readerId", challangeComment.getChallangeReader().getId());

		return "redirect:/loggedReader/showReadingChallange";
	}

	@GetMapping("/sendNotice")
	public String sendNotice(@RequestParam("readerId") int readerId, @RequestParam("noticeText") String noticeText,
			RedirectAttributes ra) {

		Reader reader = readerService.getReaderById(readerId);

		Notification notification = new Notification(reader, noticeText);

		notificationService.saveNotification(notification);

		return "redirect:/reader/main";
	}

	@GetMapping("/showBan")
	public String showBan(Model theModel) {

		theModel.addAttribute("ban", new Ban());

		return "ban";
	}

	@PostMapping("/ban")
	public String ban(@ModelAttribute("ban") Ban ban, @RequestParam("readerUsername") String readerUsername,
			Model theModel) throws MessagingException {

		Reader reader = readerService.getReaderByUsername(readerUsername);

		if (reader == null) {
			theModel.addAttribute("wrongUsername", true);
			theModel.addAttribute("ban", new Ban());
			return "ban";
		}

		banningExecutor.ban(reader, ban);

		return "redirect:/reader/main";
	}

	@GetMapping("/showBanningList")
	public String showBanningList(Model theModel, @RequestParam(defaultValue = "1") int page) {

		Page<Ban> bansPage = banService.getBans(page);

		theModel.addAttribute("bans", bansPage.getContent());
		theModel.addAttribute("bansPage", bansPage);
		theModel.addAttribute("currentPage", page);

		return "banningList";
	}

}
