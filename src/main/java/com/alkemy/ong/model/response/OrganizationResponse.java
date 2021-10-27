package com.alkemy.ong.model.response;

import lombok.Builder;

@Builder
public class OrganizationResponse {

  private String name;

  private String image;

  private int phone;

  private String address;

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
}
