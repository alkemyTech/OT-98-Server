package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Contact;
import java.util.List;

public interface IContactService {
  public List<Contact> findAll();
  public Contact save(Contact contact);
}


