package com.alkemy.ong.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateContactResponse {

  private long id;
  private String name;
  private String phone;
  private String email;
  private String message;

}
