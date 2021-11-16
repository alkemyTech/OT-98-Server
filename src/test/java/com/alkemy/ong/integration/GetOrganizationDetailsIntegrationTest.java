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
public class GetOrganizationDetailsIntegrationTest extends AbstractBaseOrganizationIntegrationTest {

  @Test
  public void shouldReturnOrganizationDetailsIfLogged() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("johnny@gmail.com"))).thenReturn(stubUser("ROLE_USER"));
    when(organizationRepository.findAll()).thenReturn(stubOrganization());

    login("ROLE_USER");

    entity = new HttpEntity<>(headers);
    response = restTemplate.exchange(
        createURLWithPort("/organization/public"), HttpMethod.GET, entity, OrganizationResponse.class);

    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals("Somos Mas", response.getBody().getName());

  }

  @Test
  public void shouldNotReturnOrganizationDetailsIfNotLogged() {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("johnny@gmail.com"))).thenReturn(stubUser("ROLE_USER"));
    when(organizationRepository.findAll()).thenReturn(stubOrganization());

    entity = new HttpEntity<>(headers);
    response = restTemplate.exchange(
        createURLWithPort("/organization/public"), HttpMethod.GET, entity, OrganizationResponse.class);
    Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

}
