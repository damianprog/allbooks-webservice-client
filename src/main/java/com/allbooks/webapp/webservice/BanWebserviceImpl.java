package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Ban;
import com.allbooks.webapp.utils.entity.BanHelperPage;
import com.allbooks.webapp.utils.entity.HelperPage;

@Service
public class BanWebserviceImpl implements BanWebservice {

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
	public void saveBan(Ban ban) {

		restTemplate.postForObject(serviceUrlName + "/bans" + accessTokenParameter, ban, Ban.class);
	}

	@Override
	public Ban getBanById(int banId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("banId", banId);

		return restTemplate.getForObject(serviceUrlName + "/bans/{banId}" + accessTokenParameter, Ban.class, params);
	}

	@Override
	public Ban getBanByReaderId(int readerId) {
		
		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);

		return restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/bans" + accessTokenParameter, Ban.class, params);
		
	}

	@Override
	public void deleteBanById(int banId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("banId", banId);
		
		restTemplate.delete(serviceUrlName + "/bans/{banId}" + accessTokenParameter,params);
	}

	@Override
	public void deleteBanByReaderId(int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		
		restTemplate.delete(serviceUrlName + "/reader/{readerId}/bans" + accessTokenParameter,params);
	}

	@Override
	public Page<Ban> getBans(int page) {
		Map<String, Integer> params = new HashMap<>();
		params.put("page", page);
		
		BanHelperPage responseEntity = restTemplate.getForObject(
				serviceUrlName + "/bans/pages/{page}" + accessTokenParameter, BanHelperPage.class,
				params);
		
		return responseEntity;
	}

}
