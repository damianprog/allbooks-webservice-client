package com.allbooks.webapp.service;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.Notification;

public interface NotificationService {

	Notification getNotificationById(int notificationId);
	
	Page<Notification> getNotificationsByReaderId(int readerId,int page);
	
	void deleteNotificationById(int notificationId);
	
	void saveNotification(Notification notification);
	
	void deleteNotificationByIdAndReaderId(int notificationId,int readerId);
	
}
