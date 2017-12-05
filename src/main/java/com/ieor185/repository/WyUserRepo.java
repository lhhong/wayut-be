package com.ieor185.repository;


import com.ieor185.model.WyUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;


@Repository
public interface WyUserRepo extends CrudRepository<WyUser, String> {

	@Query("select u from WyUser u join u.friends f where u.available = true and u.inGroup = false and f = :userId")
	List<WyUser> findAvailableFriendsNotInGroup(@Param("userId") String userId);

	@Transactional
	@Modifying
	@Query("update WyUser u set u.available = false where u.available = true and u.availableUntil < CURRENT_TIMESTAMP")
	void turnOffExpiredAvailability();

	@Transactional
	@Modifying
	@Query("update WyUser u set u.inGroup = false where u.inGroup = true and u.id in :userIds")
	void quitGroup(@Param("userIds") Collection<String> userIds);
}
