package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.VerificationToken;

@Service
public class TokenWebserviceImpl implements TokenWebservice{

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;
	
	@Override
	public PasswordToken savePasswordToken(PasswordToken passwordToken) {
		return restTemplate.postForObject(serviceUrlName + "/readers/passwordTokens", passwordToken, PasswordToken.class);
		
	}

	@Override
	public PasswordToken getPasswordTokenByReaderId(int readerId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);

		return restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/passwordTokens",
				PasswordToken.class, params);
	}

	@Override
	public PasswordToken getPasswordTokenByCredentials(int readerId, String token) {
		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));
		params.put("passwordToken", token);

		return restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/passwordTokens/{passwordToken}",
				PasswordToken.class, params);
	}

	@Override
	public void deletePasswordTokenByReaderId(int readerId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);

		restTemplate.delete(serviceUrlName + "/readers/{readerId}/passwordTokens", params);
		
	}

	@Override
	public VerificationToken saveVerificationToken(VerificationToken verificationToken) {
		return restTemplate.postForObject(serviceUrlName + "/readers/verificationTokens", verificationToken, VerificationToken.class);
		
	}

	@Override
	public VerificationToken getVerificationTokenByReaderId(int readerId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);

		return restTemplate.getForObject(
				serviceUrlName + "/readers/{readerId}/verificationTokens", VerificationToken.class, params);
	}

	@Override
	public void deleteVerificationTokenTokenById(int tokenId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("tokenId", tokenId);

		restTemplate.delete(serviceUrlName + "/verificationTokens/{tokenId}", params);
		
	}

	@Override
	public void updateVerificationToken(VerificationToken verificationToken) {

		restTemplate.put(serviceUrlName + "/verificationTokens", verificationToken);
	}

	
	
}
