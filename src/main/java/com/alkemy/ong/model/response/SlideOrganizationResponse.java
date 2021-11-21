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
public class SlideOrganizationResponse {

  private String name;

  private String image;

  private int phone;

  private String address;

  @JsonInclude(Include.NON_NULL)
  private String facebookUrl;

  @JsonInclude(Include.NON_NULL)
  private String linkedinUrl;

  @JsonInclude(Include.NON_NULL)
  private String instagramUrl;
}
