package com.alkemy.ong.integration.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.request.DetailsMemberRequest;
import com.alkemy.ong.model.response.DetailsMemberResponse;
import com.alkemy.ong.model.response.ErrorResponse;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateMemberIntegrationTest extends AbstractBaseMemberIntegrationTest {
  private final String PATH = "/members";

  @Test
  public void shouldReturnForbiddenWhenUserIsNotUser() {
    login(ApplicationRole.ADMIN.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenAnAttributeIsNull() {
    login(ApplicationRole.USER.getFullRoleName());

    DetailsMemberRequest detailsMemberRequest = new DetailsMemberRequest();

    HttpEntity<DetailsMemberRequest> entity = new HttpEntity<>(detailsMemberRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, ErrorResponse.class);

    String emptyName = "The name attribute must not be empty.";
    String emptyImage = "The image attribute must not be empty.";
    String emptyDescription = "The description attribute must not be empty.";

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertTrue(response.getBody().getMessage().contains(emptyName));
    assertTrue(response.getBody().getMessage().contains(emptyImage));
    assertTrue(response.getBody().getMessage().contains(emptyDescription));
  }

  @Test
  public void shouldCreateAMemberSuccessfully() {
    when(memberRepository.save(isA(Member.class))).thenReturn(stubMember());

    login(ApplicationRole.USER.getFullRoleName());

    DetailsMemberRequest detailsMemberRequest = exampleMemberRequest();

    HttpEntity<DetailsMemberRequest> entity = new HttpEntity<>(detailsMemberRequest, headers);
    ResponseEntity<DetailsMemberResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, DetailsMemberResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    assertEquals(response.getBody().getName(), detailsMemberRequest.getName());
    assertEquals(response.getBody().getImage(), detailsMemberRequest.getImage());
    assertEquals(response.getBody().getDescription(), detailsMemberRequest.getDescription());
  }
}
