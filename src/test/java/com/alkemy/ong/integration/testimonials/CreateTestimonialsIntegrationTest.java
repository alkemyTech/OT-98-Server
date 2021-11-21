package com.alkemy.ong.integration.testimonials;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.request.CreateTestimonialRequest;
import com.alkemy.ong.model.response.CreateTestimonialResponse;
import com.alkemy.ong.model.response.ErrorResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateTestimonialsIntegrationTest extends AbstractBaseTestimonialsIntegrationTest {

  private final String PATH = "/testimonials";

  @Test
  public void shouldReturnForbbidenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenAnAttributeIsNull() {
    login(ApplicationRole.ADMIN.getFullRoleName());

    CreateTestimonialRequest createTestimonialRequest = exampleTestimonialRequest();
    createTestimonialRequest.setName("");
    createTestimonialRequest.setContent("");

    HttpEntity<CreateTestimonialRequest> entity =
        new HttpEntity<>(createTestimonialRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, ErrorResponse.class);

    String emptyName = "The name attribute must not be empty.";
    String emptyContent = "The content attribute must not be empty.";

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertTrue(response.getBody().getMessage().contains(emptyName));
    assertTrue(response.getBody().getMessage().contains(emptyContent));
  }

  @Test
  public void shouldReturnBadRequestWhenAnAttributeExceedsMaxSize() {
    login(ApplicationRole.ADMIN.getFullRoleName());

    CreateTestimonialRequest createTestimonialRequest = exampleTestimonialRequest();
    String invalidAttribute = StringUtils.repeat("A", 251);
    createTestimonialRequest.setName(invalidAttribute);
    createTestimonialRequest.setContent(invalidAttribute);

    HttpEntity<CreateTestimonialRequest> entity =
        new HttpEntity<>(createTestimonialRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, ErrorResponse.class);

    String invalidNameResponse = "The name attribute must not be more than 250 characters.";
    String invalidContentResponse = "The content attribute must not be more than 250 characters.";

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertTrue(response.getBody().getMessage().contains(invalidNameResponse));
    assertTrue(response.getBody().getMessage().contains(invalidContentResponse));
  }

  @Test
  public void shouldCreateATestimonialSuccessful() {
    when(testimonialRepository.save(isA(Testimonial.class))).thenReturn(stubTestimonial());

    login(ApplicationRole.ADMIN.getFullRoleName());

    CreateTestimonialRequest createTestimonialRequest = exampleTestimonialRequest();

    HttpEntity<CreateTestimonialRequest> entity =
        new HttpEntity<>(createTestimonialRequest, headers);
    ResponseEntity<CreateTestimonialResponse> response = restTemplate.exchange(
        createURLWithPort(PATH), HttpMethod.POST, entity, CreateTestimonialResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    assertEquals(response.getBody().getName(), createTestimonialRequest.getName());
    assertEquals(response.getBody().getImage(), createTestimonialRequest.getImage());
    assertEquals(response.getBody().getContent(), createTestimonialRequest.getContent());
  }
}
