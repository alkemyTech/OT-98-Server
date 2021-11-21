package com.alkemy.ong.integration.testimonials;

import org.springframework.boot.test.mock.mockito.MockBean;
import com.alkemy.ong.common.AbstractBaseIntegrationTest;
import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.request.CreateTestimonialRequest;
import com.alkemy.ong.repository.ITestimonialRepository;

public abstract class AbstractBaseTestimonialsIntegrationTest extends AbstractBaseIntegrationTest {

  @MockBean
  protected ITestimonialRepository testimonialRepository;

  protected Testimonial stubTestimonial() {
    return new Testimonial(1L, "Example", "Example.png", "Example", null, false);
  }

  protected CreateTestimonialRequest exampleTestimonialRequest() {
    CreateTestimonialRequest createTestimonialRequest = new CreateTestimonialRequest();
    createTestimonialRequest.setName("Example");
    createTestimonialRequest.setImage("Example.png");
    createTestimonialRequest.setContent("Example");
    return createTestimonialRequest;
  }
}
