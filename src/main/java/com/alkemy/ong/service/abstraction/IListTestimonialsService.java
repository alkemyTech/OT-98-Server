package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.response.ListTestimonialResponse;
import com.alkemy.ong.model.response.TestimonialResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IListTestimonialsService {

  List<TestimonialResponse> testimonials(Pageable pageable);
  //ListTestimonialResponse listTestimonials(Pageable pageable);

}
