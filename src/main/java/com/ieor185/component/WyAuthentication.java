package com.ieor185.component;


import com.ieor185.user.model.WyUser;
import com.ieor185.user.model.WyUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;


/**
 * Created by xschen on 21/12/2016.
 */
@Component
public class WyAuthentication {

   private static final Logger logger = LoggerFactory.getLogger(WyAuthentication.class);

   public boolean hasRole(Authentication authentication, String role) {
      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
      for(GrantedAuthority authority : authorities) {
         String authorityRole = authority.getAuthority();
         if(authorityRole.equals(role)){
            return true;
         }
      }
      return false;
   }

   public Optional<WyUserDetails> getCurrentUser() {

      if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof WyUserDetails) {
         return Optional.of((WyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
      }
      return Optional.empty();
   }

   public String getUsername(){
      Optional<WyUserDetails> user = getCurrentUser();
      return user.map(WyUserDetails::getUsername).orElse("anonymousUser");
   }


   public long getUserId(){
      Optional<WyUserDetails> user = getCurrentUser();
      return user.map(WyUserDetails::getUserId).orElse(-1L);
   }

   public boolean isSuperUser() {
      Optional<WyUserDetails> user = getCurrentUser();
      return user.map(WyUserDetails::isSuperUser).orElse(false);
   }

   public Optional<WyUser> getUser() {
      Optional<WyUserDetails> user = getCurrentUser();
      return user.map(WyUserDetails::getUser);
   }

   public boolean hasRole(String role) {
      Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
      for(GrantedAuthority authority : authorities) {
         String authorityRole = authority.getAuthority();
         logger.info("role: {}", authorityRole);
         if(authorityRole.equals(role)){
            return true;
         }
      }
      return false;
   }

   public boolean isLoggedIn(){
      return SecurityContextHolder.getContext().getAuthentication() != null &&
              SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
              //when Anonymous Authentication is enabled
              !(SecurityContextHolder.getContext().getAuthentication()
                      instanceof AnonymousAuthenticationToken);
   }



}
