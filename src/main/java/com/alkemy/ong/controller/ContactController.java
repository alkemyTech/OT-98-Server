package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.SendEmailException;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.request.CreateContactRequest;
import com.alkemy.ong.model.response.DetailsContactResponse;
import com.alkemy.ong.model.response.ListContactResponse;
import com.alkemy.ong.service.abstraction.ICreateContactService;
import com.alkemy.ong.service.abstraction.IListContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactController {

  @Autowired
  ICreateContactService createContactService;

  @Autowired
  IListContactsService listContactsService;

  @Autowired
  ConvertUtils convertUtils;

  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DetailsContactResponse> create(
      @RequestBody CreateContactRequest createContactRequest) throws SendEmailException {
    Contact contact = createContactService.create(createContactRequest);
    DetailsContactResponse createContactResponse = this.convertUtils.toResponse(contact);
    return new ResponseEntity<>(createContactResponse, HttpStatus.CREATED);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListContactResponse> list() {
    return new ResponseEntity<>(listContactsService.list(), HttpStatus.OK);
  }

}
