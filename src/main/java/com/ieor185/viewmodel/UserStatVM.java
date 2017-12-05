package com.ieor185.viewmodel;

import com.ieor185.model.UserStatus;
import com.ieor185.model.WyUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Nick on 23-Jun-17.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStatVM implements Serializable {

    private String id = "";
    private String name = "";
    private String picture = "";

    private Set<String> friends = null;

    private boolean available = false;
    private Date availableUntil = null;
    private String availableFor = "";

    private UserStatVM(WyUser user, UserStatus status) {
        id = user.getId();
        name = user.getName();
        picture = user.getPicture();
        friends = user.getFriends();

        available = status.isAvailable();
        availableUntil = status.getAvailableUntil();
        availableFor = status.getAvailableFor();
    }

    public static UserStatVM of(WyUser user, UserStatus status) {
        return new UserStatVM(user, status);
    }

    public WyUser toEntity() {
        WyUser user = new WyUser();
        user.setId(id);
        user.setName(name);
        user.setPicture(picture);
        return user;
    }
}

