package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@JsonRootName("organization")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponse {

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

  private List<DetailsSlideResponse> slides;

  public String getName() {
    return name;
  }

  public String getImage() {
    return image;
  }

  public int getPhone() {
    return phone;
  }

  public String getAddress() {
    return address;
  }

  public String getFacebookUrl() {
    return facebookUrl;
  }

  public String getLinkedinUrl() {
    return linkedinUrl;
  }

  public String getInstagramUrl() {
    return instagramUrl;
  }
}
