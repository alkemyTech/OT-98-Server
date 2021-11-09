package com.alkemy.ong.model.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestimonialResponse implements Serializable {

  private long id;
  private String content;
  private String name;
  private String image;

}
