package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@JsonRootName("user")
@Getter
@Setter
public class UserRegisterResponse {

  private long id;

  private String firstsName;

  private String lastName;

  private String email;

}
