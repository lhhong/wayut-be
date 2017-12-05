package com.ieor185.component;


import com.ieor185.model.WyUser;
import com.ieor185.repository.WyUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;


/**
 * Created by xschen on 15/12/2016.
 * Schedule that runs schedule task for various tasks on user and subscription management
 */
@Component
public class Scheduler {

   private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

   @Autowired
   private WyUserRepo userRepo;

   private int pageSize = 200;

   @Scheduled(fixedDelay = 60000L) //every min
   @Transactional
   public void resetAvailability() {

   }

}
