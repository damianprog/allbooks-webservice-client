package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.utils.entity.TokenData;

@Service
public class TokenWebserviceImpl implements TokenWebservice {

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
	public Token saveToken(Token token) {
		return restTemplate.postForObject(serviceUrlName + "/tokens" + accessTokenParameter,
				token, Token.class);

	}

	@Override
	public Token getTokenByReaderId(int readerId,TokenType tokenType) {
		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));
		params.put("tokenType", tokenType.toString());

		return restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/tokens/types/{tokenType}" + accessTokenParameter,
				Token.class, params);
	}

	@Override
	public Token getTokenByCredentials(TokenData tokenData) {
		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(tokenData.getReaderId()));
		params.put("token", tokenData.getToken());
		params.put("tokenType", tokenData.getTokenType().toString());

		return restTemplate.getForObject(
				serviceUrlName + "/readers/{readerId}/tokens/{token}/types/{tokenType}" + accessTokenParameter,
				Token.class, params);
	}
	
	@Override
	public void deleteTokenByReaderId(int readerId,TokenType tokenType) {
		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));
		params.put("tokenType", tokenType.toString());

		restTemplate.delete(serviceUrlName + "/readers/{readerId}/tokens/types/{tokenType}" + accessTokenParameter, params);

	}

	@Override
	public void deleteTokenById(int tokenId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("tokenId", tokenId);

		restTemplate.delete(serviceUrlName + "/tokens/{tokenId}" + accessTokenParameter, params);

	}

	@Override
	public void updateToken(Token token) {

		restTemplate.put(serviceUrlName + "/tokens" + accessTokenParameter, token);
	}

}
