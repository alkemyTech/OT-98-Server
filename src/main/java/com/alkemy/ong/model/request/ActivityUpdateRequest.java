package com.alkemy.ong.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ActivityUpdateRequest {

  private String content;
  private String image;
  private String name;

}
