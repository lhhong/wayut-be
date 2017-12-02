package com.ieor185.config;


import com.ieor185.user.service.ClefUserDetailService;
import com.ieor185.user.service.ClefUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 15/10/2016.
 */

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private ClefUserDetailService clefUserDetailService;

   @Autowired
   private ClefUserService clefUserService;

   @Autowired
   private OAuth2ClientContext clientContext;

   @Resource
   private OAuth2ProtectedResourceDetails googleClientConfig;

   @Resource
   private OAuth2ProtectedResourceDetails facebookClientConfig;

   @Bean
   public Filter authFilter() {
      CompositeFilter compositeFilter = new CompositeFilter();
      List<Filter> filters = new ArrayList<>();

      final OpenIdConnectFilter googleFilter = new OpenIdConnectFilter("/google-login");
      OAuth2RestTemplate googleRestTemplate = new OAuth2RestTemplate(googleClientConfig, clientContext);
      googleFilter.setComponents(googleRestTemplate, clefUserDetailService, clefUserService,"google");
      filters.add(googleFilter);

      final OpenIdConnectFilter facebookFilter = new OpenIdConnectFilter("/facebook-login");
      OAuth2RestTemplate facebookRestTemplate = new OAuth2RestTemplate(facebookClientConfig, clientContext);
      facebookFilter.setComponents(facebookRestTemplate, clefUserDetailService, clefUserService,"facebook");
      filters.add(facebookFilter);

      compositeFilter.setFilters(filters);
      return compositeFilter;
   }

   @Bean
   public Filter clientContextFilter() {
      return new OAuth2ClientContextFilter();
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {

      http
            .addFilterAfter(clientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
            .addFilterAfter(authFilter(), OAuth2ClientContextFilter.class)
            .httpBasic()
//            .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/post-login"))

               .and()
            .authorizeRequests()
               .antMatchers("/js/admin/**").hasAnyRole("ADMIN")
               .antMatchers("/admin/**").hasAnyRole("ADMIN")
               .antMatchers("/js/user/**").hasAnyRole("USER")
               .antMatchers("/user/**").hasAnyRole("USER")
               .antMatchers("/js/commons/**").permitAll()
               .antMatchers("/commons/**").permitAll()

               .antMatchers("/css/**").permitAll()
               .antMatchers("/img/**").permitAll()
               .antMatchers("/libs/**").permitAll()
/*
//              .antMatchers("/js/client/**").hasAnyRole("USER", "ADMIN")
              .antMatchers("/js/main/**").permitAll()
              .antMatchers("/fonts/*.*").permitAll()
              .antMatchers("/signup").permitAll()
              .antMatchers("/google-login").permitAll()
              .antMatchers("/locales").permitAll()
              .antMatchers("/locales/**").permitAll()
              .antMatchers("/privacy-policy").permitAll()
              .antMatchers("/change-locale").permitAll()
              .antMatchers("/link-cache").permitAll()
              .antMatchers("/signup-success").permitAll()
              .antMatchers("/terms-of-use").permitAll()
              .antMatchers("/contact-us").permitAll()
              .antMatchers("/forgot-password").permitAll()
              .antMatchers("/about-us").permitAll()
              .antMatchers("/terms-of-use-en").permitAll()
              .antMatchers("/main/**").permitAll()
              */
              .antMatchers("/**").permitAll()

                .anyRequest().authenticated()
                .and()
              .formLogin()
                .loginPage("/_home/login")
                .loginPage("/_home/login.view")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
              .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
              .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
   }

   @Autowired
   PasswordEncoder passwordEncoder;

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(clefUserDetailService)
              .passwordEncoder(passwordEncoder);
   }

}
