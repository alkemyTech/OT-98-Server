package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.request.CreateContactRequest;
import com.alkemy.ong.repository.IContactRepository;
import com.alkemy.ong.service.abstraction.ICreateContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ICreateContactService {
  @Autowired
  IContactRepository contactRepository;

  @Override
  public Contact create(CreateContactRequest createContactRequest) {
    Contact contact= new Contact();
    contact.setName(createContactRequest.getName());
    contact.setPhone(createContactRequest.getPhone());
    contact.setEmail(createContactRequest.getEmail());
    contact.setMessage(createContactRequest.getMessage());
    return this.contactRepository.save(contact);
  }
}
