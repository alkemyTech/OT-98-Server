package com.alkemy.ong.integration.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CreateCategoryRequest;
import com.alkemy.ong.model.response.CreateCategoryResponse;
import com.alkemy.ong.model.response.ErrorResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateCategoryIntegrationTest extends AbstractBaseCategoryIntegrationTest {

  private final String PATH = "/categories";

  @Test
  public void shouldReturnForbiddenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, new HttpEntity<>(headers), Object.class);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  public void shouldReturnBadRequestWhenAnAttributeIsNull() {
    login(ApplicationRole.ADMIN.getFullRoleName());

    CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();

    HttpEntity<CreateCategoryRequest> entity = new HttpEntity<>(createCategoryRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, ErrorResponse.class);

    String emptyName = "The name attribute must not be empty.";

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody().getMessage().contains(emptyName));
  }

  @Test
  public void shouldCreateACategorySuccessfully() {
    when(categoryRepository.save(isA(Category.class))).thenReturn(stubCategory());

    login(ApplicationRole.ADMIN.getFullRoleName());

    CreateCategoryRequest createCategoryRequest = exampleCategoryRequest();

    HttpEntity<CreateCategoryRequest> entity = new HttpEntity<>(createCategoryRequest, headers);
    ResponseEntity<CreateCategoryResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, CreateCategoryResponse.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(createCategoryRequest.getName(), response.getBody().getName());

  }

}
