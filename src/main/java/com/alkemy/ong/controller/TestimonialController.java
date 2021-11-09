package com.alkemy.ong.controller;

import com.alkemy.ong.config.pagination.PaginatedResultsRetrievedEvent;
import com.alkemy.ong.config.pagination.PaginationTestimonialConfig;
import com.alkemy.ong.model.response.ListTestimonialResponse;
import com.alkemy.ong.model.response.TestimonialResponse;
import com.alkemy.ong.service.abstraction.IListTestimonialsService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/testimonial")
public class TestimonialController {

  @Autowired
  private IListTestimonialsService testimonialsService;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private PaginationTestimonialConfig paginationTestimonialConfig;

  @GetMapping
  public ResponseEntity<?> listTestimonials(@PageableDefault(size = 10) Pageable pageable, UriComponentsBuilder uriBuilder,
      HttpServletResponse response){

    List<TestimonialResponse> listTestimonials = testimonialsService.testimonials(pageable);

    applicationEventPublisher.publishEvent(
        new PaginatedResultsRetrievedEvent<TestimonialResponse>(TestimonialResponse.class, uriBuilder, response,
            pageable.getPageNumber(), 8, 10));


    return new ResponseEntity<>(listTestimonials, HttpStatus.OK);

  }

}
