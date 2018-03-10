package com.allbooks.webapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderRole;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.entity.VerificationToken;

@Service
public class ReaderServiceImpl implements ReaderService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${service.url.name}")
	private String serviceUrlName;

	@Override
	public boolean saveReader(Reader theReader) {

		ReaderRole readerRole = new ReaderRole();

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", theReader.getUsername());

		theReader.setPassword(bCryptPasswordEncoder.encode(theReader.getPassword()));

		if (!checkReaderLogin(theReader.getUsername())) {
			restTemplate.postForObject(serviceUrlName + "/readers", theReader, Reader.class);
			Reader reader = restTemplate.getForObject(serviceUrlName + "/readers/logins/{readerLogin}", Reader.class,
					params);
			readerRole.setReaderId(reader.getId());
			readerRole.setRoleId(2);
			restTemplate.postForObject(serviceUrlName + "/readerrole", readerRole, ReaderRole.class);
			return true;
		} else
			return false;
	}

	@Override
	public Reader getReaderByUsername(String login) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", login);

		Reader reader = restTemplate.getForObject(serviceUrlName + "/readers/logins/{readerLogin}", Reader.class,
				params);

		return reader;
	}

	@Override
	public boolean checkReaderLogin(String username) {

		Reader reader = getReaderByUsername(username);

		if (reader == null)
			return false;
		else
			return true;
	}

	@Override
	public Reader getReader(String login, String password) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", login);

		Reader reader = restTemplate.getForObject(serviceUrlName + "/readers/logins/{readerLogin}", Reader.class,
				params);

		if (reader != null) {
			if (reader.getPassword().equals(password))
				return reader;
			else
				return null;
		} else
			return null;

	}

	@Override
	public void updateReader(Reader reader) {
		restTemplate.put(serviceUrlName + "/readers", reader);

	}

	@Override
	public List<Review> getReaderReviews(String username) {

		Map<String, String> params = new HashMap<>();
		params.put("username", username);

		ResponseEntity<Review[]> reviews = restTemplate
				.getForEntity(serviceUrlName + "/readers/{username}/books/reviews", Review[].class, params);

		Review[] reviewsArray = reviews.getBody();
		List<Review> reviewsList = Arrays.asList(reviewsArray);

		return reviewsList;
	}

	@Override
	public Reader getReaderById(int readerId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(readerId));

		Reader reader = restTemplate.getForObject(serviceUrlName + "/readers/{readerId}", Reader.class, params);

		return reader;
	}

	@Override
	public int getReaderId(String readerLogin) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", String.valueOf(readerLogin));

		Reader reader = restTemplate.getForObject(serviceUrlName + "/readers/logins/{readerLogin}", Reader.class,
				params);

		return reader.getId();
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

		Reader reader = restTemplate.getForObject(serviceUrlName + "/readers/emails/{email}", Reader.class, params);

		return reader;
	}

}
