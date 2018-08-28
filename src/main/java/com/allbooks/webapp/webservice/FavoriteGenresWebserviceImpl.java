package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.FavouriteGenres;

@Service
public class FavoriteGenresWebserviceImpl implements FavoriteGenresWebservice{

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
	public void saveFavoriteGenres(FavouriteGenres favoriteGenres) {

		restTemplate.postForObject(serviceUrlName + "/favoriteGenres" + accessTokenParameter, favoriteGenres, FavouriteGenres.class);
		
	}

	@Override
	public FavouriteGenres getFavoriteGenresByReaderId(int readerId) {
		
		Map<String,Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		
		return restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/favoriteGenres" + accessTokenParameter, FavouriteGenres.class,params);
		
	}

	@Override
	public FavouriteGenres getFavoriteGenresById(int favoriteGenresId) {
		Map<String,Integer> params = new HashMap<>();
		params.put("favoriteGenresId", favoriteGenresId);
		
		return restTemplate.getForObject(serviceUrlName + "/favoriteGenres/{favoriteGenresId}" + accessTokenParameter, FavouriteGenres.class,params);
	}

}
