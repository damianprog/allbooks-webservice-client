package com.allbooks.webapp.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderRole;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.webservice.ReaderWebservice;

@Service
public class ReaderServiceImpl implements ReaderService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	ReaderWebservice readerWebservice;

	@Override
	public boolean saveReader(Reader theReader) {

		ReaderRole readerRole = new ReaderRole();

		theReader.setPassword(bCryptPasswordEncoder.encode(theReader.getPassword()));

		if (!checkReaderLogin(theReader.getUsername())) {
			readerWebservice.saveReader(theReader);
			Reader reader = readerWebservice.getReaderByUsername(theReader.getUsername());
			readerRole.setReaderId(reader.getId());
			readerRole.setRoleId(2);
			readerWebservice.saveReaderRole(readerRole);
			return true;
		} else
			return false;
	}

	@Override
	public Reader getReaderByUsername(String login) {

		return readerWebservice.getReaderByUsername(login);
	}

	@Override
	public boolean checkReaderLogin(String username) {

		Reader reader = readerWebservice.getReaderByUsername(username);

		if (reader == null)
			return false;
		else
			return true;
	}

	@Override
	public Reader getReaderByUsernameAndPassword(String login, String password) {

		Reader reader = readerWebservice.getReaderByUsernameAndPassword(login, password);

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
		readerWebservice.updateReader(reader);

	}

	@Override
	public List<Review> getReviewsByUsername(String username) {

		List<Review> reviews = Arrays.asList(readerWebservice.getReviewsByUsername(username));
		reviews.sort(Comparator.comparingInt(Review::getId).reversed());
		
		return reviews;
	}

	@Override
	public Reader getReaderById(int readerId) {

		return readerWebservice.getReaderById(readerId);
	}

	@Override
	public int getReaderId(String readerLogin) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", String.valueOf(readerLogin));

		Reader reader = readerWebservice.getReaderByUsername(readerLogin);

		return reader.getId();
	}

	@Override
	public void deleteReader(int readerId) {
		readerWebservice.deleteReader(readerId);

	}

	@Override
	public Reader getReaderByEmail(String email) {

		return readerWebservice.getReaderByEmail(email);
	}

}
