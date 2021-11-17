package com.alkemy.ong.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.model.response.OrganizationResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateOrganizationIntegrationTest extends AbstractBaseOrganizationIntegrationTest {

  @Test
  public void shouldUpdateOrganizationDetailsWithRightRole() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("johnny@gmail.com"))).thenReturn(stubUser("ROLE_ADMIN"));
    when(organizationRepository.findAll()).thenReturn(stubOrganization());

    login("ROLE_ADMIN");

    entity = new HttpEntity<>(stubOrganizationDetailsRequest(), headers);
    response = restTemplate.exchange(
        createURLWithPort("/organization/public"),
        HttpMethod.POST,
        entity,
        OrganizationResponse.class);

    Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  public void shouldNotUpdateOrganizationDetailsIfNotAdmin() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("johnny@gmail.com"))).thenReturn(stubUser("ROLE_USER"));
    when(organizationRepository.findAll()).thenReturn(stubOrganization());

    login("ROLE_USER");

    entity = new HttpEntity<>(stubOrganizationDetailsRequest(), headers);
    response = restTemplate.exchange(
        createURLWithPort("/organization/public"),
        HttpMethod.POST,
        entity,
        OrganizationResponse.class);

    Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

}
