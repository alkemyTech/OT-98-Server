package com.alkemy.ong.integration.contact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.request.CreateContactRequest;
import com.alkemy.ong.model.response.ListContactResponse;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListContactIntegrationTest extends AbstractBaseContactIntegrationTest {

  private final String PATH = "/contacts";

  @Test
  public void shouldReturnForbiddenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH), HttpMethod.GET,
        new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldListContactsSuccessfully() {
    List<Contact> contacts = stubContact(2);

    when(contactRepository.findByDeletedAtIsNull()).thenReturn(contacts);

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<CreateContactRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListContactResponse> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.GET, entity, ListContactResponse.class);

    Contact contact = contacts.get(0);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertNotNull(response.getBody().getContacts());
    assertEquals(response.getBody().getContacts().get(0).getId(), contact.getId());
    assertEquals(response.getBody().getContacts().get(0).getName(), contact.getName());
    assertEquals(response.getBody().getContacts().get(0).getPhone(), contact.getPhone());
    assertEquals(response.getBody().getContacts().get(0).getEmail(), contact.getEmail());
    assertEquals(response.getBody().getContacts().get(0).getMessage(), contact.getMessage());

  }
}
