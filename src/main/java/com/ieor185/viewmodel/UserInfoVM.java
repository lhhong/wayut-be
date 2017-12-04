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
public class UserInfoVM implements Serializable {

    private String id = "";
    private String name = "";
    private String photo = "";

    private UserInfoVM(WyUser user) {
        id = user.getId();
        name = user.getName();
        photo = user.getPhoto();
    }

    public static UserInfoVM of(WyUser user) {
        return new UserInfoVM(user);
    }

    public WyUser toEntity() {
        WyUser user = new WyUser();
        user.setId(id);
        user.setName(name);
        user.setPhoto(photo);
        return user;
    }
}

