package com.allbooks.webapp.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.allbooks.webapp.entity.Notification;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.entity.ReadingChallange;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.NotificationService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReadingChallangeService;
import com.allbooks.webapp.utils.bookactions.ReaderPostsWithPreparedReadersPhotoGetter;
import com.allbooks.webapp.utils.readerbook.CurrentYearReadBooksGetter;
import com.allbooks.webapp.utils.service.PhotoService;

@Controller
@RequestMapping("/loggedReader")
public class LoggedReaderController {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ReadingChallangeService readingChallangeService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private CurrentYearReadBooksGetter currentYearReadBooksGetter;

	@Autowired
	private ReaderPostsWithPreparedReadersPhotoGetter readerPostsGetter;
	
	@GetMapping("/showNotifications")
	public String showNotificationsPage(Model theModel, Principal principal, HttpSession session,
			@RequestParam(defaultValue = "1") int page) {

		int readerId = contextService.getLoggedReaderId();

		Page<Notification> notifications = notificationService.getNotificationsByReaderId(readerId, page);

		theModel.addAttribute("notifications", notifications.getContent());
		theModel.addAttribute("notificationsPage", notifications);
		theModel.addAttribute("currentPage", page);

		return "notifications";

	}

	@GetMapping("/deleteNotification")
	public String deleteNotification(Model theModel, @RequestParam("notificationId") int notificationId,
			HttpSession session) {

		int readerId = contextService.getLoggedReaderId();

		notificationService.deleteNotificationByIdAndReaderId(notificationId, readerId);

		return "redirect:/loggedReader/showNotifications";
	}

	@GetMapping("/readingChallange")
	public String readingChallange(Model theModel, @RequestParam("numberOfBooks") int numberOfBooks,
			HttpSession session) {

		int readerId = contextService.getLoggedReaderId();

		Reader reader = readerService.getReaderById(readerId);

		ReadingChallange readingChallange = new ReadingChallange(reader, numberOfBooks);

		readingChallangeService.saveReadingChallange(readingChallange);

		return "redirect:/reader/main";
	}

	@GetMapping("/showReadingChallange")
	public String showReadingChallange(Model theModel, @RequestParam("readerId") int readerId) {

		Reader reader = readerService.getReaderById(readerId);

		ReadingChallange readingChallange = readingChallangeService.getReadingChallangeByReaderId(readerId);

		if (reader.getProfilePhoto() != null) {
			String resizedProfilePhoto = photoService
					.getEncodedImage(photoService.resize(reader.getProfilePhoto(), 80, 80));
			reader.setEncodedProfilePhoto(resizedProfilePhoto);
		}

		List<ReaderBook> currentYearBooks = currentYearReadBooksGetter.getBooks(readerId);

		List<ReaderPost> readingChallangeComments = readerPostsGetter.getPreparedReadingChallangeComments(readerId);

		photoService.encodeAndResizeBookPhotoInBookChildren(currentYearBooks, 100, 150);

		theModel.addAttribute("reader", reader);
		theModel.addAttribute("readBooks", currentYearBooks);
		theModel.addAttribute("readingChallange", readingChallange);
		theModel.addAttribute("readingChallangeComments",readingChallangeComments);
		
		return "readingChallange";
	}

	

}
