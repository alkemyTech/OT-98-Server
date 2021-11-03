package com.alkemy.ong.model.response;

import com.alkemy.ong.model.entity.Category;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonRootName("news")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsResponse {

  private String name;
  private String content;
  private String image;
  private String nameCategory;
}
