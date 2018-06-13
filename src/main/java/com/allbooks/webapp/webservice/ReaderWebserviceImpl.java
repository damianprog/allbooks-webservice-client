package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderRole;

@Service
public class ReaderWebserviceImpl implements ReaderWebservice {

	@Autowired
	RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;

	@Override
	public Reader saveReader(Reader theReader) {

		return restTemplate.postForObject(serviceUrlName + "/readers", theReader, Reader.class);
	}

	@Override
	public Reader getReaderByUsernameAndPassword(String login, String password) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", login);

		return restTemplate.getForObject(serviceUrlName + "/readers/logins/{readerLogin}", Reader.class, params);

	}

	@Override
	public Reader getReaderByUsername(String login) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", login);

		return restTemplate.getForObject(serviceUrlName + "/readers/logins/{readerLogin}", Reader.class, params);

	}

	@Override
	public void updateReader(Reader reader) {
		restTemplate.put(serviceUrlName + "/readers", reader);

	}

	@Override
	public Reader getReaderById(int readerId) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", readerId);

		return restTemplate.getForObject(serviceUrlName + "/readers/{readerId}", Reader.class, params);
	}

	@Override
	public void deleteReader(int readerId) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", readerId);

		restTemplate.delete(serviceUrlName + "/readers/{readerId}", params);

	}

	@Override
	public Reader getReaderByEmail(String email) {
		Map<String, String> params = new HashMap<>();
		params.put("email", email);

		return restTemplate.getForObject(serviceUrlName + "/readers/emails/{email}", Reader.class, params);
	}

	@Override
	public void saveReaderRole(ReaderRole readerRole) {
		restTemplate.postForObject(serviceUrlName + "/readerrole", readerRole, ReaderRole.class);

	}

}
