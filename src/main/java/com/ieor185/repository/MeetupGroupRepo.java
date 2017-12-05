package com.ieor185.repository;


import com.ieor185.model.MeetupGroup;
import com.ieor185.model.WyUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface MeetupGroupRepo extends CrudRepository<MeetupGroup, String> {

	@Query("select g from MeetupGroup g join g.members m where m.id in :memberList")
	List<MeetupGroup> findMeetupGroupsContaing(Collection<String> memberList);
}
