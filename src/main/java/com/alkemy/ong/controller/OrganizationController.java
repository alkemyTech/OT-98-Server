package com.alkemy.ong.controller;

import com.alkemy.ong.exception.OrganizationNotFoundException;
import com.alkemy.ong.service.OrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

  @Autowired
  OrganizationServiceImpl organizationService;

  @GetMapping("/public")
  public ResponseEntity<?> getOrganizationDetails() throws OrganizationNotFoundException {
      return new ResponseEntity<>(organizationService.getOrganizationDetails().getBody(), HttpStatus.OK);
  }
}
