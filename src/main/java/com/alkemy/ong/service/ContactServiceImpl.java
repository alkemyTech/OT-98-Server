package com.alkemy.ong.service;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.request.CreateContactRequest;
import com.alkemy.ong.model.response.DetailsContactResponse;
import com.alkemy.ong.model.response.ListContactResponse;
import com.alkemy.ong.repository.IContactRepository;
import com.alkemy.ong.service.abstraction.ICreateContactService;
import com.alkemy.ong.service.abstraction.IListContactsService;
import java.sql.Date;
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
    contact.setDeletedAt(new Date(266241));
    return contactRepository.save(contact);
  }

  @Override
  public ListContactResponse list() {
    List<Contact> contacts = contactRepository.findAll();
    List<DetailsContactResponse> detailsContactResponses = convertUtils.toResponse(contacts);
    return new ListContactResponse(detailsContactResponses);
  }
}
