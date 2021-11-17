package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
public class NewsDetailsResponse {

  @JsonInclude(Include.NON_NULL)
  private Long id;
  private String name;
  private String content;
  private String image;
  @JsonInclude(Include.NON_NULL)
  private String category;
  @JsonInclude(Include.NON_NULL)
  private NewsCategoryResponse newsCategory;

}
