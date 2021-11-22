package com.alkemy.ong.integration.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.Optional;
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
public class UpdateMemberIntegrationTest extends AbstractBaseMemberIntegrationTest {

  private final Long ID_TO_UPDATE = stubMember().getId();
  private final String PATH = "/members/" + ID_TO_UPDATE;

  @Test
  public void shouldReturnForbiddenWhenUserIsNotUser() {
    login(ApplicationRole.ADMIN.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH), HttpMethod.PUT,
        new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenAnAttributeIsNull() {
    login(ApplicationRole.USER.getFullRoleName());

    DetailsMemberRequest detailsMemberRequest = new DetailsMemberRequest();

    HttpEntity<DetailsMemberRequest> entity = new HttpEntity<>(detailsMemberRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.PUT, entity, ErrorResponse.class);

    String emptyName = "The name attribute must not be empty.";
    String emptyImage = "The image attribute must not be empty.";
    String emptyDescription = "The description attribute must not be empty.";

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertTrue(response.getBody().getMessage().contains(emptyName));
    assertTrue(response.getBody().getMessage().contains(emptyImage));
    assertTrue(response.getBody().getMessage().contains(emptyDescription));
  }

  @Test
  public void shouldReturnNotFoundWhenIdNotExist() {
    when(memberRepository.findById(eq(ID_TO_UPDATE))).thenReturn(Optional.empty());

    login(ApplicationRole.USER.getFullRoleName());

    DetailsMemberRequest detailsMemberRequest = exampleMemberRequest();

    HttpEntity<DetailsMemberRequest> entity = new HttpEntity<>(detailsMemberRequest, headers);
    ResponseEntity<ErrorResponse> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.PUT, entity, ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    assertEquals(response.getBody().getMessage(), "Member not found");
  }

  @Test
  public void shouldUpdateMemberSuccessfully() {
    Member editedMember = stubMember();
    editedMember.setName("nameEdited");
    editedMember.setImage("imageEdited.png");
    editedMember.setDescription("descEdited");

    when(memberRepository.findById(eq(ID_TO_UPDATE))).thenReturn(Optional.of(stubMember()));
    when(memberRepository.save(eq(editedMember))).thenReturn(editedMember);

    login(ApplicationRole.USER.getFullRoleName());

    DetailsMemberRequest detailsMemberRequest = exampleMemberRequest();
    detailsMemberRequest.setName("nameEdited");
    detailsMemberRequest.setDescription("descEdited");
    detailsMemberRequest.setImage("imageEdited.png");

    HttpEntity<DetailsMemberRequest> entity = new HttpEntity<>(detailsMemberRequest, headers);
    ResponseEntity<DetailsMemberResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.PUT, entity, DetailsMemberResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody().getName(), detailsMemberRequest.getName());
    assertEquals(response.getBody().getImage(), detailsMemberRequest.getImage());
    assertEquals(response.getBody().getDescription(), detailsMemberRequest.getDescription());
  }
}
