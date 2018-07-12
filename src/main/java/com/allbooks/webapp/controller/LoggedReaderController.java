package com.allbooks.webapp.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.allbooks.webapp.entity.Notification;
import com.allbooks.webapp.service.NotificationService;

@Controller
@RequestMapping("/loggedReader")
public class LoggedReaderController {

	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/showNotificationsPage")
	public String showNotificationsPage(Model theModel,Principal principal,HttpSession session) {
		
		int readerId = (int) session.getAttribute("readerId");
		
		List<Notification> notifications = notificationService.getNotificationsByReaderId(readerId);
		
		theModel.addAttribute("notifications",notifications);
		
		return "notifications";
		
	}
	
	@GetMapping("/deleteNotification")
	public String deleteNotification(Model theModel,@RequestParam("notificationId") int notificationId,HttpSession session) {
		
		int readerId = (int) session.getAttribute("readerId");
		
		notificationService.deleteNotificationByIdAndReaderId(notificationId, readerId);
		
		return "redirect:/loggedReader/showNotificationsPage";
	}
	
}
