package com.alkemy.ong.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.common.AbstractBaseIntegrationTest;
import com.alkemy.ong.common.SecurityTestConfig;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.response.UserAuthenticatedMeResponse;
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
    Assert.assertEquals("John", response.getBody().getFirstName());
    Assert.assertEquals("Doe", response.getBody().getLastName());
    Assert.assertEquals("johnny@gmail.com", response.getBody().getEmail());
    Assert.assertEquals("https://foo.jpg", response.getBody().getPhoto());
  }

}
