package com.ieor185.service;


import com.ieor185.model.WyUser;
import com.ieor185.repository.WyUserRepository;
import com.ieor185.viewmodel.UserInfoVM;
import com.ieor185.viewmodel.UserStatVM;
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
   WyUserRepository wyUserRepository;

   @Autowired
   TestFriendsService testFriendsService;

   @Transactional
   public void login(UserInfoVM user) {
      if (!wyUserRepository.exists(user.getId())) {
         Set<String> testFriends = testFriendsService.randomTestFriends(user.getId());
         WyUser toSave = user.toEntity();
         toSave.addFriends(testFriends);
         wyUserRepository.save(toSave);
      }
   }

   @Transactional
   public void addFriend(String userId, String friendId) {
      WyUser user = wyUserRepository.findOne(userId);
      user.addFriend(friendId);
      wyUserRepository.save(user);
   }

   @Transactional
   public void updateFriends(String userId, Collection<String> friends) {
      WyUser user = wyUserRepository.findOne(userId);
      user.addFriends(friends);
      wyUserRepository.save(user);
   }

}
