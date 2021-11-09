package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.request.CreateTestimonialRequest;

public interface ICreateTestimonialService {

  Testimonial create(CreateTestimonialRequest createTestimonialRequest);

}
