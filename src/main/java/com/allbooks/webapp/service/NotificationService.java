package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.Notification;

public interface NotificationService {

	Notification getNotificationById(int notificationId);
	
	List<Notification> getNotificationsByReaderId(int readerId);
	
	void deleteNotificationById(int notificationId);
	
	void saveNotification(Notification notification);
	
	void deleteNotificationByIdAndReaderId(int notificationId,int readerId);
	
}
