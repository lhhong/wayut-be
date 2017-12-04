package com.ieor185.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Set;

/**
 * Created by low on 4/12/17 8:45 AM.
 */
@Entity
@Getter
@Setter
public class MeetupGroup {

	@Id
	@GeneratedValue
	private long id;

	@ElementCollection
	private Set<String> users;
}
