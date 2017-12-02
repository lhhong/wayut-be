package com.ieor185.user.service;


import com.ieor185.user.model.ClefUser;
import com.ieor185.user.model.ClefUserEntity;
import com.ieor185.user.model.SignupForm;
import com.ieor185.user.repository.ClefUserEntityRepository;
import com.ieor185.user.viewmodel.UserEntityViewModel;
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
public class ClefUserServiceImpl implements ClefUserService {

   private static final Logger logger = LoggerFactory.getLogger(ClefUserServiceImpl.class);

   @Autowired
   ClefUserEntityRepository userEntityRepository;

   @Autowired
   PasswordEncoder passwordEncoder;

   @Override public boolean isUserAuthenticated(String username, String password) {

      username = username.toLowerCase();

      Optional<ClefUser> userOptional = findUserByUsername(username);
      if(userOptional.isPresent()){
         return userOptional.get().getPassword().equals(encryptPassword(password));
      }
      return false;
   }


   @Override public Optional<ClefUser> findUserByUsername(String name) {
      name = name.toLowerCase();

      List<ClefUserEntity> users = userEntityRepository.findByUsername(name);
      if(users.isEmpty()) {
         return Optional.empty();
      }
      return Optional.of(users.get(0));
   }

   @Override public List<ClefUser> findUserByEmail(String email) {
      email = email.toLowerCase();
      return userEntityRepository.findByEmail(email).stream().collect(Collectors.toList());
   }

   @Override
   public boolean usernameExists(String username) {
   	return findUserByUsername(username).isPresent();
   }

   @Transactional
   @Override public ClefUser save(ClefUser user) {
      String username = user.getUsername().toLowerCase();
      user.setUsername(username);

      Optional<ClefUser> userOptional = findUserByUsername(user.getUsername());
      if(userOptional.isPresent()){
         ClefUser existingUser = userOptional.get();
         String oldPassword = existingUser.getPassword();
         existingUser.copyProfile(user);

         if (user.getAuthenticationSource().equals("password")) {
            if (!oldPassword.equals(existingUser.getPassword())) {
               existingUser.setPassword(encryptPassword(existingUser.getPassword()));
            }
         }
         return userEntityRepository.save(entity(existingUser));
      } else {
         user.setCreatedTime(new Date());
         if (user.getAuthenticationSource().equals("password")) {
            user.setPassword(encryptPassword(user.getPassword()));
         }
         return userEntityRepository.save(entity(user));
      }
   }

   private ClefUserEntity entity(ClefUser user) {
      return new ClefUserEntity(user);
   }




   private String encryptPassword(String rawPassword) {

      return passwordEncoder.encode(rawPassword);

   }


   @Override public List<ClefUser> findAll() {
      List<ClefUser> result = new ArrayList<>();
      for(ClefUser user : userEntityRepository.findAll()){
         result.add(user);
      }
      return result;
   }


   @Override public Page<ClefUserEntity> findByPage(int pageIndex, int pageSize) {
      return userEntityRepository.findAll(new PageRequest(pageIndex, pageSize));
   }


   @Override public Page<ClefUserEntity> findByPage(int pageIndex, int pageSize, Sort sort) {
      return userEntityRepository.findAll(new PageRequest(pageIndex, pageSize, sort));
   }


   @Override public long countUsers() {
      return userEntityRepository.count();
   }


   @Override public long countPages(int pageSize) {
      return (long)Math.ceil((double)countUsers() / pageSize);
   }


   @Override public Optional<ClefUser> findUserById(long userId) {
      ClefUser user = userEntityRepository.findOne(userId);
      if(user == null) {
         return Optional.empty();
      } else {
         return Optional.of(user);
      }
   }

   @Override
   @Transactional
   public ClefUserEntity updateProfilePic(long userId, String imageUrl) {
      ClefUserEntity existing = userEntityRepository.findOne(userId);
      if (existing == null) return null;
      existing.setImageUrl(imageUrl);
      userEntityRepository.save(existing);

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

      Optional<ClefUser> user = findUserByUsername(email);

      if (!user.isPresent()) {
         throw new Exception("User not found");
      }

      ClefUser userToSave = user.get();
      userToSave.setPassword(encryptPassword(newPassword));
      userEntityRepository.save(entity(userToSave));

      return newPassword;
   }

   @Override
   @Transactional
   public ClefUserEntity updateUserEntity(long userId, UserEntityViewModel userViewModel) {
      ClefUserEntity existing = userEntityRepository.findOne(userId);
      if (existing == null) return null;
      existing.setDisplayName(userViewModel.getDisplayName());
      existing.setDescription(userViewModel.getDescription());
      //leave updating of imageUrl to "updateProfilePic()"
      userEntityRepository.save(existing);

      return existing;
   }

   @Override public ClefUser deleteUserById(long userId) {
      ClefUser user = userEntityRepository.findOne(userId);
      userEntityRepository.delete(userId);
      return user;
   }

   @Override public boolean signup(SignupForm signupForm) {

      Optional<ClefUser> userOptional = findUserByUsername(signupForm.getEmail());
      if (userOptional.isPresent()) return false;

      ClefUser user = new ClefUserEntity();
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
