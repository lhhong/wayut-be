package com.ieor185.user.viewmodel;

import com.ieor185.user.model.WyUser;
import com.ieor185.user.model.WyUserDetails;
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
public class WyUserEntityViewModel implements Serializable {

    private long id;
    private String displayName;
    private String description;
    private String imageUrl;
    private long memberSince;

    private String email;

    public static WyUserEntityViewModel anonymousUser() {
        return new WyUserEntityViewModel(-1, "Anonymous User", "", "", 0L, "");
    }

    public static WyUserEntityViewModel of(WyUser user) {
        return new WyUserEntityViewModel(user);
    }

    public WyUserEntityViewModel(WyUser user) {
        setId(user.getId());
        setDisplayName(user.getDisplayName());
        setDescription(user.getDescription());
        setImageUrl(user.getImageUrl());
        setEmail("");
        setMemberSince(user.getCreatedTime().getTime());
    }


    // added by May
    public void addAttributes (WyUser user) {
        this.id = user.getId();
        this.displayName = user.getDisplayName();
        this.description = user.getDescription();
        this.imageUrl = user.getImageUrl();
        this.memberSince = user.getCreatedTime().getTime();
    }
    public void addAttributes (WyUserDetails user) {
        this.email = user.getEmail();
    }

}
