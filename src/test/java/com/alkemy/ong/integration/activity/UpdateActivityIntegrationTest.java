package com.alkemy.ong.integration.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.ActivityDetailsRequest;
import com.alkemy.ong.model.response.ActivityDetailsResponse;
import com.alkemy.ong.model.response.ErrorResponse;
import java.util.Optional;
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
public class UpdateActivityIntegrationTest extends AbstractBaseActivityIntegrationTest {

  private final Long ID_TO_UPDATE = stubActivity().getId();
  private final String PATH = "/activities/" + ID_TO_UPDATE;

  @Test
  public void shouldReturnForbiddenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());
    ActivityDetailsRequest activityDetailsRequest = new ActivityDetailsRequest();
    HttpEntity<ActivityDetailsRequest> entity = new HttpEntity<>(activityDetailsRequest, headers);
    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.PUT, entity, Object.class);
    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenAnAttributeIsNull() {
    login(ApplicationRole.ADMIN.getFullRoleName());
    ActivityDetailsRequest activityDetailsRequest = new ActivityDetailsRequest();
    HttpEntity<ActivityDetailsRequest> entity = new HttpEntity<>(activityDetailsRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.PUT, entity, ErrorResponse.class);
    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertTrue(response.getBody().getMessage().contains(emptyName));
    assertTrue(response.getBody().getMessage().contains(emptyContent));
    assertTrue(response.getBody().getMessage().contains(emptyImage));
  }

  @Test
  public void shouldReturnNotFoundWhenIdNotExist() {
    when(activityRepository.findById(eq(ID_TO_UPDATE))).thenReturn(Optional.empty());
    login(ApplicationRole.ADMIN.getFullRoleName());
    ActivityDetailsRequest activityDetailsRequest = exampleActivityRequest();
    HttpEntity<ActivityDetailsRequest> entity = new HttpEntity<>(activityDetailsRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.PUT, entity, ErrorResponse.class);
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    assertEquals(response.getBody().getMessage(), "Activity not found");
  }

  @Test
  public void shouldUpdateActivitySuccessfully() {
    Activity editedActivity = stubActivity();
    editedActivity.setName("Edited");
    editedActivity.setContent("Edited");
    editedActivity.setImage("Edited.png");
    when(activityRepository.findById(eq(ID_TO_UPDATE))).thenReturn(Optional.of(stubActivity()));
    when(activityRepository.save(eq(editedActivity))).thenReturn(editedActivity);
    login(ApplicationRole.ADMIN.getFullRoleName());
    ActivityDetailsRequest activityDetailsRequest = exampleActivityRequest();
    activityDetailsRequest.setName("Edited");
    activityDetailsRequest.setContent("Edited");
    activityDetailsRequest.setImage("Edited.png");
    HttpEntity<ActivityDetailsRequest> entity = new HttpEntity<>(activityDetailsRequest, headers);
    ResponseEntity<ActivityDetailsResponse> response = restTemplate.exchange(
        createURLWithPort(PATH),
        HttpMethod.PUT, entity, ActivityDetailsResponse.class);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody().getName(), activityDetailsRequest.getName());
    assertEquals(response.getBody().getContent(), activityDetailsRequest.getContent());
    assertEquals(response.getBody().getImage(), activityDetailsRequest.getImage());
  }

}
