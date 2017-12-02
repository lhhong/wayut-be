package com.ieor185.user.service;


import com.ieor185.user.model.WyUser;
import com.ieor185.user.model.WyUserEntity;
import com.ieor185.user.model.SignupForm;
import com.ieor185.user.viewmodel.WyUserEntityViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * Created by xschen on 16/10/2016.
 */
public interface WyUserService {
   boolean isUserAuthenticated(String username, String password);

   Optional<WyUser> findUserByUsername(String name);
   Optional<WyUser> findUserById(long userId);
   List<WyUser> findUserByEmail(String email);

	boolean usernameExists(String username);

	WyUser save(WyUser user);


   List<WyUser> findAll();
   Page<WyUserEntity> findByPage(int pageIndex, int pageSize);
   Page<WyUserEntity> findByPage(int pageIndex, int pageSize, Sort sort);

   long countUsers();
   long countPages(int pageSize);

	@Transactional
	WyUserEntity updateProfilePic(long userId, String imageUrl);

	/**
	 * @Deprecated implementation already in FreeAgentController
	 * @param email
	 * @return
	 * @throws Exception
	 */
	String assignNewPassword(String email) throws Exception;

	@Transactional
	WyUserEntity updateUserEntity(long userId, WyUserEntityViewModel userViewModel);

	WyUser deleteUserById(long userId);


	boolean signup(SignupForm signupForm);

}
