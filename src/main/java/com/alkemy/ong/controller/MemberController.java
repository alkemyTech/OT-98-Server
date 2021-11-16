package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.EntityAlreadyExistException;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.request.CreateCategoryRequest;
import com.alkemy.ong.model.request.DetailsMemberRequest;
import com.alkemy.ong.model.response.CreateCategoryResponse;
import com.alkemy.ong.model.response.DetailsMemberResponse;
import com.alkemy.ong.model.response.ListMemberResponse;
import com.alkemy.ong.service.abstraction.ICreateMemberService;
import com.alkemy.ong.service.abstraction.IListMembersService;
import javax.validation.Valid;
import com.alkemy.ong.service.abstraction.IDeleteMembersService;
import com.alkemy.ong.service.abstraction.IListMembersService;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  @Autowired
  IListMembersService listMembersService;

  @Autowired
  ICreateMemberService createMemberService;

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  IDeleteMembersService deleteMembersService;


  @GetMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListMemberResponse> list() {
    return new ResponseEntity<>(listMembersService.list(), HttpStatus.OK);
  }


  @PostMapping(value = "/members", 
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DetailsMemberResponse> create(
      @Valid @RequestBody DetailsMemberRequest detailsMemberRequest) {
    Member member = createMemberService.create(detailsMemberRequest);
    DetailsMemberResponse detailsMemberResponse = convertUtils.memberToResponse(member);
    return new ResponseEntity<>(detailsMemberResponse, HttpStatus.CREATED);
  }
  
  @DeleteMapping(value = "/members/{id}")
  public ResponseEntity<?> deleteBy(@PathVariable long id) throws EntityNotFoundException {
    deleteMembersService.deleteBy(id);
    return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

  }

}
