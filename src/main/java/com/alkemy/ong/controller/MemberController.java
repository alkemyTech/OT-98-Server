package com.alkemy.ong.controller;

import com.alkemy.ong.model.response.ListMemberResponse;
import com.alkemy.ong.service.abstraction.IDeleteMembersService;
import com.alkemy.ong.service.abstraction.IListMembersService;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  @Autowired
  IListMembersService listMembersService;

  @Autowired
  IDeleteMembersService deleteMembersService;

  @GetMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListMemberResponse> list() {
    return new ResponseEntity<>(listMembersService.list(), HttpStatus.OK);
  }

  @DeleteMapping(value = "/members/{id}")
  public ResponseEntity<?> deleteBy(@PathVariable Long id) throws EntityNotFoundException {
    deleteMembersService.deleteBy(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
