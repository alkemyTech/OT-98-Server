package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@JsonRootName("user")
@Getter
@Setter
public class ResponseUser {

  private long id;

  private String firstsName;

  private String lastName;

  private String email;

  private String photo;

  private Timestamp timestamp;

}
