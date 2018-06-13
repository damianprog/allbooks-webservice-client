package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Pending;

@Service
public class PendingWebserviceImpl implements PendingWebservice {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;	
	
	@Override
	public Pending[] getReaderPendings(int id) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", id);

		ResponseEntity<Pending[]> response = restTemplate
				.getForEntity(serviceUrlName + "/readers/{readerId}/friends/pendings", Pending[].class, params);

		return response.getBody();
	}

	@Override
	public void savePending(Pending pending) {
		restTemplate.postForObject(serviceUrlName + "/friends/pendings", pending, Pending.class);
	}

	@Override
	public Pending getReadersPending(int reader1Id, int reader2Id) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("reader1", reader1Id);
		params.put("reader2", reader2Id);

		return restTemplate.getForObject(serviceUrlName + "/readers/{reader1}/friends/{reader2}/pendings", Pending.class,
				params);
	}

	@Override
	public void deletePending(int pendingIdInt) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("pendingId", pendingIdInt);

		restTemplate.delete(serviceUrlName + "/pendings/{pendingId}", params);
	}

	@Override
	public Pending getPendingByRecipentIdAndSenderId(int recipentId, int senderId) {
		
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("recipentId", recipentId);
		params.put("senderId", senderId);
		
		return restTemplate.getForObject(serviceUrlName + "/pendings/readers/{recipentId}/{senderId}", Pending.class,
				params);
	}

}
