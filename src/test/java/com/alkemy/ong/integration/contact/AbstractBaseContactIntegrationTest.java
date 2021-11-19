package com.alkemy.ong.integration.contact;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.util.Lists;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.alkemy.ong.common.SecurityTestConfig;
import com.alkemy.ong.integration.AbstractBaseIntegrationTest;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.CreateContactRequest;
import com.alkemy.ong.repository.IContactRepository;

public abstract class AbstractBaseContactIntegrationTest extends AbstractBaseIntegrationTest {

  @MockBean
  protected IContactRepository contactRepository;


  protected Role stubRole(String name) {
    Role role = new Role();
    role.setName(name);
    return role;
  }

  protected User stubUser(String role) {
    User user = new User();
    user.setEmail("jaman@alkemy.com");
    user.setPhoto("https://foo.jpg");
    user.setFirstName("Joaquin");
    user.setLastName("Aman");
    user.setPassword("foo12345");
    user.setRoles(Lists.list(stubRole(role)));
    user.setTimestamp(Timestamp.from(Instant.now()));
    user.setSoftDeleted(false);
    return user;
  }

  protected User stubAdmin(String role) {
    User user = new User();
    user.setEmail("mruiz@alkemy.com");
    user.setPhoto("https://foo.jpg");
    user.setFirstName("Marip");
    user.setLastName("Ruiz");
    user.setPassword("foo12345");
    user.setRoles(Lists.list(stubRole(role)));
    user.setTimestamp(Timestamp.from(Instant.now()));
    user.setSoftDeleted(false);
    return user;
  }

  protected void loginADMIN(String role) {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("mruiz@alkemy.com"))).thenReturn(stubAdmin(role));

    String jwt = SecurityTestConfig.createToken("mruiz@alkemy.com", role);
    headers.set("Authorization", jwt);
  }

  protected void loginUSER(String role) {
    when(authenticationManager.authenticate(any())).thenReturn(null);
    when(userRepository.findByEmail(eq("jaman@alkemy.com"))).thenReturn(stubUser(role));

    String jwt = SecurityTestConfig.createToken("jaman@alkemy.com", role);
    headers.set("Authorization", jwt);
  }

  protected Contact stubContact() {
    return new Contact(1l, "nameExample", "12346789", "example@alkemy.com", "messageExample", null);
  }

  protected CreateContactRequest stubContactRequest() {
    CreateContactRequest createContactRequest = new CreateContactRequest();
    createContactRequest.setName("nameExample");
    createContactRequest.setPhone("12346789");
    createContactRequest.setEmail("example@alkemy.com");
    createContactRequest.setMessage("messageExample");
    return createContactRequest;
  }

  protected List<Contact> stubContact(int n) {
    List<Contact> contacts = new ArrayList<Contact>();

    for (int i = 1; i <= n; i++) {
      contacts.add(
          new Contact(i, "nameExample", "12346789", "example@alkemy.com", "messageExample", null));
    }

    return contacts;
  }
}
