package com.alkemy.ong.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.common.SecurityTestConfig;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.OrganizationDetailsRequest;
import com.alkemy.ong.model.request.UserAuthenticationRequest;
import com.alkemy.ong.model.response.OrganizationResponse;
import com.alkemy.ong.model.response.UserAuthenticatedMeResponse;
import com.alkemy.ong.service.abstraction.IOrganizationService;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizationIntegrationTest extends AbstractBaseIntegrationTest {

  @MockBean
  private IOrganizationService organizationService;

  @Test
  public void shouldReturnOrganizationDetailsIfLogged() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("johnny@gmail.com"))).thenReturn(stubUser("ROLE_USER"));
    when(organizationService.getOrganizationDetails()).thenReturn(stubOrganizationResponse());

    login("ROLE_USER");

    HttpEntity<OrganizationDetailsRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<OrganizationResponse> response = restTemplate.exchange(
        createURLWithPort("/organization/public"), HttpMethod.GET, entity, OrganizationResponse.class);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals("Somos Mas", response.getBody().getName());

  }

  @Test
  public void shouldNotReturnOrganizationDetailsIfNotLogged() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("johnny@gmail.com"))).thenReturn(stubUser("ROLE_USER"));
    when(organizationService.getOrganizationDetails()).thenReturn(stubOrganizationResponse());

    HttpEntity<OrganizationDetailsRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/organization/public"), String.class);
    Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  public void shouldUpdateOrganizationDetailsWithRightRole() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("johnny@gmail.com"))).thenReturn(stubUser("ROLE_ADMIN"));

    login("ROLE_ADMIN");

    HttpEntity<OrganizationDetailsRequest> entity = new HttpEntity<>(stubOrganizationDetailsRequest(), headers);
    ResponseEntity<OrganizationResponse> response = restTemplate.exchange(
        createURLWithPort("/organization/public"), HttpMethod.POST, entity, OrganizationResponse.class);

    Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  public void shouldNotUpdateOrganizationDetailsIfNotAdmin() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("johnny@gmail.com"))).thenReturn(stubUser("ROLE_USER"));

    login("ROLE_USER");

    HttpEntity<OrganizationDetailsRequest> entity = new HttpEntity<>(stubOrganizationDetailsRequest(), headers);
    ResponseEntity<OrganizationResponse> response = restTemplate.exchange(
        createURLWithPort("/organization/public"), HttpMethod.POST, entity, OrganizationResponse.class);

    Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  private Role stubRole(String name) {
    Role role = new Role();
    role.setName(name);
    return role;
  }

  private User stubUser(String role) {
    return new User(1L,
        "John",
        "Doe",
        "johnny@gmail.com",
        "123456789",
        "photo.jpg",
        Lists.list(stubRole(role)),
        null,
        false);
  }

  private OrganizationResponse stubOrganizationResponse() {
    return new OrganizationResponse("Somos Mas",
        "somos_mas.jpg",
        123456789,
        "some place 1234",
        "somos_mas",
        "somos_mas",
        "somos_mas");
  }

  private OrganizationDetailsRequest stubOrganizationDetailsRequest() {
    OrganizationDetailsRequest organizationDetailsRequest = new OrganizationDetailsRequest();
    organizationDetailsRequest.setName("");
    organizationDetailsRequest.setImage("");
    organizationDetailsRequest.setAddress("some place 1234");
    organizationDetailsRequest.setPhone(123456789);
    organizationDetailsRequest.setEmail("");
    organizationDetailsRequest.setWelcomeText("");
    organizationDetailsRequest.setAboutUsText("We are Somos Mas");
    organizationDetailsRequest.setFacebookUrl("somos_mas");
    organizationDetailsRequest.setLinkedinUrl("somos_mas");
    organizationDetailsRequest.setInstagramUrl("somos_mas");
    return organizationDetailsRequest;
  }

  private void login(String role) {
    String jwt = SecurityTestConfig.createToken("johnny@gmail.com", role);
    headers.set("Authorization", jwt);
    HttpEntity<UserAuthenticationRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<UserAuthenticatedMeResponse> response = restTemplate.exchange(
        createURLWithPort("/auth/me"), HttpMethod.GET, entity, UserAuthenticatedMeResponse.class);
  }

}
