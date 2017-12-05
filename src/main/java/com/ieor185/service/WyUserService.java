package com.ieor185.service;


import com.ieor185.model.WyUser;
import com.ieor185.repository.WyUserRepo;
import com.ieor185.viewmodel.UserInfoVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;


/**
 * Created by lhhong on 03/12/2017.
 * Handles user related issues
 */
@Service
public class WyUserService {

   private static final Logger logger = LoggerFactory.getLogger(WyUserService.class);

   @Autowired
   WyUserRepo wyUserRepo;

   @Autowired
   TestFriendsService testFriendsService;

   @Transactional
   public void login(UserInfoVM user) {
      if (!wyUserRepo.exists(user.getId())) {
         logger.info("{} not found in database, registering {}", user.getId(), user.getName());
         Set<String> testFriends = testFriendsService.randomTestFriends(user.getId());
         WyUser toSave = user.toEntity();
         toSave.addFriends(testFriends);
         wyUserRepo.save(toSave);
         testFriends.forEach(f -> addFriend(f, user.getId()));
      }
   }

   @Transactional
   public void addFriend(String userId, String friendId) {
      WyUser user = wyUserRepo.findOne(userId);
      user.addFriend(friendId);
      wyUserRepo.save(user);
   }

   @Transactional
   public void updateFriends(String userId, Collection<String> friends) {
      WyUser user = wyUserRepo.findOne(userId);
      user.addFriends(friends);
      wyUserRepo.save(user);
   }

}
