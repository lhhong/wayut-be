package com.ieor185.component;


import com.ieor185.model.MeetupGroup;
import com.ieor185.model.WyUser;
import com.ieor185.repository.MeetupGroupRepo;
import com.ieor185.repository.WyUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by xschen on 15/12/2016.
 * Schedule that runs schedule task for various tasks on user and subscription management
 */
@Component
public class Scheduler {

   private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

   @Autowired
   private WyUserRepo userRepo;

   @Autowired
   private MeetupGroupRepo meetupGroupRepo;

   private int pageSize = 200;

   @Scheduled(fixedDelay = 60000L) //every min
   @Transactional
   public void resetAvailability() {
      logger.info("resetting availability");
      userRepo.turnOffExpiredAvailability();
   }

   @Scheduled(fixedDelay = 60000L) //every min
   @Transactional
   public void resetGroup() {
      logger.info("resetting group");
      List<MeetupGroup> expired = meetupGroupRepo.findExpiredMeetup();
      Set<String> ids = expired.stream()
            .flatMap(g ->
                  g.getMembers().stream().map(WyUser::getId))
            .collect(Collectors.toSet());
      ids.add("dummy"); //to prevent sql error due to empty set
      userRepo.quitGroup(ids);
      meetupGroupRepo.delete(expired);
   }
}
