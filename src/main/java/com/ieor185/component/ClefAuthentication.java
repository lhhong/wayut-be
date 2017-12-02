package com.ieor185.component;


import com.ieor185.user.model.ClefUser;
import com.ieor185.user.model.ClefUserDetails;
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
public class ClefAuthentication {

   private static final Logger logger = LoggerFactory.getLogger(ClefAuthentication.class);

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

   public Optional<ClefUserDetails> getCurrentUser() {

      if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof ClefUserDetails) {
         return Optional.of((ClefUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
      }
      return Optional.empty();
   }

   public String getUsername(){
      Optional<ClefUserDetails> user = getCurrentUser();
      return user.map(ClefUserDetails::getUsername).orElse("anonymousUser");
   }


   public long getUserId(){
      Optional<ClefUserDetails> user = getCurrentUser();
      return user.map(ClefUserDetails::getUserId).orElse(-1L);
   }

   public boolean isSuperUser() {
      Optional<ClefUserDetails> user = getCurrentUser();
      return user.map(ClefUserDetails::isSuperUser).orElse(false);
   }

   public Optional<ClefUser> getUser() {
      Optional<ClefUserDetails> user = getCurrentUser();
      return user.map(ClefUserDetails::getUser);
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
