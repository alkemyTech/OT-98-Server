package com.alkemy.ong.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RequestUser {

  @NotBlank(message = "This field must not be empty")
  @Size(max = 250, message = "max error size")
  private String firstName;

  @NotBlank(message = "This field must not be empty")
  @Size(max = 250, message = "max error size")
  private String lastName;

  @Email(message = "invalid email")
  @NotBlank(message = "This field must not be empty")
  @Size(max = 250, message = "max error size")
  private String email;

  @NotBlank(message = "This field must not be empty")
  @Size(min = 4, message = "min error")
  private String password;
  
}
