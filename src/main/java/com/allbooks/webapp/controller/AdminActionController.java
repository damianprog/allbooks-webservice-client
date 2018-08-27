package com.allbooks.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/adminAction")
public class AdminActionController {

	@GetMapping("/action")
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
	
}
