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

  @NotBlank
  @Size(max = 250)
  private String firstName;

  @NotBlank
  @Size(max = 250)
  private String lastName;

  @Email
  @NotBlank
  @Size(max = 250)
  private String email;

  @NotBlank
  @Size(min = 4, max = 250)
  private String password;

}
