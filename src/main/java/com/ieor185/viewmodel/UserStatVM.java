package com.ieor185.viewmodel;

import com.ieor185.model.WyUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

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

    private boolean available = false;
    private Date availableUntil = null;
    private String availableFor = "";

    private UserStatVM(WyUser user) {
        id = user.getId();
        name = user.getName();
        picture = user.getPicture();

        available = user.isAvailable();
        availableUntil = user.getAvailableUntil();
        availableFor = user.getAvailableFor();
    }

    public static UserStatVM of(WyUser user) {
        return new UserStatVM(user);
    }

    public WyUser toEntity() {
        WyUser user = new WyUser();
        user.setId(id);
        user.setName(name);
        user.setPicture(picture);
        return user;
    }
}

