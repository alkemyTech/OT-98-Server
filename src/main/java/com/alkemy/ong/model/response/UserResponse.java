package com.alkemy.ong.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String photo;

}
