package com.ieor185.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by low on 4/12/17 8:45 AM.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "meetup_group")
public class MeetupGroup {

	@Id
	@GeneratedValue
	private long id;

	private String activity = "";
	private Date availableUntil = null;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<WyUser> members;
}
