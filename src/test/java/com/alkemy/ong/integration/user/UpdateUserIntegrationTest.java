package com.alkemy.ong.integration.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserUpdateRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.UserDetailsResponse;
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
public class UpdateUserIntegrationTest extends AbstractBaseUserIntegrationTest {

  private final Long ID_TO_DELETE = stubUser(ApplicationRole.USER.getFullRoleName()).getId();
  private final String PATH = "/users/" + ID_TO_DELETE;

  @Test
  public void shouldReturnForbiddenWhenUserIsNotUser() {
    login(ApplicationRole.ADMIN.getFullRoleName());
    UserUpdateRequest userUpdateRequest = exampleUserRequest();
    HttpEntity<UserUpdateRequest> entity = new HttpEntity<>(userUpdateRequest, headers);
    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.DELETE, entity, Object.class);
    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnNotFoundWhenIdNoExist() {
    when(userRepository.findById(eq(ID_TO_DELETE))).thenReturn(Optional.empty());
    login(ApplicationRole.USER.getFullRoleName());
    UserUpdateRequest userUpdateRequest = exampleUserRequest();
    HttpEntity<UserUpdateRequest> entity = new HttpEntity<>(userUpdateRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.PATCH, entity, ErrorResponse.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("User not found",
        response.getBody().getMessage());
  }

  @Test
  public void shouldUpdateUserSuccessfully() {
    User userEdited = stubUser(ApplicationRole.USER.getFullRoleName());
    userEdited.setFirstName("Edited");
    userEdited.setLastName("Edited");
    userEdited.setEmail("edited@gmail.com");
    userEdited.setPhoto("edited.png");
    when(userRepository.findById(eq(ID_TO_DELETE))).thenReturn(Optional.of(
        stubUser(
            ApplicationRole.USER.getFullRoleName())
    ));
    when(userRepository.save(eq(userEdited))).thenReturn(userEdited);
    login(ApplicationRole.USER.getFullRoleName());
    UserUpdateRequest userUpdateRequest = exampleUserRequest();
    userUpdateRequest.setFirstName("Edited");
    userUpdateRequest.setLastName("Edited");
    userUpdateRequest.setEmail("edited@gmail.com");
    userUpdateRequest.setPhoto("edited.png");
    HttpEntity<UserUpdateRequest> entity = new HttpEntity<>(userUpdateRequest, headers);
    ResponseEntity<UserDetailsResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.PATCH, entity, UserDetailsResponse.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(userUpdateRequest.getFirstName(), response.getBody().getFirstName());
    assertEquals(userUpdateRequest.getLastName(), response.getBody().getLastName());
    assertEquals(userUpdateRequest.getEmail(), response.getBody().getEmail());
    assertEquals(userUpdateRequest.getPhoto(), response.getBody().getPhoto());
  }

}
