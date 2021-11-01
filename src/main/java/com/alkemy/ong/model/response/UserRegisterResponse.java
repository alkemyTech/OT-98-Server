package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonRootName("user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponse {

  private long id;

  private String firstsName;

  private String lastName;

  private String email;

  private String jwt;

}
