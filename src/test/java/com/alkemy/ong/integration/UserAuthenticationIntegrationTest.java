package com.alkemy.ong.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.UserDetailsResponse;
import org.assertj.core.util.Lists;
import org.junit.Assert;
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
public class UserAuthenticationIntegrationTest extends AbstractBaseIntegrationTest {

  @Test
  public void shouldReturnBadRequestWhenEmailDoesNotHaveRightFormat() {
    UserAuthenticationRequest authenticationRequest = new UserAuthenticationRequest();
    authenticationRequest.setEmail("abc");
    authenticationRequest.setPassword("1234");

    HttpEntity<UserAuthenticationRequest> entity = new HttpEntity<>(authenticationRequest, headers);

    ResponseEntity<ErrorResponse> response = restTemplate.exchange(
        createURLWithPort("/auth/login"), HttpMethod.POST, entity, ErrorResponse.class);

    Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Assert.assertEquals("Invalid email or password.", response.getBody().getMessage());
  }

  @Test
  public void shouldLoginSuccessfully() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("user@alkemy.com"))).thenReturn(stubUser("USER"));

    UserAuthenticationRequest authenticationRequest = new UserAuthenticationRequest();
    authenticationRequest.setEmail("user@alkemy.com");
    authenticationRequest.setPassword("abc1234&");

    HttpEntity<UserAuthenticationRequest> entity = new HttpEntity<>(authenticationRequest, headers);

    ResponseEntity<UserDetailsResponse> response = restTemplate.exchange(
        createURLWithPort("/auth/login"), HttpMethod.POST, entity, UserDetailsResponse.class);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertNotNull(response.getBody().getEmail());
    Assert.assertEquals("user@alkemy.com", response.getBody().getEmail());
  }

  private Role stubRole(String name) {
    Role role = new Role();
    role.setName(name);
    return role;
  }

  private User stubUser(String role) {
    User user = new User();
    user.setEmail("user@alkemy.com");
    user.setPhoto("photo");
    user.setFirstName("Bruce");
    user.setLastName("Wayne");
    user.setPassword("abc1234&");
    user.setRoles(Lists.list(stubRole(role)));
    return user;
  }
}
