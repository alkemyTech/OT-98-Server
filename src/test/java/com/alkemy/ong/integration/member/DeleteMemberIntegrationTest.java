package com.alkemy.ong.integration.member;

import static org.junit.Assert.assertEquals;
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
import com.alkemy.ong.model.request.DetailsMemberRequest;
import com.alkemy.ong.model.response.ErrorResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteMemberIntegrationTest extends AbstractBaseMemberIntegrationTest {

  private final Long ID_TO_DELETE = stubMember().getId();
  private final String PATH = "/members/" + ID_TO_DELETE;

  @Test
  public void shouldReturnForbiddenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.DELETE, new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }


  @Test
  public void shouldReturnNotFoundWhenIdDoesNotExist() {
    when(memberRepository.findById(eq(ID_TO_DELETE))).thenReturn(Optional.empty());

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<DetailsMemberRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.DELETE, entity, ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    assertEquals(response.getBody().getMessage(), "The requested resource could not be found.");
  }

  @Test
  public void shouldSoftDeleteAMemberSuccessfully() {
    when(memberRepository.getById(eq(ID_TO_DELETE))).thenReturn(stubMember());
    when(memberRepository.save(eq(stubMember()))).thenReturn(stubMember());

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<Object> entity = new HttpEntity<>(headers);
    ResponseEntity<Object> response =
        restTemplate.exchange(createURLWithPort(PATH), HttpMethod.DELETE, entity, Object.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

}
