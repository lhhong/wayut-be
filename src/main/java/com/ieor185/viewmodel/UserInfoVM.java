package com.ieor185.viewmodel;

import com.ieor185.model.WyUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
    private String picture = "";

    private UserInfoVM(WyUser user) {
        id = user.getId();
        name = user.getName();
        picture = user.getPicture();
    }

    public static UserInfoVM of(WyUser user) {
        return new UserInfoVM(user);
    }

    public WyUser toEntity() {
        return new WyUser(id, name, picture);
    }
}

