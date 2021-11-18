package com.alkemy.ong.integration.news;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.request.NewsDetailsRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.NewsDetailsResponse;
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
public class CreateNewsIntegrationTest extends AbstractBaseNewsIntegrationTest {

  private final String PATH = "/news";

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

    NewsDetailsRequest newsDetailsRequest = new NewsDetailsRequest();

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(newsDetailsRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, ErrorResponse.class);

    String emptyName = "The name attribute must not be empty.";
    String emptyContent = "The content attribute must not be empty.";
    String emptyImage = "The image attribute must not be empty.";

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertTrue(response.getBody().getMessage().contains(emptyName));
    assertTrue(response.getBody().getMessage().contains(emptyContent));
    assertTrue(response.getBody().getMessage().contains(emptyImage));
  }

  @Test
  public void shouldReturnNotFoundWhenCategoryNewsNotExist() {
    when(newsRepository.findCategoryByName("news")).thenReturn(null);
    login(ApplicationRole.ADMIN.getFullRoleName());

    NewsDetailsRequest request = stubNewsDetailsRequest();

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(request, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    assertEquals(response.getBody().getMessage(), "News category not found.");
  }

  @Test
  public void shouldCreateANewsSuccessful() {
    when(newsRepository.findCategoryByName(eq("news"))).thenReturn(stubNewsCategory());
    when(newsRepository.save(isA(News.class))).thenReturn(stubNews());

    login(ApplicationRole.ADMIN.getFullRoleName());

    NewsDetailsRequest newsDetailsRequest = stubNewsDetailsRequest();

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(newsDetailsRequest, headers);
    ResponseEntity<NewsDetailsResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, NewsDetailsResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    assertEquals(response.getBody().getName(), newsDetailsRequest.getName());
    assertEquals(response.getBody().getContent(), newsDetailsRequest.getContent());
    assertEquals(response.getBody().getImage(), newsDetailsRequest.getImage());
  }
}
