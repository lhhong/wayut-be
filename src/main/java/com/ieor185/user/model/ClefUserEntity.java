package com.ieor185.user.model;


import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by xschen on 16/10/2016.
 */
@Entity
@Table(name = "clef_users", indexes = {
        @Index(name="usernameIndex", columnList = "username", unique = true),
        @Index(name="userEmailIndex", columnList = "email", unique = false)
})
public class ClefUserEntity implements ClefUser {
   @Id
   @GeneratedValue
   private long id;

   private String username = "";
   private String password = "";

   private String email = "";
   private String roles = "ROLE_USER";

   private String displayName = "";
   private String description = "";

   private String imageUrl = "";

   private String authenticationSource = "";

   private boolean enabled = true;

   private long createdBy;

   private long lastUpdatedBy;

   private int rank = 1;

   @Override
   public String getDescription() {
      return description;
   }

   @Override
   public void setDescription(String description) {
      this.description = description;
   }

   @Override
   public String getImageUrl() {
      return imageUrl;
   }

   @Override
   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }

   @Temporal(TemporalType.TIMESTAMP)
   private Date createdTime;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updatedTime;

   public ClefUserEntity(ClefUser user) {
      copy(user);
   }

   public ClefUserEntity() {

   }


   @Override
   public String getAuthenticationSource() {
      return authenticationSource;
   }

   @Override
   public void setAuthenticationSource(String authenticationSource) {
      this.authenticationSource = authenticationSource;
   }

   @Override public boolean isSuperUser() {
      return roles.contains("ROLE_ADMIN");
   }

   @Override public boolean isValid(){
      return !StringUtils.isEmpty(username);
   }

   @Override public void setCreatedTime(Date createdTime) {
      if(createdTime != null) {
         this.createdTime = new Date(createdTime.getTime());
      } else {
         this.createdTime = null;
      }
   }


   @Override public Date getCreatedTime() {
      if(createdTime != null){
         return new Date(createdTime.getTime());
      }
      return null;
   }



   @Override public void setUpdatedTime(Date updatedTime) {
      if(updatedTime != null) {
         this.updatedTime = new Date(updatedTime.getTime());
      } else {
         this.updatedTime =null;
      }
   }


   @Override public Date getUpdatedTime() {
      if(updatedTime != null){
         return new Date(updatedTime.getTime());
      }
      return null;
   }


   @Override public long getId() {
      return id;
   }


   @Override public void setId(long id) {
      this.id = id;
   }


   @Override public String getUsername() {
      return username;
   }


   @Override public void setUsername(String username) {
      this.username = username;
   }


   @Override public String getPassword() {
      return password;
   }


   @Override public void setPassword(String password) {
      this.password = password;
   }


   @Override public String getEmail() {
      return email;
   }


   @Override public void setEmail(String email) {
      this.email = email;
   }


   @Override public String getRoles() {
      return roles;
   }


   @Override public void setRoles(String roles) {
      this.roles = roles;
   }


   @Override public String getDisplayName() {
      return displayName;
   }


   @Override public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }


   @Override public long getCreatedBy() {
      return createdBy;
   }


   @Override public void setCreatedBy(long createdBy) {
      this.createdBy = createdBy;
   }


   @Override public long getLastUpdatedBy() {
      return lastUpdatedBy;
   }


   @Override public void setLastUpdatedBy(long lastUpdatedBy) {
      this.lastUpdatedBy = lastUpdatedBy;
   }


   @Override public void setEnabled(boolean enabled) { this.enabled = enabled; }

   @Override public boolean isEnabled() { return enabled; }

   @Override
   public int getRank() {
      return rank;
   }

   @Override
   public void setRank(int rank) {
      this.rank = rank;
   }
}
