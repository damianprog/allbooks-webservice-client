package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Notification;

@Service
public class NotificationWebserviceImpl implements NotificationWebservice {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;

	@Autowired
	private OAuth2RestOperations oAuth2RestOperations;

	private String accessTokenParameter;

	@PostConstruct
	private void initializeAccessTokenField() {
		accessTokenParameter = "?access_token=" + oAuth2RestOperations.getAccessToken().getValue();
	}

	@Override
	public Notification getNotificationById(int notificationId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("notificationId", notificationId);

		return restTemplate.getForObject(serviceUrlName + "/notifications/{notificationId}" + accessTokenParameter,
				Notification.class, params);

	}

	@Override
	public Notification[] getNotificationsByReaderId(int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);

		return restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/notifications" + accessTokenParameter,
				Notification[].class, params);
		
	}

	@Override
	public void deleteNotificationById(int notificationId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("notificationId", notificationId);

		restTemplate.delete(serviceUrlName + "/notifications/{notificationId}" + accessTokenParameter,params);
		
	}

	@Override
	public void saveNotification(Notification notification) {
		
		restTemplate.postForEntity(serviceUrlName + "/notifications" + accessTokenParameter, notification, Notification.class);
		
	}

	@Override
	public void deleteNotificationByIdAndReaderId(int notificationId, int readerId) {

		Map<String,Integer> params = new HashMap<>();
		params.put("notificationId", notificationId);
		params.put("readerId", readerId);
		
		restTemplate.delete(serviceUrlName + "/readers/{readerId}/notifications/{notificationId}" + accessTokenParameter,params);
		
	}

}
