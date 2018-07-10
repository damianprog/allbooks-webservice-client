package com.allbooks.webapp.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Client
@Configuration
public class OAuthClientConfig {
	
    @Value("${oauth.token:http://localhost:9000/oauth/token}")
    private String tokenUrl;
	
    @Bean
    protected OAuth2ProtectedResourceDetails resource() {

        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        
        List<String> scopes = new ArrayList<String>(2);
        scopes.add("write");
        scopes.add("read");
        scopes.add("trust");
        resource.setAccessTokenUri(tokenUrl);
        resource.setClientId("allbooks-webservice-client");
        resource.setClientSecret("P@ssw0rd");
        resource.setGrantType("password");
        resource.setScope(scopes);

        resource.setUsername("allbooks-webservice-user");
        resource.setPassword("admin123");

        return resource;
    }
    
    @Bean
    public OAuth2RestOperations oAuth2RestOperations() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();

        return new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
    }
    
}
