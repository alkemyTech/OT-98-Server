package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.repository.ITestimonialRepository;
import com.alkemy.ong.service.abstraction.IListTestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.alkemy.ong.model.request.CreateTestimonialRequest;
import com.alkemy.ong.service.abstraction.ICreateTestimonialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestimonialServiceImpl implements ICreateTestimonialService, IListTestimonialsService {

  @Autowired
  private ITestimonialRepository testimonialRepository;

  @Transactional
  @Override
  public Testimonial create(CreateTestimonialRequest createTestimonialRequest) {
    Testimonial testimonial = new Testimonial();
    testimonial.setName(createTestimonialRequest.getName());
    testimonial.setImage(createTestimonialRequest.getImage());
    testimonial.setContent(createTestimonialRequest.getContent());
    testimonial.setSoftDelete(false);
    return testimonialRepository.save(testimonial);

  }

  @Override
  public Page<Testimonial> getList(int numberpage) {
    Pageable page = PageRequest.of(numberpage, 10);
    Page<Testimonial> pageTestimonial = testimonialRepository.findBySoftDeleteIsFalse(page);
    return pageTestimonial;
  }

}
