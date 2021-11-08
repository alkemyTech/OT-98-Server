package com.alkemy.ong.service;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.request.CreateContactRequest;
import com.alkemy.ong.model.response.CreateContactResponse;
import com.alkemy.ong.repository.IContactRepository;
import com.alkemy.ong.service.abstraction.ICreateContactService;
import com.alkemy.ong.service.abstraction.IListContactsService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ICreateContactService, IListContactsService {

  @Autowired
  IContactRepository contactRepository;

  @Autowired
  ConvertUtils convertUtils;

  @Override
  public Contact create(CreateContactRequest createContactRequest) {
    Contact contact = new Contact();
    contact.setName(createContactRequest.getName());
    contact.setPhone(createContactRequest.getPhone());
    contact.setEmail(createContactRequest.getEmail());
    contact.setMessage(createContactRequest.getMessage());
    return contactRepository.save(contact);
  }

  @Override
  public List<CreateContactResponse> contacts() {
    List<Contact> contacts = contactRepository.findAll();
    List<CreateContactResponse> createContactResponses = new ArrayList<>();
    contacts.forEach(contact -> {
      createContactResponses.add(
          convertUtils.toResponse(contact)
      );
    });
    return createContactResponses;
  }
}
