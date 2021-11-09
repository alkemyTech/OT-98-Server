package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.response.ListTestimonialResponse;
import com.alkemy.ong.model.response.TestimonialResponse;
import com.alkemy.ong.repository.ITestimonialRepository;
import com.alkemy.ong.service.abstraction.IListTestimonialsService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestimonialServiceImpl implements IListTestimonialsService {

 @Autowired
 private ITestimonialRepository testimonialRepository;

  @Override
  @Transactional
  public List<TestimonialResponse> testimonials(Pageable pageable) {

    Page<Testimonial> page = testimonialRepository.findAll(pageable);
    List<Testimonial> testimonials = page.getContent();

    TestimonialResponse testimonialResponse;
    List<TestimonialResponse> testimonialsResponse = new ArrayList<>();
    for (Testimonial testimonial : testimonials){

      testimonialResponse = new TestimonialResponse();
      testimonialResponse.setId(testimonial.getId());
      testimonialResponse.setContent(testimonial.getContent());
      testimonialResponse.setImage(testimonial.getImage());
      testimonialResponse.setName(testimonial.getName());

      testimonialsResponse.add(testimonialResponse);

    }
    return testimonialsResponse;
  }
}
