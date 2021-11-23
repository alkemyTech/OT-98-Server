package com.alkemy.ong.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SlideDetailsRequest {

  private String imageUrl;
  private int order;
  private String text;


}
