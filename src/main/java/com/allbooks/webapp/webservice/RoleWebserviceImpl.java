package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Role;

@Service
public class RoleWebserviceImpl implements RoleWebservice{

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;
	
	@Override
	public Role getRoleById(int roleId) {
		
		Map<String, Integer> params = new HashMap<>();
		params.put("roleId", roleId);
		
		return restTemplate.getForObject(serviceUrlName + "/roles/{roleId}", Role.class,params);
		
	}

	
	
}
