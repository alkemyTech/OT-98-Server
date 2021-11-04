package com.alkemy.ong.controller;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.request.CreateTestimonialRequest;
import com.alkemy.ong.model.response.CreateTestimonialResponse;
import com.alkemy.ong.repository.ITestimonialRepository;
import com.alkemy.ong.service.abstraction.ICreateTestimonialService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

  @RestController
  @RequestMapping("/testimonials")
  public class TestimonialController {
  @Autowired
  private ICreateTestimonialService createTestimonialService;

  @Autowired
  private ConvertUtils convertUtils;

  @PostMapping(value = "",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)

  public ResponseEntity<CreateTestimonialResponse> create(
      @Valid @RequestBody CreateTestimonialRequest createTestimonialRequest){
      Testimonial testimonial = createTestimonialService.create(createTestimonialRequest);
      CreateTestimonialResponse createTestimonialResponse = this.convertUtils.toResponse(testimonial);
      return  new ResponseEntity<>(createTestimonialResponse,HttpStatus.CREATED);

  }

}
