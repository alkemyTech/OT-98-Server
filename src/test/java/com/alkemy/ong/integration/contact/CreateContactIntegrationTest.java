package com.alkemy.ong.integration.contact;

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
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.request.CreateContactRequest;
import com.alkemy.ong.model.response.DetailsContactResponse;
import com.alkemy.ong.model.response.ErrorResponse;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateContactIntegrationTest extends AbstractBaseContactIntegrationTest {

  private final String PATH = "/contacts";

  @Test
  public void shouldReturnForbbidenWhenUserIsNotUser() {

    loginUSER(ApplicationRole.ADMIN.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenAnAttributeIsNull() {
    when(contactRepository.save(isA(Contact.class))).thenReturn(stubContact());

    loginUSER(ApplicationRole.USER.getFullRoleName());

    CreateContactRequest createContactRequest = new CreateContactRequest();

    HttpEntity<CreateContactRequest> entity = new HttpEntity<>(createContactRequest, headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, ErrorResponse.class);

    String emptyName = "The name attribute must not be empty.";
    String emptyEmail = "The email attribute must not be empty.";

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertTrue(response.getBody().getMessage().contains(emptyName));
    assertTrue(response.getBody().getMessage().contains(emptyEmail));
  }

  @Test
  public void shouldCreateAContactSuccessfully() {
    when(contactRepository.save(isA(Contact.class))).thenReturn(stubContact());

    loginUSER(ApplicationRole.USER.getFullRoleName());

    CreateContactRequest createContactRequest = stubContactRequest();

    HttpEntity<CreateContactRequest> entity = new HttpEntity<>(createContactRequest, headers);
    ResponseEntity<DetailsContactResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.POST, entity, DetailsContactResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    assertEquals(response.getBody().getName(), createContactRequest.getName());
    assertEquals(response.getBody().getEmail(), createContactRequest.getEmail());
  }
}
