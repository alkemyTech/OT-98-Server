package com.alkemy.ong.integration.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CategoryUpdateRequest;
import com.alkemy.ong.model.request.CreateCategoryRequest;
import com.alkemy.ong.model.response.DetailsCategoryResponse;
import com.alkemy.ong.model.response.ErrorResponse;
import java.util.Optional;
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
public class UpdateCategoryIntegrationTest extends AbstractBaseCategoryIntegrationTest {

  private final Long ID_TO_UPDATE = stubCategory().getId();
  private final String PATH = "/categories/" + ID_TO_UPDATE;

  @Test
  public void shouldReturnForbiddenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH), HttpMethod.PUT,
        new HttpEntity<>(headers), Object.class);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  public void shouldReturnBadRequestWhenAnAttributeIsNull() {
    login(ApplicationRole.ADMIN.getFullRoleName());

    CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();

    HttpEntity<CategoryUpdateRequest> entity = new HttpEntity<>(categoryUpdateRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.PUT, entity, ErrorResponse.class);

    String emptyName = "The name attribute must not be empty.";

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody().getMessage().contains(emptyName));

  }

  @Test
  public void shouldReturnNotFoundWhenIdDoesNotExist() {
    when(categoryRepository.findById(eq(ID_TO_UPDATE))).thenReturn(Optional.empty());

    login(ApplicationRole.ADMIN.getFullRoleName());

    CreateCategoryRequest createCategoryRequest = exampleCategoryRequest();

    HttpEntity<CreateCategoryRequest> entity = new HttpEntity<>(createCategoryRequest, headers);
    ResponseEntity<ErrorResponse> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.PUT, entity, ErrorResponse.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("The requested resource could not be found.", response.getBody().getMessage());
  }

  @Test
  public void shouldUpdateCategorySuccessfully() {
    Category editedCategory = stubCategory();
    editedCategory.setName("nameEdited");
    editedCategory.setImage("imageEdited.png");
    editedCategory.setDescription("descEdited");

    when(categoryRepository.getById(eq(ID_TO_UPDATE))).thenReturn(stubCategory());
    when(categoryRepository.save(eq(editedCategory))).thenReturn(editedCategory);

    login(ApplicationRole.ADMIN.getFullRoleName());

    CategoryUpdateRequest categoryUpdateRequest = updateCategoryRequest();
    categoryUpdateRequest.setName("nameEdited");
    categoryUpdateRequest.setDescription("descEdited");
    categoryUpdateRequest.setImage("imageEdited.png");

    HttpEntity<CategoryUpdateRequest> entity = new HttpEntity<>(categoryUpdateRequest, headers);
    ResponseEntity<DetailsCategoryResponse> response = restTemplate.exchange(
        createURLWithPort(PATH),
        HttpMethod.PUT, entity, DetailsCategoryResponse.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(categoryUpdateRequest.getName(), response.getBody().getName());
    assertEquals(categoryUpdateRequest.getImage(), response.getBody().getImage());
    assertEquals(categoryUpdateRequest.getDescription(), response.getBody().getDescription());
  }

}
