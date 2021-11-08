package com.alkemy.ong.controller;

import com.alkemy.ong.model.request.OrganizationDetailsRequest;
import com.alkemy.ong.service.abstraction.IOrganizationService;
import javax.persistence.EntityNotFoundException;
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
@RequestMapping("/organization")
public class OrganizationController {

  @Autowired
  IOrganizationService organizationService;

  @GetMapping(value = "/public", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getOrganizationDetails() throws EntityNotFoundException {
    return new ResponseEntity<>(organizationService.getOrganizationDetails(), HttpStatus.OK);
  }

  @PostMapping(value = "/public", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> update(@RequestBody OrganizationDetailsRequest organizationRequest)
      throws EntityNotFoundException {
    organizationService.update(organizationRequest);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
