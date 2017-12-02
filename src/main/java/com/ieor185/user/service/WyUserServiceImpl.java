package com.ieor185.user.service;


import com.ieor185.user.model.WyUser;
import com.ieor185.user.model.WyUserEntity;
import com.ieor185.user.model.SignupForm;
import com.ieor185.user.repository.WyUserEntityRepository;
import com.ieor185.user.viewmodel.WyUserEntityViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by xschen on 16/10/2016.
 * Service that provides the handling for user and subscription
 */
@Service
public class WyUserServiceImpl implements WyUserService {

   private static final Logger logger = LoggerFactory.getLogger(WyUserServiceImpl.class);

   @Autowired
   WyUserEntityRepository wyUserEntityRepository;

   @Autowired
   PasswordEncoder passwordEncoder;

   @Override public boolean isUserAuthenticated(String username, String password) {

      username = username.toLowerCase();

      Optional<WyUser> userOptional = findUserByUsername(username);
      if(userOptional.isPresent()){
         return userOptional.get().getPassword().equals(encryptPassword(password));
      }
      return false;
   }


   @Override public Optional<WyUser> findUserByUsername(String name) {
      name = name.toLowerCase();

      List<WyUserEntity> users = wyUserEntityRepository.findByUsername(name);
      if(users.isEmpty()) {
         return Optional.empty();
      }
      return Optional.of(users.get(0));
   }

   @Override public List<WyUser> findUserByEmail(String email) {
      email = email.toLowerCase();
      return wyUserEntityRepository.findByEmail(email).stream().collect(Collectors.toList());
   }

   @Override
   public boolean usernameExists(String username) {
   	return findUserByUsername(username).isPresent();
   }

   @Transactional
   @Override public WyUser save(WyUser user) {
      String username = user.getUsername().toLowerCase();
      user.setUsername(username);

      Optional<WyUser> userOptional = findUserByUsername(user.getUsername());
      if(userOptional.isPresent()){
         WyUser existingUser = userOptional.get();
         String oldPassword = existingUser.getPassword();
         existingUser.copyProfile(user);

         if (user.getAuthenticationSource().equals("password")) {
            if (!oldPassword.equals(existingUser.getPassword())) {
               existingUser.setPassword(encryptPassword(existingUser.getPassword()));
            }
         }
         return wyUserEntityRepository.save(entity(existingUser));
      } else {
         user.setCreatedTime(new Date());
         if (user.getAuthenticationSource().equals("password")) {
            user.setPassword(encryptPassword(user.getPassword()));
         }
         return wyUserEntityRepository.save(entity(user));
      }
   }

   private WyUserEntity entity(WyUser user) {
      return new WyUserEntity(user);
   }




   private String encryptPassword(String rawPassword) {

      return passwordEncoder.encode(rawPassword);

   }


   @Override public List<WyUser> findAll() {
      List<WyUser> result = new ArrayList<>();
      for(WyUser user : wyUserEntityRepository.findAll()){
         result.add(user);
      }
      return result;
   }


   @Override public Page<WyUserEntity> findByPage(int pageIndex, int pageSize) {
      return wyUserEntityRepository.findAll(new PageRequest(pageIndex, pageSize));
   }


   @Override public Page<WyUserEntity> findByPage(int pageIndex, int pageSize, Sort sort) {
      return wyUserEntityRepository.findAll(new PageRequest(pageIndex, pageSize, sort));
   }


   @Override public long countUsers() {
      return wyUserEntityRepository.count();
   }


   @Override public long countPages(int pageSize) {
      return (long)Math.ceil((double)countUsers() / pageSize);
   }


   @Override public Optional<WyUser> findUserById(long userId) {
      WyUser user = wyUserEntityRepository.findOne(userId);
      if(user == null) {
         return Optional.empty();
      } else {
         return Optional.of(user);
      }
   }

   @Override
   @Transactional
   public WyUserEntity updateProfilePic(long userId, String imageUrl) {
      WyUserEntity existing = wyUserEntityRepository.findOne(userId);
      if (existing == null) return null;
      existing.setImageUrl(imageUrl);
      wyUserEntityRepository.save(existing);

      return existing;
   }

   /**
    * @Deprecated Implementation already in FreeAgentController
    * @param email
    * @return
    * @throws Exception
    */
   @Override
   public String assignNewPassword(String email) throws Exception {
      RandomValueStringGenerator generator = new RandomValueStringGenerator(8);
      String newPassword = generator.generate();

      Optional<WyUser> user = findUserByUsername(email);

      if (!user.isPresent()) {
         throw new Exception("User not found");
      }

      WyUser userToSave = user.get();
      userToSave.setPassword(encryptPassword(newPassword));
      wyUserEntityRepository.save(entity(userToSave));

      return newPassword;
   }

   @Override
   @Transactional
   public WyUserEntity updateUserEntity(long userId, WyUserEntityViewModel userViewModel) {
      WyUserEntity existing = wyUserEntityRepository.findOne(userId);
      if (existing == null) return null;
      existing.setDisplayName(userViewModel.getDisplayName());
      existing.setDescription(userViewModel.getDescription());
      //leave updating of imageUrl to "updateProfilePic()"
      wyUserEntityRepository.save(existing);

      return existing;
   }

   @Override public WyUser deleteUserById(long userId) {
      WyUser user = wyUserEntityRepository.findOne(userId);
      wyUserEntityRepository.delete(userId);
      return user;
   }

   @Override public boolean signup(SignupForm signupForm) {

      Optional<WyUser> userOptional = findUserByUsername(signupForm.getEmail());
      if (userOptional.isPresent()) return false;

      WyUser user = new WyUserEntity();
      user.setEmail(signupForm.getEmail());
      user.setUsername(signupForm.getEmail());
      user.setDescription(signupForm.getDescription());
      user.setDisplayName(signupForm.getDisplayName());
      user.setEnabled(true);
      user.setPassword(signupForm.getPassword());
      user.setAuthenticationSource("password");
      user.setRoles("ROLE_USER");
      save(user);
      return true;
   }

}
