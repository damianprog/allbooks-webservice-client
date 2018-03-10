package com.allbooks.webapp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.VerificationToken;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${service.url.name}")
	String serviceUrlName;

	@Override
	public void createPasswordToken(Reader reader, String token) {

		PasswordToken passwordToken = new PasswordToken(token, reader);

		restTemplate.postForObject(serviceUrlName + "/readers/passwordTokens", passwordToken, PasswordToken.class);

	}

	@Override
	public PasswordToken getPasswordTokenByReaderId(int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);

		PasswordToken passwordToken = restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/passwordTokens",
				PasswordToken.class, params);

		return passwordToken;
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
	public void deletePasswordToken(int readerId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);

		restTemplate.delete(serviceUrlName + "/readers/{readerId}/passwordTokens", params);

	}

	@Override
	public void createVerificationToken(Reader reader, String token) {

		VerificationToken tokenObj = new VerificationToken(token, reader);

		restTemplate.postForObject(serviceUrlName + "/readers/verificationTokens", tokenObj, VerificationToken.class);

	}

	@Override
	public VerificationToken getTokenByReaderId(int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);

		VerificationToken verificationToken = restTemplate.getForObject(
				serviceUrlName + "/readers/{readerId}/verificationTokens", VerificationToken.class, params);

		return verificationToken;
	}

	@Override
	public void deleteVerificationTokenTokenById(int tokenId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("tokenId", tokenId);

		restTemplate.delete(serviceUrlName + "/verificationTokens/{tokenId}", params);
	}

}
