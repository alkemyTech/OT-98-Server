package com.alkemy.ong.model.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListTestimonialResponse {

  private List<TestimonialResponse> testimonials;

}
