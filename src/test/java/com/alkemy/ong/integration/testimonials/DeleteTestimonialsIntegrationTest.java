package com.alkemy.ong.integration.testimonials;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.response.ErrorResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteTestimonialsIntegrationTest extends AbstractBaseTestimonialsIntegrationTest {

  private final Long ID_TO_DELETE = stubTestimonial().getId();
  private final String PATH = "/testimonials/" + ID_TO_DELETE;

  @Test
  public void shouldReturnForbiddenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.DELETE, new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnNotFoundWhenIdNotExist() {
    when(testimonialRepository.findById(eq(stubTestimonial().getId())))
        .thenReturn(Optional.empty());

    login(ApplicationRole.ADMIN.getFullRoleName());

    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.DELETE, new HttpEntity<>(headers), ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    assertEquals(response.getBody().getMessage(), "Testimonial not found.");
  }

  @Test
  public void shouldSoftDeleteATestimonialSuccessful() {
    when(testimonialRepository.findById(eq(stubTestimonial().getId())))
        .thenReturn(Optional.of(stubTestimonial()));
    when(testimonialRepository.save(eq(stubTestimonial()))).thenReturn(stubTestimonial());

    login(ApplicationRole.ADMIN.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.DELETE, new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
  }
}
