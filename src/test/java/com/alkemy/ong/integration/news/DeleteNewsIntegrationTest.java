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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteNewsIntegrationTest extends AbstractBaseNewsIntegrationTest {

  private final Long ID_TO_DELETE = stubNews().getId();
  private final String PATH = "/news/" + ID_TO_DELETE;

  @Test
  public void shouldReturnForbbidenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.DELETE, new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenIdNotExist() {
    when(newsRepository.findById(eq(ID_TO_DELETE))).thenReturn(Optional.empty());

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.DELETE, entity, ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertEquals(response.getBody().getMessage(), "News not found!");
  }

  @Test
  public void shouldSoftDeleteANewsSuccessful() {
    when(newsRepository.findById(eq(ID_TO_DELETE))).thenReturn(Optional.of(stubNews()));
    when(newsRepository.save(eq(stubNews()))).thenReturn(stubNews());

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<Object> entity = new HttpEntity<>(headers);
    ResponseEntity<Object> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.DELETE, entity, Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
  }
}
