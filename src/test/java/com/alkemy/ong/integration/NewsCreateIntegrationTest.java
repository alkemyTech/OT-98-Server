package com.alkemy.ong.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.CreateNewsRequest;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.UserDetailsResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsCreateIntegrationTest extends AbstractBaseIntegrationTest {

  private String jwtToken;

  @Before
  public void shouldLoginSuccessfully() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("user@alkemy.com"))).thenReturn(stubUser("USER"));

    UserAuthenticationRequest authenticationRequest = new UserAuthenticationRequest();
    authenticationRequest.setEmail("user@alkemy.com");
    authenticationRequest.setPassword("abc1234&");

    HttpEntity<UserAuthenticationRequest> entity = new HttpEntity<>(authenticationRequest, headers);

    ResponseEntity<UserDetailsResponse> response = restTemplate.exchange(
        createURLWithPort("/auth/login"), HttpMethod.POST, entity, UserDetailsResponse.class);

    jwtToken = response.getBody().getJwt();
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

  @Test
  public void shouldReturnBadRequestWhenCategoryNotExist() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("user@alkemy.com"))).thenReturn(stubUser("USER"));

    UserAuthenticationRequest authenticationRequest = new UserAuthenticationRequest();
    authenticationRequest.setEmail("user@alkemy.com");
    authenticationRequest.setPassword("abc1234&");

    HttpEntity<UserAuthenticationRequest> entityA =
        new HttpEntity<>(authenticationRequest, headers);

    ResponseEntity<UserDetailsResponse> responseA = restTemplate.exchange(
        createURLWithPort("/auth/login"), HttpMethod.POST, entityA, UserDetailsResponse.class);

    jwtToken = responseA.getBody().getJwt();

    CreateNewsRequest createNewsRequest = new CreateNewsRequest();
    createNewsRequest.setName("Example");
    createNewsRequest.setImage("photo.png");
    createNewsRequest.setContent("Example");
    createNewsRequest.setCategoryName("Example");

    System.out.println(jwtToken);
    headers.add(HttpHeaders.AUTHORIZATION, jwtToken);
    HttpEntity<CreateNewsRequest> entity = new HttpEntity<>(createNewsRequest, headers);

    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort("/news"),
        HttpMethod.POST, entity, ErrorResponse.class);

    System.out.println(response.getStatusCodeValue());
    Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Assert.assertEquals("Category not exist", response.getBody().getMessage());
  }
}
