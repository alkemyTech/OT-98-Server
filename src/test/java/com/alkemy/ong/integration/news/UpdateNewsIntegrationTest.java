package com.alkemy.ong.integration.news;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.request.NewsDetailsRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.NewsDetailsResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateNewsIntegrationTest extends AbstractBaseNewsIntegrationTest {

  private final Long ID_TO_UPDATE = stubNews().getId();
  private final String PATH = "/news/" + ID_TO_UPDATE;

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

    NewsDetailsRequest newsDetailsRequest = new NewsDetailsRequest();

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(newsDetailsRequest, headers);
    ResponseEntity<ErrorResponse> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.PUT, entity, ErrorResponse.class);

    String emptyName = "The name attribute must not be empty.";
    String emptyContent = "The content attribute must not be empty.";
    String emptyImage = "The image attribute must not be empty.";

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertTrue(response.getBody().getMessage().contains(emptyName));
    assertTrue(response.getBody().getMessage().contains(emptyContent));
    assertTrue(response.getBody().getMessage().contains(emptyImage));
  }

  @Test
  public void shouldReturnBadRequestWhenIdNotExist() {
    when(newsRepository.findById(eq(ID_TO_UPDATE))).thenReturn(Optional.empty());

    login(ApplicationRole.ADMIN.getFullRoleName());

    NewsDetailsRequest newsDetailsRequest = stubNewsDetailsRequest();

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(newsDetailsRequest, headers);
    ResponseEntity<ErrorResponse> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.PUT, entity, ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertEquals(response.getBody().getMessage(), "News not found!");
  }

  @Test
  public void shouldUpdateNewsSuccessful() {
    News editedNews = stubNews();
    editedNews.setName("Edited");
    editedNews.setContent("Edited");
    editedNews.setImage("Edited.png");

    when(newsRepository.findById(eq(ID_TO_UPDATE))).thenReturn(Optional.of(stubNews()));
    when(newsRepository.save(eq(editedNews))).thenReturn(editedNews);

    login(ApplicationRole.ADMIN.getFullRoleName());

    NewsDetailsRequest newsDetailsRequest = stubNewsDetailsRequest();
    newsDetailsRequest.setName("Edited");
    newsDetailsRequest.setContent("Edited");
    newsDetailsRequest.setImage("Edited.png");

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(newsDetailsRequest, headers);
    ResponseEntity<NewsDetailsResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.PUT, entity, NewsDetailsResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody().getName(), newsDetailsRequest.getName());
    assertEquals(response.getBody().getContent(), newsDetailsRequest.getContent());
    assertEquals(response.getBody().getImage(), newsDetailsRequest.getImage());
  }
}
