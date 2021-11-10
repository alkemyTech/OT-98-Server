package com.alkemy.ong.controller;

import com.alkemy.ong.model.response.ListMemberResponse;
import com.alkemy.ong.service.abstraction.IListMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  @Autowired
  IListMembersService iListMembersService;

  @GetMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListMemberResponse> list() {
    return new ResponseEntity<>(iListMembersService.list(), HttpStatus.OK);
  }

}
