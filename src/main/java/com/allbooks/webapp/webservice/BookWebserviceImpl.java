package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.utils.entity.HelperPage;

@Service
public class BookWebserviceImpl implements BookWebservice {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;

	

	@Override
	public Book getBook(int bookId) {

		Map<String, String> params = new HashMap<>();
		params.put("bookId", String.valueOf(bookId));

		Book book = restTemplate.getForObject(serviceUrlName + "/books/{bookId}", Book.class, params);

		return book;
	}

	@Override
	public Book getBookByName(String bookname) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("title", bookname);

		Book book = restTemplate.getForObject(serviceUrlName + "/books/title/{title}", Book.class, params);

		return book;
	}

	@Override
	public void saveBook(Book book) {
		restTemplate.postForObject(serviceUrlName + "/books", book, Book.class);

	}

	

	@Override
	public Page<Book> getBooksByCategory(String category, int page) {
		Map<String, String> params = new HashMap<>();
		params.put("category", category);
		params.put("page", String.valueOf(page));

		HelperPage responseEntity = restTemplate.getForObject(serviceUrlName + "/books/categories/{category}/{page}",
				HelperPage.class, params);

		return responseEntity;
	}

	

}
