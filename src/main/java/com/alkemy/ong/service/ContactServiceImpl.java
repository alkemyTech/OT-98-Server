package com.alkemy.ong.service;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.common.mail.EmailHelper;
import com.alkemy.ong.exception.SendEmailException;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.request.ContactTemplateEmail;
import com.alkemy.ong.model.request.CreateContactRequest;
import com.alkemy.ong.model.response.DetailsContactResponse;
import com.alkemy.ong.model.response.ListContactResponse;
import com.alkemy.ong.repository.IContactRepository;
import com.alkemy.ong.service.abstraction.ICreateContactService;
import com.alkemy.ong.service.abstraction.IListContactsService;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContactServiceImpl implements ICreateContactService, IListContactsService {

  private static final Date ACTIVE_CONTACT = null;

  @Autowired
  IContactRepository contactRepository;

  @Autowired
  ConvertUtils convertUtils;

  @Autowired
  EmailHelper emailHelper;

  @Override
  public Contact create(CreateContactRequest createContactRequest) {
    Contact contact = new Contact();
    contact.setName(createContactRequest.getName());
    contact.setPhone(createContactRequest.getPhone());
    contact.setEmail(createContactRequest.getEmail());
    contact.setMessage(createContactRequest.getMessage());
    contact.setDeletedAt(ACTIVE_CONTACT);
    try {
      ContactTemplateEmail contactTemplateEmail = new ContactTemplateEmail();
      contactTemplateEmail.setEmail(createContactRequest.getEmail());
      emailHelper.send(contactTemplateEmail);
    } catch (SendEmailException s) {
      log.info(s.getMessage());
    }
    return contactRepository.save(contact);
  }

  @Override
  public ListContactResponse list() {
    List<Contact> contacts = contactRepository.findByDeletedAtIsNull();
    List<DetailsContactResponse> detailsContactResponses = convertUtils.toResponse(contacts);
    return new ListContactResponse(detailsContactResponses);
  }

}
