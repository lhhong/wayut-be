package com.ieor185.service;

import com.ieor185.repository.UserStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by low on 4/12/17 1:46 AM.
 */
@Service
public class StatusService {

	private static final Logger logger = LoggerFactory.getLogger(StatusService.class);

	@Autowired
	UserStatusRepository userStatusRepository;

	public void setStatus() {

	}
}
