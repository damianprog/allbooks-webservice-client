package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.utils.entity.HelperPage;

@Service
public class BookWebserviceImpl implements BookWebservice {

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
	public Book getBook(int bookId) {

		Map<String, String> params = new HashMap<>();
		params.put("bookId", String.valueOf(bookId));

		Book book = restTemplate.getForObject(serviceUrlName + "/books/{bookId}" + accessTokenParameter, Book.class,
				params);

		return book;
	}

	@Override
	public Book getBookByName(String bookname) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("title", bookname);

		Book book = restTemplate.getForObject(serviceUrlName + "/books/title/{title}" + accessTokenParameter,
				Book.class, params);

		return book;
	}

	@Override
	public void saveBook(Book book) {
		restTemplate.postForObject(serviceUrlName + "/books" + accessTokenParameter, book, Book.class);

	}

	@Override
	public Page<Book> getBooksByCategory(String category, int page) {
		Map<String, String> params = new HashMap<>();
		params.put("category", category);
		params.put("page", String.valueOf(page));

		HelperPage responseEntity = restTemplate.getForObject(
				serviceUrlName + "/books/categories/{category}/{page}" + accessTokenParameter, HelperPage.class,
				params);

		return responseEntity;
	}

	@Override
	public Book[] getBooksByPhrase(String phrase) {
		
		Map<String,String> params = new HashMap<>();
		params.put("phrase", phrase);
		
		ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/books/search/{phrase}" + accessTokenParameter, Book[].class, params);

		return responseEntity.getBody();
		
	}

	@Override
	public Book getBookByCategoryExceptBooksWithIds(String category, int[] excludedIds) {
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i < excludedIds.length; i++) {
			   sb.append(excludedIds[i]);
			   if(i < excludedIds.length-1)
			   sb.append(",");
		}
		
		Map<String,String> params = new HashMap<>();
		params.put("category", category);
		params.put("excludedIds", sb.toString());
		
		return restTemplate.getForObject(serviceUrlName + "/books/categories/{category}/exclude/{excludedIds}" + accessTokenParameter,
				Book.class, params);
	}

}
