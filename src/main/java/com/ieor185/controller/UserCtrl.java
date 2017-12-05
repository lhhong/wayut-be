package com.ieor185.controller;

import com.ieor185.service.WyUserService;
import com.ieor185.viewmodel.UserInfoVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by low on 4/12/17 1:51 AM.
 */
@RestController
@RequestMapping(value = "/user")
public class UserCtrl {

	private static final Logger logger = LoggerFactory.getLogger(UserCtrl.class);

	@Autowired
	private WyUserService userService;

	@PostMapping(value="login")
	@ResponseBody
	public String login(@RequestBody UserInfoVM user) {
		logger.info("logging in: {}", user.getName());
		userService.login(user);
		return "Done";
	}

	@PostMapping(value="update_friends/{userId}")
	@ResponseBody
	public String updateFriends(@PathVariable("userId") String userId, @RequestBody List<String> friends) {
		logger.info("updating friends for {}", userId);
		userService.updateFriends(userId, friends);
		return "Done";
	}
}
