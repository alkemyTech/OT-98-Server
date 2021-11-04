package com.alkemy.ong.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTestimonialResponse {

  private long id;
  private String name;
  private String content;

}
