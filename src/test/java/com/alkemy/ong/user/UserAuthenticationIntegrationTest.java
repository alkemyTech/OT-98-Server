package com.alkemy.ong.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.common.AbstractBaseIntegrationTest;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.UserDetailsResponse;
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
    when(userRepository.findByEmail(eq("johnny@gmail.com"))).thenReturn(stubUser("USER"));

    UserAuthenticationRequest authenticationRequest = new UserAuthenticationRequest();
    authenticationRequest.setEmail("johnny@gmail.com");
    authenticationRequest.setPassword("abc1234&");

    HttpEntity<UserAuthenticationRequest> entity = new HttpEntity<>(authenticationRequest, headers);

    ResponseEntity<UserDetailsResponse> response = restTemplate.exchange(
        createURLWithPort("/auth/login"), HttpMethod.POST, entity, UserDetailsResponse.class);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertNotNull(response.getBody().getEmail());
    Assert.assertEquals("johnny@gmail.com", response.getBody().getEmail());
  }

}
