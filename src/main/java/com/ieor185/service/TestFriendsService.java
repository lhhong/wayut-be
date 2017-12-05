package com.ieor185.service;

import com.ieor185.model.WyUser;
import com.ieor185.repository.WyUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by low on 3/12/17 8:52 PM.
 */
@Service
public class TestFriendsService {

	private static final Logger logger = LoggerFactory.getLogger(TestFriendsService.class);

	private static final String ANON_PHOTO = "some url";

	@Autowired
	private WyUserRepo wyUserRepo;

	private List<WyUser> testFriends = new ArrayList<>();

	public Set<String> randomTestFriends(String id) {
		Set<String> friends = new HashSet<>();
		Random random = new Random();
		for (int i = 0; i < 15; i++) {
			int index = random.nextInt(30);
			friends.add(testFriends.get(index).getId());
		}
		return friends;
	}

	public void initialize() {
		testFriends.add(new WyUser("mock1", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock2", "Jade", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock3", "Crystal", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock4", "Jim", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock5", "Harry", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock6", "Benjamin", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock7", "Jordan", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock8", "Jackie", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock9", "Anna", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock10", "Katy", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock11", "Nicole", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock12", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock13", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock14", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock15", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock16", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock17", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock18", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock19", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock20", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock21", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock22", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock23", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock24", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock25", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock26", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock27", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock28", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock29", "Andrew", ANON_PHOTO, new HashSet<>()));
		testFriends.add(new WyUser("mock30", "Andrew", ANON_PHOTO, new HashSet<>()));

		wyUserRepo.save(testFriends);

	}
}
