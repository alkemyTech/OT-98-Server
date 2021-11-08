package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsCategoryResponse {

  private String name;
  @JsonInclude(Include.NON_NULL)
  private String description;
  @JsonInclude(Include.NON_NULL)
  private String image;
}
