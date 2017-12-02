package com.ieor185.component;


import com.ieor185.user.model.WyUser;
import com.ieor185.user.model.WyUserEntity;
import com.ieor185.user.service.WyUserService;
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

      WyUserService wyUserService = context.getBean(WyUserService.class);

      logger.info("Run users...");
      setupUsers(wyUserService);
      setupAdmin(wyUserService);
   }


   private void setupAdmin(WyUserService wyUserService) {

      String username = "admin";
      logger.info("Setup {}", username);

      WyUser admin;
      Optional<WyUser> userOptional = wyUserService.findUserByUsername(username);
      if(userOptional.isPresent()){
         admin = userOptional.get();
         String roles = admin.getRoles();
         if(!roles.contains("ROLE_ADMIN") || !roles.contains("ROLE_USER")) {
            admin.setRoles("ROLE_USER,ROLE_ADMIN");
         }
      } else {
         admin = new WyUserEntity();
         admin.setUsername("admin");
         admin.setRoles("ROLE_USER,ROLE_ADMIN");
         admin.setPassword("clef@2016");
         admin.setEmail("someadmin@email.com");
         admin.setEnabled(true);
         admin.setAuthenticationSource("password");
      }

      wyUserService.save(admin);
   }

   private void setupUsers(WyUserService wyUserService){
      for(int i=1; i <= 2; ++i){

         String username = "user" + i;
         logger.info("Setup {}", username);

         WyUser user;
         Optional<WyUser> userOptional = wyUserService.findUserByUsername(username);
         if(userOptional.isPresent()) {
            user = userOptional.get();
         } else {
            user = new WyUserEntity();
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
         wyUserService.save(user);
      }
   }


}
