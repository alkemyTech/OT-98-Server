package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.request.TestimonialDetailsRequest;

public interface ICreateTestimonialService {

  Testimonial create(TestimonialDetailsRequest createTestimonialRequest);

}
