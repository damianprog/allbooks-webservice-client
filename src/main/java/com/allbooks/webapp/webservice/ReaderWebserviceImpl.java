package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderRole;

@Service
public class ReaderWebserviceImpl implements ReaderWebservice {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OAuth2RestOperations oAuth2RestOperations;

	@Value("${service.url.name}")
	private String serviceUrlName;

	private String accessTokenParameter;

	@PostConstruct
	private void initializeAccessTokenField() {
		accessTokenParameter = "?access_token=" + oAuth2RestOperations.getAccessToken().getValue();
	}

	@Override
	public Reader saveReader(Reader theReader) {

		return restTemplate.postForObject(serviceUrlName + "/readers" + accessTokenParameter, theReader, Reader.class);
	}

	@Override
	public Reader getReaderByUsernameAndPassword(String login, String password) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", login);

		return restTemplate.getForObject(serviceUrlName + "/readers/logins/{readerLogin}" + accessTokenParameter,
				Reader.class, params);

	}

	@Override
	public Reader getReaderByUsername(String login) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", login);

		return restTemplate.getForObject(serviceUrlName + "/readers/logins/{readerLogin}" + accessTokenParameter,
				Reader.class, params);

	}

	@Override
	public void updateReader(Reader reader) {

		restTemplate.put(serviceUrlName + "/readers" + accessTokenParameter, reader);

	}

	@Override
	public Reader getReaderById(int readerId) {
		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));

		return restTemplate.getForObject(serviceUrlName + "/readers/{readerId}" + accessTokenParameter, Reader.class,
				params);
	}

	@Override
	public void deleteReader(int readerId) {
		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));

		restTemplate.delete(serviceUrlName + "/readers/{readerId}" + accessTokenParameter, params);

	}

	@Override
	public Reader getReaderByEmail(String email) {
		Map<String, String> params = new HashMap<>();
		params.put("email", email);

		return restTemplate.getForObject(serviceUrlName + "/readers/emails/{email}" + accessTokenParameter,
				Reader.class, params);
	}

	@Override
	public void saveReaderRole(ReaderRole readerRole) {

		restTemplate.postForObject(serviceUrlName + "/readerrole" + accessTokenParameter, readerRole, ReaderRole.class);

	}

}
