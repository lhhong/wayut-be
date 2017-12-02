package com.ieor185.user.service;


import com.ieor185.user.model.ClefUser;
import com.ieor185.user.model.ClefUserEntity;
import com.ieor185.user.model.SignupForm;
import com.ieor185.user.viewmodel.UserEntityViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 16/10/2016.
 */
public interface ClefUserService {
   boolean isUserAuthenticated(String username, String password);

   Optional<ClefUser> findUserByUsername(String name);
   Optional<ClefUser> findUserById(long userId);
   List<ClefUser> findUserByEmail(String email);

	boolean usernameExists(String username);

	ClefUser save(ClefUser user);


   List<ClefUser> findAll();
   Page<ClefUserEntity> findByPage(int pageIndex, int pageSize);
   Page<ClefUserEntity> findByPage(int pageIndex, int pageSize, Sort sort);

   long countUsers();
   long countPages(int pageSize);

	@Transactional
	ClefUserEntity updateProfilePic(long userId, String imageUrl);

	/**
	 * @Deprecated implementation already in FreeAgentController
	 * @param email
	 * @return
	 * @throws Exception
	 */
	String assignNewPassword(String email) throws Exception;

	@Transactional
	ClefUserEntity updateUserEntity(long userId, UserEntityViewModel userViewModel);

	ClefUser deleteUserById(long userId);


	boolean signup(SignupForm signupForm);

}
