package com.alkemy.ong.model.request;

import com.alkemy.ong.model.entity.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
  @Size(min = 4, max = 250, message = "min error")
  private String password;

  private String photo;

  @NotNull
  private Role role;

}
