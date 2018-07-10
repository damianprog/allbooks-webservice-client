package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Role;

@Service
public class RoleWebserviceImpl implements RoleWebservice{

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
	public Role getRoleById(int roleId) {
		
		Map<String, Integer> params = new HashMap<>();
		params.put("roleId", roleId);
		
		return restTemplate.getForObject(serviceUrlName + "/roles/{roleId}" + accessTokenParameter, Role.class,params);
		
	}

	
	
}
