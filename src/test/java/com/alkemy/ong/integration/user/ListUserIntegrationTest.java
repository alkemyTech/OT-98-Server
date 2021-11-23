package com.alkemy.ong.integration.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.response.ListActiveUsersResponse;
import java.util.List;
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
public class ListUserIntegrationTest extends AbstractBaseUserIntegrationTest {

  private final String PATH = "/users";

  @Test
  public void shouldReturnForbiddenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());
    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH), HttpMethod.GET,
        new HttpEntity<>(headers), Object.class);
    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnListUserSuccessfully() {
    List<User> users = stubUser(2);
    when(userRepository.findBySoftDeletedFalse()).thenReturn(users);
    login(ApplicationRole.ADMIN.getFullRoleName());
    HttpEntity<Object> entity = new HttpEntity<>(headers);
    ResponseEntity<ListActiveUsersResponse> response = restTemplate.exchange(
        createURLWithPort(PATH),
        HttpMethod.GET, entity, ListActiveUsersResponse.class);
    User user = users.get(0);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody().getUsers());
    assertEquals(user.getId(), response.getBody().getUsers().get(0).getId());
    assertEquals(user.getFirstName(), response.getBody().getUsers().get(0).getFirstName());
    assertEquals(user.getLastName(), response.getBody().getUsers().get(0).getLastName());
    assertEquals(user.getEmail(), response.getBody().getUsers().get(0).getEmail());
    assertEquals(user.getPhoto(), response.getBody().getUsers().get(0).getPhoto());
  }

}
