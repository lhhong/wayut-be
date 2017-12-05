package com.ieor185.service;

import com.ieor185.model.UserStatus;
import com.ieor185.model.WyUser;
import com.ieor185.repository.UserStatusRepo;
import com.ieor185.repository.WyUserRepo;
import com.ieor185.viewmodel.UserStatVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by low on 4/12/17 1:48 AM.
 */
@Service
public class FriendsService {

	private static final Logger logger = LoggerFactory.getLogger(FriendsService.class);

	@Autowired
	private WyUserRepo userRepo;

	@Autowired
	private UserStatusRepo userStatusRepo;

	public List<UserStatVM> getAvailableFriends(String id) {

		List<UserStatVM> friendList = new ArrayList<>();

		WyUser user = userRepo.findOne(id);
		Iterable<WyUser> friends = userRepo.findAll(user.getFriends());
		Iterable<UserStatus> friendsStatus = userStatusRepo.findAll(user.getFriends());
		friends.forEach(f -> {
			friendsStatus.forEach(s -> {
				if (f.getId().equals(s.getId())) {
					friendList.add(UserStatVM.of(f, s));
				}
			});
		});

		return friendList;
	}
}
