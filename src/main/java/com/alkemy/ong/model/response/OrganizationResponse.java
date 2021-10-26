package com.alkemy.ong.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrganizationResponse {

  private String name;

  private String image;

  private int phone;

  private String address;

}
