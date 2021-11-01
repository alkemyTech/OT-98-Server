package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonRootName("User token")
@Getter
@Setter
@AllArgsConstructor
public class UserRegisterResponse {

  private String jwt;

}
