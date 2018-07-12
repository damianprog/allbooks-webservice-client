package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Notification;

public interface NotificationWebservice {

	Notification getNotificationById(int notificationId);

	Notification[] getNotificationsByReaderId(int readerId);

	void deleteNotificationById(int notificationId);

	void saveNotification(Notification notification);

	void deleteNotificationByIdAndReaderId(int notificationId, int readerId);

}
