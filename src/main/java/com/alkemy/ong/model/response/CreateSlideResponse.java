package com.alkemy.ong.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSlideResponse {

  private long id;
  private String imageUrl;
  private String text;
  private int order;
  private int organizationId;

}
