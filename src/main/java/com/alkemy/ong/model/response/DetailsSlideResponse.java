package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonRootName("slides")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailsSlideResponse {

  private String image;
  private Integer order;
}
