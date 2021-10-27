package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.repository.IContactRepository;
import com.alkemy.ong.service.abstraction.IContactService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService implements IContactService {
  @Autowired
  private IContactRepository iContactRepository;


  @Override
  public List<Contact> findAll() {
    List<Contact> contactList = this.iContactRepository.findAll();
    return contactList;
  }

  @Override
  public Contact save(Contact contact) {
    return this.iContactRepository.save(contact);
  }
}
