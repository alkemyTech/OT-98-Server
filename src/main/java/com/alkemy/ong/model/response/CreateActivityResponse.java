package com.alkemy.ong.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateActivityResponse {

  private long id;
  private String name;
  private String content;
  private String image;
}
