package com.alkemy.ong.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateActivityResponse {

  private long id;
  private String name;
  private String content;
  private String image;

}
