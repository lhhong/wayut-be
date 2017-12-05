package com.ieor185.service;

import com.ieor185.model.MeetupGroup;
import com.ieor185.model.WyUser;
import com.ieor185.repository.MeetupGroupRepo;
import com.ieor185.repository.WyUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by low on 4/12/17 9:29 AM.
 */
@Service
public class GroupsService {

	private static final Logger logger = LoggerFactory.getLogger(GroupsService.class);

	@Autowired
	MeetupGroupRepo meetupGroupRepo;

	@Autowired
	WyUserRepo userRepo;

	@Transactional
	public void joinGroup(long groupId, String userId) {
		MeetupGroup group = meetupGroupRepo.findOne(groupId);
		WyUser user = userRepo.findOne(userId);
		group.addMember(user);
		if (user.getAvailableUntil().before(group.getAvailableUntil())) {
			group.setAvailableUntil(user.getAvailableUntil());
		}
		meetupGroupRepo.save(group);
	}

	@Transactional
	public void formGroup(String originalUser, String user2) {
		MeetupGroup group = new MeetupGroup();
		WyUser origin = userRepo.findOne(originalUser);
		WyUser adder = userRepo.findOne(user2);
		group.addMember(origin);
		group.addMember(adder);
		group.setActivity(origin.getAvailableFor());
		group.setAvailableUntil(
				minDate(origin.getAvailableUntil(), adder.getAvailableUntil()));
		meetupGroupRepo.save(group);
	}

	private Date minDate(Date date1, Date date2) {
		return date1.before(date2) ? date1 : date2;
	}
}
