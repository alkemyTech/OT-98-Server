package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.request.CreateTestimonialRequest;

public interface IUpdateTestimonialService {

  Testimonial update(CreateTestimonialRequest testimonialRequest, Long id);
}