package com.alkemy.ong.integration.category;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.config.ApplicationRole;
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
public class GetCategoryIntegrationTest extends AbstractBaseCategoryIntegrationTest {

  private final Long ID_TO_GET = stubCategory().getId();
  private final String PATH = "/categories/" + ID_TO_GET;

  @Test
  public void shouldReturnForbiddenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH), HttpMethod.GET,
        new HttpEntity<>(headers), Object.class);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  public void shouldReturnNotFoundWhenIdNotExist() {
    when(categoryRepository.findById(eq(ID_TO_GET))).thenReturn(Optional.empty());

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<DetailsCategoryResponse> entity = new HttpEntity<>(headers);
    ResponseEntity<ErrorResponse> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.GET, entity, ErrorResponse.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("The requested resource could not be found.", response.getBody().getMessage());
  }

  @Test
  public void shouldGetCategorySuccessful() {
    when(categoryRepository.getById(eq(ID_TO_GET))).thenReturn(stubCategory());

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<DetailsCategoryResponse> entity = new HttpEntity<>(headers);
    ResponseEntity<DetailsCategoryResponse> response = restTemplate.exchange(
        createURLWithPort(PATH),
        HttpMethod.GET, entity, DetailsCategoryResponse.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(stubCategory().getId(), response.getBody().getId());
    assertEquals(stubCategory().getName(), response.getBody().getName());
    assertEquals(stubCategory().getDescription(), response.getBody().getDescription());
    assertEquals(stubCategory().getImage(), response.getBody().getImage());
  }

}
