package com.alkemy.ong.integration.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.ActivityDetailsRequest;
import com.alkemy.ong.model.response.ActivityDetailsResponse;
import com.alkemy.ong.model.response.ErrorResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CreateActivityIntegrationTest extends AbstractBaseActivityIntegrationTest {

  private final String PATH = "/activities";

  @Test
  public void shouldReturnForbiddenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());
    ActivityDetailsRequest activityDetailsRequest = new ActivityDetailsRequest();
    HttpEntity<ActivityDetailsRequest> entity = new HttpEntity<>(activityDetailsRequest, headers);
    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, Object.class);
    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenAnAttributeIsNull() {
    login(ApplicationRole.ADMIN.getFullRoleName());
    ActivityDetailsRequest activityDetailsRequest = new ActivityDetailsRequest();
    HttpEntity<ActivityDetailsRequest> entity = new HttpEntity<>(activityDetailsRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, ErrorResponse.class);
    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertTrue(response.getBody().getMessage().contains(emptyName));
    assertTrue(response.getBody().getMessage().contains(emptyContent));
    assertTrue(response.getBody().getMessage().contains(emptyImage));
  }

  @Test
  public void shouldCreateActivitySuccessfully() {
    when(activityRepository.save(isA(Activity.class))).thenReturn(stubActivity());
    login(ApplicationRole.ADMIN.getFullRoleName());
    ActivityDetailsRequest activityDetailsRequest = exampleActivityRequest();
    HttpEntity<ActivityDetailsRequest> entity = new HttpEntity<>(activityDetailsRequest, headers);
    ResponseEntity<ActivityDetailsResponse> response = restTemplate.exchange(
        createURLWithPort(PATH),
        HttpMethod.POST, entity, ActivityDetailsResponse.class);
    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    assertEquals(response.getBody().getName(), activityDetailsRequest.getName());
    assertEquals(response.getBody().getContent(), activityDetailsRequest.getContent());
    assertEquals(response.getBody().getImage(), activityDetailsRequest.getImage());
  }

}
