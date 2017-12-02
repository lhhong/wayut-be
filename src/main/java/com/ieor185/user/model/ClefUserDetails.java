package com.ieor185.user.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.Collection;
import java.util.List;


/**
 * Created by xschen on 11/12/2016.
 */
public class ClefUserDetails implements UserDetails {

   private ClefUser user;

   private List<GrantedAuthority> authorities;

   private OAuth2AccessToken token;


   public ClefUserDetails(ClefUser user, String roles) {
      this.user = user;
      authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
   }

   public ClefUserDetails(OAuth2AccessToken token, ClefUser user) {
      this.token = token;
      this.user = user;
      authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
   }

   public void setToken(OAuth2AccessToken token) {
      this.token = token;
   }

   public OAuth2AccessToken getToken() {
      return token;
   }

   public long getUserId() { return user.getId(); }


   public String getEmail() {
      return user.getEmail();
   }

   public boolean isSuperUser() { return user.isSuperUser(); }


   public ClefUser getUser() {
      return user;
   }


   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
   }

   @Override
   public String getPassword() {
      return user.getPassword();
   }

   @Override
   public String getUsername() {
   	return user.getUsername();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
}
