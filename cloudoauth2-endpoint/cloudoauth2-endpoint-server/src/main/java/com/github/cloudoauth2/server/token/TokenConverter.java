package com.github.cloudoauth2.server.token;

import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

public class TokenConverter extends DefaultAccessTokenConverter{

	public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication){
		Map<String, ?> response = super.convertAccessToken(token, authentication);
		response.remove(AccessTokenConverter.AUD);
		return response;
	}
}
