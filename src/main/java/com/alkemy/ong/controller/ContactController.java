package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.request.CreateContactRequest;
import com.alkemy.ong.model.response.CreateContactResponse;
import com.alkemy.ong.service.abstraction.ICreateContactService;
import com.alkemy.ong.service.abstraction.IListContactsService;
import java.util.List;
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
  public ResponseEntity<CreateContactResponse> create(
      @RequestBody CreateContactRequest createContactRequest) {
    Contact contact = createContactService.create(createContactRequest);
    CreateContactResponse createContactResponse = this.convertUtils.toResponse(contact);
    return new ResponseEntity<>(createContactResponse, HttpStatus.CREATED);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CreateContactResponse>> contacts() {
    return new ResponseEntity<>(listContactsService.contacts(), HttpStatus.OK);
  }
  
}
