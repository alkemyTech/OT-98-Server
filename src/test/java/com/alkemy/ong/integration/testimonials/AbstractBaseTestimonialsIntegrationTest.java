package com.alkemy.ong.integration.testimonials;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.alkemy.ong.common.AbstractBaseIntegrationTest;
import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.request.TestimonialDetailsRequest;
import com.alkemy.ong.repository.ITestimonialRepository;

public abstract class AbstractBaseTestimonialsIntegrationTest extends AbstractBaseIntegrationTest {

  @MockBean
  protected ITestimonialRepository testimonialRepository;

  protected Testimonial stubTestimonial() {
    return new Testimonial(1L, "Example", "Example.png", "Example", null, false);
  }

  protected TestimonialDetailsRequest exampleTestimonialRequest() {
    TestimonialDetailsRequest createTestimonialRequest = new TestimonialDetailsRequest();
    createTestimonialRequest.setName("Example");
    createTestimonialRequest.setImage("Example.png");
    createTestimonialRequest.setContent("Example");
    return createTestimonialRequest;
  }

  protected List<Testimonial> stubTestimonial(int count) {
    List<Testimonial> testimonials = new ArrayList<>(count);
    for (int i = 1; i <= count; i++) {
      testimonials.add(new Testimonial(i, "Example", "Example.png", "Example", null, false));
    }

    return testimonials;
  }
}
