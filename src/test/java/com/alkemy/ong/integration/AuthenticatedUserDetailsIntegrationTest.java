package com.alkemy.ong.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.common.SecurityTestConfig;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.response.UserAuthenticatedMeResponse;
import java.sql.Timestamp;
import java.time.Instant;
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
public class AuthenticatedUserDetailsIntegrationTest extends AbstractBaseIntegrationTest {

  @Test
  public void shouldMeSuccessfullyUser() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("imontovio@alkemy.com"))).thenReturn(
        stubUser(ApplicationRole.USER.getFullRoleName()));

    String jwt = SecurityTestConfig.createToken("imontovio@alkemy.com",
        ApplicationRole.USER.getFullRoleName());
    headers.set("Authorization", jwt);
    HttpEntity<UserAuthenticationRequest> entity = new HttpEntity<>(headers);

    ResponseEntity<UserAuthenticatedMeResponse> response = restTemplate.exchange(
        createURLWithPort("/auth/me"), HttpMethod.GET, entity, UserAuthenticatedMeResponse.class);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals("Ignacio", response.getBody().getFirstName());
    Assert.assertEquals("Montovio", response.getBody().getLastName());
    Assert.assertEquals("imontovio@alkemy.com", response.getBody().getEmail());
    Assert.assertEquals("https://foo.jpg", response.getBody().getPhoto());
  }

  private Role stubRole(String name) {
    Role role = new Role();
    role.setName(name);
    return role;
  }

  private User stubUser(String role) {
    User user = new User();
    user.setEmail("imontovio@alkemy.com");
    user.setPhoto("https://foo.jpg");
    user.setFirstName("Ignacio");
    user.setLastName("Montovio");
    user.setPassword("foo12345");
    user.setRoles(Lists.list(stubRole(role)));
    user.setTimestamp(Timestamp.from(Instant.now()));
    user.setSoftDeleted(false);
    return user;
  }
}
