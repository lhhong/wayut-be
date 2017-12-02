package com.ieor185.user.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by low on 27/6/17 6:04 PM.
 */
@Getter
@Setter
public class SignupForm {

   @NotNull
   @Email
   @Size(min=1, max=127)
   private String email;

   @NotNull
   @Size(min=3, max=30)
   private String displayName;

   @Size(max=40)
   private String description;

   @NotNull
	@Size(min=8, max=16)
   private String password;

   @NotNull
   @Size(min=8, max=16)
   private String passwordConfirm;
}
