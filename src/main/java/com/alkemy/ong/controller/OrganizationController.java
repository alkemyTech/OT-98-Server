package com.alkemy.ong.controller;

import com.alkemy.ong.model.response.OrganizationResponse;
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
  public ResponseEntity<?> publicInformation() throws Exception {
    try {
      OrganizationResponse organization = organizationService.getOrganization();
      return new ResponseEntity<>(organization, HttpStatus.OK);
    }catch (Exception e){
      return ResponseEntity.badRequest().body("");
    }


  }

}
