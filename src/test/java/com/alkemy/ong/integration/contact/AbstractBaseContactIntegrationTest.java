package com.alkemy.ong.integration.contact;

import com.alkemy.ong.common.AbstractBaseIntegrationTest;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.request.CreateContactRequest;
import com.alkemy.ong.repository.IContactRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class AbstractBaseContactIntegrationTest extends AbstractBaseIntegrationTest {

  @MockBean
  protected IContactRepository contactRepository;

  protected Contact stubContact() {
    return new Contact(1l, "nameExample", "12346789", "example@alkemy.com", "messageExample", null);
  }

  protected CreateContactRequest exampleContactRequest() {
    CreateContactRequest createContactRequest = new CreateContactRequest();
    createContactRequest.setName("nameExample");
    createContactRequest.setPhone("12346789");
    createContactRequest.setEmail("example@alkemy.com");
    createContactRequest.setMessage("messageExample");
    return createContactRequest;
  }

  protected List<Contact> stubContact(int count) {
    List<Contact> contacts = new ArrayList<>();

    for (int i = 1; i <= count; i++) {
      contacts.add(
          new Contact(i, "nameExample", "12346789", "example@alkemy.com", "messageExample", null));
    }

    return contacts;
  }
}
