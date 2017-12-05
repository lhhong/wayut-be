package com.ieor185.service;

import com.ieor185.model.MeetupGroup;
import com.ieor185.model.WyUser;
import com.ieor185.repository.MeetupGroupRepo;
import com.ieor185.repository.WyUserRepo;
import com.ieor185.viewmodel.UserStatVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by low on 4/12/17 1:48 AM.
 */
@Service
public class FriendsService {

	private static final Logger logger = LoggerFactory.getLogger(FriendsService.class);

	@Autowired
	private WyUserRepo userRepo;

	@Autowired
	private MeetupGroupRepo meetupGroupRepo;

	public List<UserStatVM> getAvailableFriendsNotInGroup(String id) {

		/*List<UserStatVM> friendList = new ArrayList<>();

		WyUser user = userRepo.findOne(id);
		Iterable<WyUser> friends = userRepo.findAll(user.getFriends());
		friends.forEach(f -> friendList.add(UserStatVM.of(f)));
		return friendList;
		*/
		return userRepo.findAvailableFriendsNotInGroup(id)
				.stream()
				.map(UserStatVM::of)
				.collect(Collectors.toList());
	}

	public List<MeetupGroup> getGroupsWithFriends(String id) {
		WyUser user = userRepo.findOne(id);
		return meetupGroupRepo.findMeetupGroupsContaing(user.getFriends());
	}

}
