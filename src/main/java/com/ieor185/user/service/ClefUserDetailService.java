package com.ieor185.user.service;


import com.ieor185.user.model.ClefUser;
import com.ieor185.user.model.ClefUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Created by xschen on 16/10/2016.
 */
@Service
public class ClefUserDetailService implements UserDetailsService {

   private static final Logger logger = LoggerFactory.getLogger(ClefUserDetailService.class);

   @Autowired
   private ClefUserService clefUserService;

   @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<ClefUser> userOptional = clefUserService.findUserByUsername(username);

      logger.info("loading user with username = {}", username);

      if(!userOptional.isPresent()){
         logger.warn("System does not find a user with username: {}", username);
         throw new UsernameNotFoundException("User not found");
      }

      ClefUser user = userOptional.get();

      String roles = getRoles(user);

      return new ClefUserDetails(user, roles);
   }

   private String getRoles(ClefUser user) {
      if(user.getUsername().equalsIgnoreCase("admin")) {
         return user.getRoles() + ",ROLE_ROOT";
      }

      if(user.isEnabled()){
         return user.getRoles();
      }
      else {
         return user.getRoles() + ",ROLE_DISABLED";
      }
   }
}
