package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.ReaderBook;

@Service
public class ReaderBookWebserviceImpl implements ReaderBookWebservice{

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;
	
	@Override
	public void saveReaderBook(ReaderBook readerBook) {
		restTemplate.put("http://localhost:9000/readerbooks", readerBook);

	}

	@Override
	public ReaderBook getReaderBook(int bookId, int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("bookId", bookId);

		ReaderBook readerBook = restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/readerbooks/{bookId}",
				ReaderBook.class, params);

		return readerBook;

	}

	@Override
	public void updateReaderBook(ReaderBook readerBook) {

		restTemplate.put("http://localhost:9000/readerbooks", readerBook);

	}

	@Override
	public ReaderBook[] getReaderBooks(int id) {

		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(id));

		ResponseEntity<ReaderBook[]> responseEntity = restTemplate
				.getForEntity(serviceUrlName + "/readers/{readerId}/readerbooks", ReaderBook[].class, params);

		return responseEntity.getBody();
	}
	
	@Override
	public void deleteReaderBookByReaderIdAndBookId(int readerId, int bookId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("bookId", bookId);

		restTemplate.delete(serviceUrlName + "/readers/{readerId}/readerbooks/books/{bookId}", params);

	}
	
	@Override
	public ReaderBook getReaderBookById(int readerBookId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerBookId", readerBookId);

		ReaderBook readerBook = restTemplate.getForObject(serviceUrlName + "/readerbooks/{readerBookId}",
				ReaderBook.class, params);

		return readerBook;

	}

	
}
