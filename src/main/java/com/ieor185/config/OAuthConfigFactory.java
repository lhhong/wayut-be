package com.ieor185.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 * Created by low on 20/6/17 5:32 PM.
 */
@Configuration
public class OAuthConfigFactory {

	@Bean(name = "googleClientConfig")
	@ConfigurationProperties("oauth2.google")
	public OAuth2ProtectedResourceDetails getGoogleClientConfig() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean(name = "facebookClientConfig")
	@ConfigurationProperties("oauth2.facebook")
	public OAuth2ProtectedResourceDetails getFacebookClientConfig() {
		return new AuthorizationCodeResourceDetails();
	}
}
