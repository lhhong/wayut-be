package com.ieor185;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.TimeUnit;


/**
 * Created by xschen on 26/12/2016.
 */
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
@EnableAsync
public class WayutApplication {
   public static void main(String[] args) {
      SpringApplication.run(WayutApplication.class, args);
   }

}
