package com.ieor185.user.model;


import java.util.Date;


/**
 * Created by xschen on 24/12/2016.
 */
public interface ClefUser {

	String getAuthenticationSource();

	void setAuthenticationSource(String authenticationSource);

   boolean isSuperUser();

   boolean isValid();

   void setCreatedTime(Date createdTime);

   Date getCreatedTime();

   void setUpdatedTime(Date updatedTime);

   Date getUpdatedTime();

   long getId();

   void setId(long id);

   String getUsername();

   void setUsername(String username);

   String getPassword();

   void setPassword(String password);

   String getEmail();

   void setEmail(String email);

   String getRoles();

   void setRoles(String roles);

   String getDisplayName();

   void setDisplayName(String lastName);

   long getCreatedBy();

   void setCreatedBy(long createdBy);

   long getLastUpdatedBy();

   void setLastUpdatedBy(long lastUpdatedBy);

   boolean isEnabled();

   void setEnabled(boolean enabled);

   void setImageUrl(String imageUrl);

   String getImageUrl();

   void setDescription(String description);

   String getDescription();

   default void copyProfile(ClefUser rhs){
      setCreatedBy(rhs.getCreatedBy());
      setCreatedTime(rhs.getCreatedTime());
      setEmail(rhs.getEmail());
      setDisplayName(rhs.getDisplayName());
      setLastUpdatedBy(rhs.getLastUpdatedBy());
      setPassword(rhs.getPassword());
      setRoles(rhs.getRoles());
      setUpdatedTime(rhs.getUpdatedTime());
      setUsername(rhs.getUsername());
      setEnabled(rhs.isEnabled());
      setImageUrl(rhs.getImageUrl());
      setAuthenticationSource(rhs.getAuthenticationSource());
      setDescription(rhs.getDescription());
   }

   default void copy(ClefUser rhs) {
      copyProfile(rhs);
      setId(rhs.getId());
   }

   default boolean isDemoUser() {
      return getRoles().contains("ROLE_DEMO");
   }

	int getRank();

   void setRank(int rank);
}
