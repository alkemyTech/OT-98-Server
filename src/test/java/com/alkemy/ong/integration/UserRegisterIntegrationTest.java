package com.alkemy.ong.integration;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import com.alkemy.ong.common.JwtUtil;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserRegisterRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.OrganizationResponse;
import com.alkemy.ong.model.response.UserRegisterResponse;
import com.alkemy.ong.service.abstraction.IOrganizationService;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRegisterIntegrationTest extends AbstractBaseIntegrationTest {

  @Autowired
  private JwtUtil jwtUtil;

  @MockBean
  private IOrganizationService organizationService;

  @Test
  public void shouldReturnBadRequestWhenTheEmailAlreadyExist() {
    when(userRepository.findByEmail(eq("example@gmail.com"))).thenReturn(new User());

    UserRegisterRequest registerRequest = new UserRegisterRequest();
    registerRequest.setFirstName("Example");
    registerRequest.setLastName("Example");
    registerRequest.setEmail("example@gmail.com");
    registerRequest.setPassword("12346789");

    HttpEntity<UserRegisterRequest> entity = new HttpEntity<>(registerRequest, this.headers);

    ResponseEntity<ErrorResponse> response = this.restTemplate.exchange(
        createURLWithPort("/auth/register"), HttpMethod.POST, entity, ErrorResponse.class);

    Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Assert.assertEquals("Email already exist.", response.getBody().getMessage());
  }

  @Test
  public void shouldRegisterSuccessfully() {
    when(roleService.findBy(eq(ApplicationRole.USER.getFullRoleName())))
        .thenReturn(stubRole("USER"));
    when(userRepository.save(isA(User.class))).thenReturn(stubUser("USER"));
    when(organizationService.getOrganizationDetails()).thenReturn(stubOrganization());

    UserRegisterRequest registerRequest = new UserRegisterRequest();
    registerRequest.setFirstName("Example");
    registerRequest.setLastName("Example");
    registerRequest.setEmail("example@gmail.com");
    registerRequest.setPassword("12346789");

    HttpEntity<UserRegisterRequest> entity = new HttpEntity<>(registerRequest, this.headers);

    ResponseEntity<UserRegisterResponse> response = this.restTemplate.exchange(
        createURLWithPort("/auth/register"), HttpMethod.POST, entity, UserRegisterResponse.class);

    Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Assert.assertEquals(response.getBody().getFirstsName(), registerRequest.getFirstName());
    Assert.assertEquals(response.getBody().getLastName(), registerRequest.getLastName());
    Assert.assertEquals(response.getBody().getEmail(), registerRequest.getEmail());
    Assert.assertTrue(jwtUtil.validateToken(response.getBody().getJwt(), stubUser("USER")));
  }

  private Role stubRole(String name) {
    Role role = new Role();
    role.setName(name);
    return role;
  }

  private User stubUser(String role) {
    return new User(12L,
        "Example",
        "Example",
        "example@gmail.com",
        "123456789",
        "photo",
        Lists.list(stubRole(role)),
        null,
        false);
  }

  private OrganizationResponse stubOrganization() {
    return new OrganizationResponse(
        "Fit",
        "toyota.jpg",
        1112222,
        "don juan toyota 1234",
        "toyota",
        "toyota",
        "toyota"
    );
  }

}
