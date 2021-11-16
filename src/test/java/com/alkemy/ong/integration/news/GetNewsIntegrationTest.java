package com.alkemy.ong.integration.news;

import static org.junit.Assert.assertEquals;
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
import com.alkemy.ong.model.request.NewsDetailsRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.NewsDetailsResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetNewsIntegrationTest extends AbstractBaseNewsIntegrationTest {

  private final Long ID_TO_GET = stubNews().getId();
  private final String PATH = "/news/" + ID_TO_GET;

  @Test
  public void shouldReturnForbbidenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH), HttpMethod.GET,
        new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenIdNotExist() {
    when(newsRepository.findById(eq(ID_TO_GET))).thenReturn(Optional.empty());

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ErrorResponse> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.GET, entity, ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertEquals(response.getBody().getMessage(), "News not found!");
  }

  @Test
  public void shouldGetNewsSuccesful() {
    when(newsRepository.findById(eq(ID_TO_GET))).thenReturn(Optional.of(stubNews()));

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<NewsDetailsResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.GET, entity, NewsDetailsResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody().getId(), ID_TO_GET);
    assertEquals(response.getBody().getName(), stubNews().getName());
    assertEquals(response.getBody().getContent(), stubNews().getContent());
    assertEquals(response.getBody().getImage(), stubNews().getImage());
  }
}
