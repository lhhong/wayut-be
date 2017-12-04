package com.ieor185.component;


import com.ieor185.service.TestFriendsService;
import com.ieor185.service.WyUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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
//      for (String beanName : beanNames) {
//         logger.info("bean: {}", beanName);
//      }
      logger.info("Run loader...");

      TestFriendsService testFriendsService = context.getBean(TestFriendsService.class);

      logger.info("Inserting mock users...");
      testFriendsService.initialize();
   }

}
