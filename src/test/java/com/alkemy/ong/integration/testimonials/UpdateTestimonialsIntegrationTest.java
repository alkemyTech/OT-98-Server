package com.alkemy.ong.integration.testimonials;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import java.util.Optional;
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
import com.alkemy.ong.model.request.TestimonialDetailsRequest;
import com.alkemy.ong.model.request.NewsDetailsRequest;
import com.alkemy.ong.model.response.CreateTestimonialResponse;
import com.alkemy.ong.model.response.ErrorResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateTestimonialsIntegrationTest extends AbstractBaseTestimonialsIntegrationTest {

  private final Long ID_TO_UPDATE = stubTestimonial().getId();
  private final String PATH = "/testimonials/" + ID_TO_UPDATE;

  @Test
  public void shouldReturnForbbidenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH), HttpMethod.PUT,
        new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenAnAttributeIsNull() {
    login(ApplicationRole.ADMIN.getFullRoleName());

    TestimonialDetailsRequest updateTestimonialRequest = exampleTestimonialRequest();
    updateTestimonialRequest.setName("");
    updateTestimonialRequest.setContent("");

    HttpEntity<TestimonialDetailsRequest> entity =
        new HttpEntity<>(updateTestimonialRequest, headers);
    ResponseEntity<ErrorResponse> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.PUT, entity, ErrorResponse.class);

    String emptyName = "The name attribute must not be empty.";
    String emptyContent = "The content attribute must not be empty.";

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertTrue(response.getBody().getMessage().contains(emptyName));
    assertTrue(response.getBody().getMessage().contains(emptyContent));
  }

  @Test
  public void shouldReturnBadRequestWhenAnAttributeExceedsMaxSize() {
    login(ApplicationRole.ADMIN.getFullRoleName());

    TestimonialDetailsRequest updateTestimonialRequest = exampleTestimonialRequest();
    String invalidAttribute = StringUtils.repeat("A", 251);
    updateTestimonialRequest.setName(invalidAttribute);
    updateTestimonialRequest.setContent(invalidAttribute);

    HttpEntity<TestimonialDetailsRequest> entity =
        new HttpEntity<>(updateTestimonialRequest, headers);
    ResponseEntity<ErrorResponse> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.PUT, entity, ErrorResponse.class);

    String invalidNameResponse = "The name attribute must not be more than 250 characters.";
    String invalidContentResponse = "The content attribute must not be more than 250 characters.";

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertTrue(response.getBody().getMessage().contains(invalidNameResponse));
    assertTrue(response.getBody().getMessage().contains(invalidContentResponse));
  }


  @Test
  public void shouldReturnNotFoundWhenIdNotExist() {
    when(testimonialRepository.findById(eq(ID_TO_UPDATE))).thenReturn(Optional.empty());

    login(ApplicationRole.ADMIN.getFullRoleName());

    TestimonialDetailsRequest updateTestimonialRequest = exampleTestimonialRequest();

    HttpEntity<TestimonialDetailsRequest> entity =
        new HttpEntity<>(updateTestimonialRequest, headers);
    ResponseEntity<ErrorResponse> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.PUT, entity, ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    assertEquals(response.getBody().getMessage(), "Testimonial not found.");
  }

  @Test
  public void shouldCreateATestimonialSuccessful() {
    Testimonial editedTestimonial = stubTestimonial();
    editedTestimonial.setName("Example");
    editedTestimonial.setContent("Example");
    editedTestimonial.setImage("Example.png");

    when(testimonialRepository.findById(eq(ID_TO_UPDATE)))
        .thenReturn(Optional.of(stubTestimonial()));
    when(testimonialRepository.save(isA(Testimonial.class))).thenReturn(editedTestimonial);

    login(ApplicationRole.ADMIN.getFullRoleName());

    TestimonialDetailsRequest updateTestimonialRequest = exampleTestimonialRequest();
    updateTestimonialRequest.setName("Example");
    updateTestimonialRequest.setContent("Example");
    updateTestimonialRequest.setImage("Example.png");

    HttpEntity<TestimonialDetailsRequest> entity =
        new HttpEntity<>(updateTestimonialRequest, headers);
    ResponseEntity<CreateTestimonialResponse> response = restTemplate
        .exchange(createURLWithPort(PATH), HttpMethod.PUT, entity, CreateTestimonialResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody().getName(), updateTestimonialRequest.getName());
    assertEquals(response.getBody().getContent(), updateTestimonialRequest.getContent());
    assertEquals(response.getBody().getImage(), updateTestimonialRequest.getImage());
  }
}
