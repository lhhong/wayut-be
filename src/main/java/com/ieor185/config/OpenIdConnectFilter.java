package com.ieor185.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ieor185.user.model.ClefUser;
import com.ieor185.user.model.ClefUserDetails;
import com.ieor185.user.model.ClefUserEntity;
import com.ieor185.user.service.ClefUserDetailService;
import com.ieor185.user.service.ClefUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by low on 21/6/17 9:19 AM.
 */
public class OpenIdConnectFilter extends AbstractAuthenticationProcessingFilter {

	public OAuth2RestOperations restTemplate;

	private ClefUserDetailService userDetailService;

	private ClefUserService userService;

	private String authenticationSource;

	public OpenIdConnectFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		setAuthenticationManager(new NoopAuthenticationManager());
		OAuth2ClientAuthenticationProcessingFilter a;
	}
	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		logger.info("attempting authentication");
		OAuth2AccessToken accessToken;
		try {
			accessToken = restTemplate.getAccessToken();
		} catch (OAuth2Exception e) {
			throw new BadCredentialsException("Could not obtain access token", e);
		}
		try {
			logger.info(JSON.toJSONString(accessToken.getAdditionalInformation(), true));
			String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
			Jwt tokenDecoded = JwtHelper.decode(idToken);
			Map<String, String> authInfo = new ObjectMapper().readValue(tokenDecoded.getClaims(), Map.class);


			logger.info(JSON.toJSONString(authInfo, true));

			UserDetails userDetails = null;
			try {
				ClefUserDetails clefUserDetails;
				clefUserDetails = (ClefUserDetails) userDetailService.loadUserByUsername(authInfo.get("email").toLowerCase());
				if (!clefUserDetails.getUser().getAuthenticationSource().equals(authenticationSource)) {
//					throw new BadCredentialsException("User already registered with another authentication source");
				}
				clefUserDetails.setToken(accessToken);
				userDetails = clefUserDetails;
			} catch (UsernameNotFoundException e) {
				ClefUser user = new ClefUserEntity();
				user.setUsername(authInfo.get("email").toLowerCase());
				user.setEmail(authInfo.get("email").toLowerCase());
				user.setAuthenticationSource(authenticationSource);
				user.setEnabled(true);
				user.setRoles("ROLE_USER");
				if (authInfo.containsKey("name")) {
					user.setDisplayName(authInfo.get("name"));
				}
				if (authInfo.containsKey("picture")) {
					user.setImageUrl(authInfo.get("picture"));
				}
				userService.save(user);
				userDetails = new ClefUserDetails(accessToken, user);
			}

			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		} catch (InvalidTokenException e) {
			logger.warn("invalid token");
			throw new BadCredentialsException("Could not obtain user details from token", e);
		}
	}

	public void setComponents(OAuth2RestTemplate restTemplate, ClefUserDetailService userDetailService,
	                          ClefUserService userService, String authenticationSource) {
		this.restTemplate = restTemplate;
		this.userDetailService = userDetailService;
		this.authenticationSource = authenticationSource;
		this.userService = userService;
	}

	private static class NoopAuthenticationManager implements AuthenticationManager {

		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			throw new UnsupportedOperationException("No authentication should be done with this AuthenticationManager");
		}

	}
}
