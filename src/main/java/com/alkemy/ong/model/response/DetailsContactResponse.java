package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName("contact")
public class DetailsContactResponse {

  private long id;
  private String name;
  private String phone;
  private String email;
  private String message;
  private Date delete_at;

}
