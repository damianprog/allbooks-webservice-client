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

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.utils.entity.ReaderBookHelperPage;

@Service
public class ReaderBookWebserviceImpl implements ReaderBookWebservice {

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
	public void saveReaderBook(ReaderBook readerBook) {
		restTemplate.put("http://localhost:9000/readerbooks" + accessTokenParameter, readerBook);

	}

	@Override
	public ReaderBook getReaderBook(int bookId, int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("bookId", bookId);

		ReaderBook readerBook = restTemplate.getForObject(
				serviceUrlName + "/readers/{readerId}/readerbooks/{bookId}" + accessTokenParameter, ReaderBook.class,
				params);

		return readerBook;

	}

	@Override
	public void updateReaderBook(ReaderBook readerBook) {

		restTemplate.put("http://localhost:9000/readerbooks" + accessTokenParameter, readerBook);

	}

	@Override
	public ReaderBook[] getReaderBooks(int id) {

		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(id));

		ResponseEntity<ReaderBook[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/readers/{readerId}/readerbooks" + accessTokenParameter, ReaderBook[].class, params);

		return responseEntity.getBody();
	}

	@Override
	public void deleteReaderBookByReaderIdAndBookId(int readerId, int bookId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("bookId", bookId);

		restTemplate.delete(serviceUrlName + "/readers/{readerId}/readerbooks/books/{bookId}" + accessTokenParameter,
				params);

	}

	@Override
	public ReaderBook getReaderBookById(int readerBookId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerBookId", readerBookId);

		ReaderBook readerBook = restTemplate.getForObject(
				serviceUrlName + "/readerbooks/{readerBookId}" + accessTokenParameter, ReaderBook.class, params);

		return readerBook;

	}

	@Override
	public ReaderBook[] getReaderBooksByShelves(int readerId, ShelvesState shelvesStates) {

		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));
		params.put("shelves", shelvesStates.shelveState());

		ResponseEntity<ReaderBook[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/readers/{readerId}/readerbooks/shelves/{shelves}" + accessTokenParameter,
				ReaderBook[].class, params);

		return responseEntity.getBody();

	}

	@Override
	public Page<ReaderBook> getReaderBooksPages(int readerId, int page) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("page", page);

		ReaderBookHelperPage responseEntity = restTemplate.getForObject(
				serviceUrlName + "/readers/{readerId}/readerbooks/pages/{page}" + accessTokenParameter,
				ReaderBookHelperPage.class, params);

		return responseEntity;
	}

	@Override
	public Page<ReaderBook> getReaderBooksByShelvesPages(int readerId, ShelvesState shelvesStates, int page) {
		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));
		params.put("shelves", shelvesStates.shelveState());
		params.put("page", String.valueOf(page));

		ReaderBookHelperPage responseEntity = restTemplate.getForObject(serviceUrlName
				+ "/readers/{readerId}/readerbooks/shelves/{shelves}/pages/{page}" + accessTokenParameter,
				ReaderBookHelperPage.class, params);

		return responseEntity;
	}

	@Override
	public ReaderBook[] get10LatestReaderBooks() {

		ResponseEntity<ReaderBook[]> responseEntity = restTemplate
				.getForEntity(serviceUrlName + "/readerbooks/latest" + accessTokenParameter, ReaderBook[].class);

		return responseEntity.getBody();

	}

	@Override
	public ReaderBook[] getReaderBooksByCategory(int readerId, String category) {

		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));
		params.put("category", category);

		ResponseEntity<ReaderBook[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/readers/{readerId}/readerbooks/categories/{category}" + accessTokenParameter,
				ReaderBook[].class, params);
		
		return responseEntity.getBody();
	}

	@Override
	public int[] getReaderBooksBooksIdsByReaderIdAndCategory(int readerId, String category) {
		
		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));
		params.put("category", category);
		
		ResponseEntity<int[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/readers/{readerId}/readerbooks/categories/{category}/books/ids" + accessTokenParameter,
				int[].class, params);
			
		return responseEntity.getBody();
	}

	@Override
	public ReaderBook[] get10LatestReaderBooksByReaderId(int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		
		ResponseEntity<ReaderBook[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/readers/{readerId}/readerbooks/latest" + accessTokenParameter,
				ReaderBook[].class, params);
		
		return responseEntity.getBody();
	}

}
