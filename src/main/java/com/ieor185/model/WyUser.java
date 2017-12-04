package com.ieor185.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


/**
 * Created by xschen on 16/10/2016.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wy_users")
public class WyUser {

	@Id
	private String id;

	private String name = "";
	private String photo = "";

	@ElementCollection
	private Set<String> friends = new HashSet<>();

	public void addFriends(Collection<String> ids) {
		friends.addAll(ids);
	}
	public void addFriend(String id) {
		friends.add(id);
	}
}
