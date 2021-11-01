package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@JsonRootName("category")
@Getter
@Setter
public class CreateCategoryResponse {

  private long id;

  private String name;

}
