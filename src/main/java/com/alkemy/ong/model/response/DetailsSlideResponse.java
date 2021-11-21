package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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

  @JsonInclude(Include.NON_NULL)
  private String text;
  private Integer order;

  @JsonInclude(Include.NON_NULL)
  private SlideOrganizationResponse organization;

  public DetailsSlideResponse(String image, Integer order) {
    this.image = image;
    this.order = order;
  }
}
