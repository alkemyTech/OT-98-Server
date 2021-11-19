package com.alkemy.ong.integration.organization;

import com.alkemy.ong.common.SecurityTestConfig;
import com.alkemy.ong.common.AbstractBaseIntegrationTest;
import com.alkemy.ong.model.entity.Organization;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.OrganizationDetailsRequest;
import com.alkemy.ong.model.response.OrganizationResponse;
import com.alkemy.ong.repository.IOrganizationRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.util.Lists;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public class AbstractBaseOrganizationIntegrationTest extends AbstractBaseIntegrationTest {

  protected HttpEntity<OrganizationDetailsRequest> entity;
  protected ResponseEntity<OrganizationResponse> response;

  @MockBean
  protected IOrganizationRepository organizationRepository;

  protected void login(String role) {
    String jwt = SecurityTestConfig.createToken("johnny@gmail.com", role);
    headers.set("Authorization", jwt);
  }

  protected Role stubRole(String name) {
    Role role = new Role();
    role.setName(name);
    return role;
  }

  protected User stubUser(String role) {
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

  protected List<Organization> stubOrganization() {
    List<Organization> organizationList = new ArrayList<>();
    Organization organization = new Organization(1L,
        "Somos Mas",
        "somos_mas.jpg",
        "some place 1234",
        123456789,
        "somos_mas@gmail.com",
        "Welcome!!!",
        "We are Somos Más",
        new Timestamp(System.currentTimeMillis()),
        false);
    organizationList.add(organization);
    return organizationList;
  }

  protected OrganizationDetailsRequest stubOrganizationDetailsRequest() {
    OrganizationDetailsRequest organizationDetailsRequest = new OrganizationDetailsRequest();
    organizationDetailsRequest.setName("Somos Mas 2");
    organizationDetailsRequest.setImage("somos_mas_2.jpg");
    organizationDetailsRequest.setAddress("some place 1234 bis");
    organizationDetailsRequest.setPhone(1234567890);
    organizationDetailsRequest.setEmail("somos_mas2@gmail.com");
    organizationDetailsRequest.setWelcomeText("Welcome2!!!");
    organizationDetailsRequest.setAboutUsText("We are Somos Mas 2");
    organizationDetailsRequest.setFacebookUrl("somos_mas_2");
    organizationDetailsRequest.setLinkedinUrl("somos_mas_2");
    organizationDetailsRequest.setInstagramUrl("somos_mas_2");
    return organizationDetailsRequest;
  }

}
