package com.ieor185.component;


import com.ieor185.user.model.ClefUser;
import com.ieor185.user.model.ClefUserEntity;
import com.ieor185.user.service.ClefUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by chen0 on 2/6/2016.
 */
@Component
public class Loader implements ApplicationListener<ApplicationReadyEvent> {
   private static final Logger logger = LoggerFactory.getLogger(Loader.class);

   private Process process = null;


   @Override public void onApplicationEvent(ApplicationReadyEvent e) {
      logger.info("Loader triggered at {}", e.getTimestamp());

      ApplicationContext context = e.getApplicationContext();
      String[] beanNames = context.getBeanDefinitionNames();
      Arrays.sort(beanNames);
//      for (String beanName : beanNames) {
//         logger.info("bean: {}", beanName);
//      }
      logger.info("Run loader...");

      ClefUserService userService = context.getBean(ClefUserService.class);

      logger.info("Run users...");
      setupUsers(userService);
      setupAdmin(userService);
   }


   private void setupAdmin(ClefUserService userService) {

      String username = "admin";
      logger.info("Setup {}", username);

      ClefUser admin;
      Optional<ClefUser> userOptional = userService.findUserByUsername(username);
      if(userOptional.isPresent()){
         admin = userOptional.get();
         String roles = admin.getRoles();
         if(!roles.contains("ROLE_ADMIN") || !roles.contains("ROLE_USER")) {
            admin.setRoles("ROLE_USER,ROLE_ADMIN");
         }
      } else {
         admin = new ClefUserEntity();
         admin.setUsername("admin");
         admin.setRoles("ROLE_USER,ROLE_ADMIN");
         admin.setPassword("clef@2016");
         admin.setEmail("someadmin@email.com");
         admin.setEnabled(true);
         admin.setAuthenticationSource("password");
      }

      userService.save(admin);
   }

   private void setupUsers(ClefUserService userService){
      for(int i=1; i <= 2; ++i){

         String username = "user" + i;
         logger.info("Setup {}", username);

         ClefUser user;
         Optional<ClefUser> userOptional = userService.findUserByUsername(username);
         if(userOptional.isPresent()) {
            user = userOptional.get();
         } else {
            user = new ClefUserEntity();
            if(i == 1) {
               user.setEmail("clef.dev@gmail.com");
            } else {
               user.setEmail("clef.jenkins@gmail.com");
            }

            user.setUsername(username);
            user.setPassword(username);
            user.setEnabled(true);
         }

	      user.setAuthenticationSource("password");
         user.setRoles("ROLE_USER");
         userService.save(user);
      }
   }


}
