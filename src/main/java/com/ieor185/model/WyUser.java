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
	private String picture = "";

	@ElementCollection
	private Set<String> friends = new HashSet<>();

	private boolean available = false;
	private Date availableUntil = null;
	private String availableFor = "";

	public WyUser(String id, String name, String picture) {
		this.id = id;
		this.name = name;
		this.picture = picture;
	}

	public void addFriends(Collection<String> ids) {
		friends.addAll(ids);
	}
	public void addFriend(String id) {
		friends.add(id);
	}
}
