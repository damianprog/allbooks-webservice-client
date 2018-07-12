package com.allbooks.webapp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Notification;
import com.allbooks.webapp.webservice.NotificationWebservice;

@Service
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	private NotificationWebservice notificationWebservice;
	
	@Override
	public Notification getNotificationById(int notificationId) {
		return notificationWebservice.getNotificationById(notificationId);
	}

	@Override
	public List<Notification> getNotificationsByReaderId(int readerId) {
		return Arrays.asList(notificationWebservice.getNotificationsByReaderId(readerId));
	}

	@Override
	public void deleteNotificationById(int notificationId) {
		notificationWebservice.deleteNotificationById(notificationId);
	}

	@Override
	public void saveNotification(Notification notification) {
		notificationWebservice.saveNotification(notification);
	}

	@Override
	public void deleteNotificationByIdAndReaderId(int notificationId, int readerId) {
		notificationWebservice.deleteNotificationByIdAndReaderId(notificationId,readerId);
	}

}
