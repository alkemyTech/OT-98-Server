package com.alkemy.ong.integration.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.config.ApplicationRole;
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
public class DeleteUserIntegrationTest extends AbstractBaseUserIntegrationTest {

  private final Long ID_TO_DELETE = stubUser(ApplicationRole.USER.getFullRoleName()).getId();
  private final String PATH = "/users/" + ID_TO_DELETE;

  @Test
  public void shouldReturnForbiddenWhenUserIsNotUser() {
    login(ApplicationRole.ADMIN.getFullRoleName());
    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.DELETE, new HttpEntity<>(headers), Object.class);
    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnNotFoundWhenIdNoExist() {
    when(userRepository.findById(eq(ID_TO_DELETE))).thenReturn(Optional.empty());
    login(ApplicationRole.USER.getFullRoleName());
    HttpEntity<Object> entity = new HttpEntity<>(headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.DELETE, entity, ErrorResponse.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("There's no User registered with that ID number!!!",
        response.getBody().getMessage());
  }

  @Test
  public void shouldSoftDeleteUserSuccessfully() {
    when(userRepository.getById(eq(ID_TO_DELETE))).thenReturn(stubUser(
        ApplicationRole.USER.getFullRoleName()));
    when(userRepository.save(eq(stubUser(
        ApplicationRole.USER.getFullRoleName())))).thenReturn(stubUser(
        ApplicationRole.USER.getFullRoleName()));
    login(ApplicationRole.USER.getFullRoleName());
    HttpEntity<Object> entity = new HttpEntity<>(headers);
    ResponseEntity<Object> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.DELETE, entity, Object.class);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

}
