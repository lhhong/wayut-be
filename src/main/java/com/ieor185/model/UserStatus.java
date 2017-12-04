package com.ieor185.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by low on 3/12/17 8:58 PM.
 */
@Entity
@Getter
@Setter
@Table(name = "user_status")
public class UserStatus {

	@Id
	private String id;

	private boolean available = false;
	private Date availableUntil = null;
	private String availableFor = "";
}
