package com.alkemy.ong.model.response;

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
public class GetByIdNewsResponse {

  private Long id;
  private String name;
  private String content;
  private String image;
  private NewsCategoryResponse newsCategory;
}
