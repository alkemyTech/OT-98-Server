package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@JsonRootName("category")
@Getter
@Setter
public class CategoryCreationResponse {

  private long id;

  private String name;

}
