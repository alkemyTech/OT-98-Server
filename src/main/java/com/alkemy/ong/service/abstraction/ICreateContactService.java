package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.SendEmailException;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.request.CreateContactRequest;

public interface ICreateContactService {

  Contact create(CreateContactRequest createContactRequest) throws SendEmailException;

}
