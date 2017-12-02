package com.ieor185.user.viewmodel;

import com.ieor185.user.model.ClefUser;
import com.ieor185.user.model.ClefUserDetails;
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
public class UserEntityViewModel implements Serializable {

    private long id;
    private String displayName;
    private String description;
    private String imageUrl;
    private long memberSince;

    private String email;

    public static UserEntityViewModel anonymousUser() {
        return new UserEntityViewModel(-1, "Anonymous User", "", "", 0L, "");
    }

    public static UserEntityViewModel of(ClefUser user) {
        return new UserEntityViewModel(user);
    }

    public UserEntityViewModel(ClefUser user) {
        setId(user.getId());
        setDisplayName(user.getDisplayName());
        setDescription(user.getDescription());
        setImageUrl(user.getImageUrl());
        setEmail("");
        setMemberSince(user.getCreatedTime().getTime());
    }


    // added by May
    public void addAttributes (ClefUser user) {
        this.id = user.getId();
        this.displayName = user.getDisplayName();
        this.description = user.getDescription();
        this.imageUrl = user.getImageUrl();
        this.memberSince = user.getCreatedTime().getTime();
    }
    public void addAttributes (ClefUserDetails user) {
        this.email = user.getEmail();
    }

}
