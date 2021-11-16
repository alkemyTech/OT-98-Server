package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.EntityAlreadyExistException;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.request.CreateCategoryRequest;
import com.alkemy.ong.model.request.CreateMemberRequest;
import com.alkemy.ong.model.response.CreateCategoryResponse;
import com.alkemy.ong.model.response.CreateMemberResponse;
import com.alkemy.ong.model.response.ListMemberResponse;
import com.alkemy.ong.service.abstraction.ICreateMemberService;
import com.alkemy.ong.service.abstraction.IListMembersService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  @Autowired
  IListMembersService listMembersService;

  @Autowired
  ICreateMemberService createMemberService;

  @Autowired
  private ConvertUtils convertUtils;

  @GetMapping(value = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListMemberResponse> list() {
    return new ResponseEntity<>(listMembersService.list(), HttpStatus.OK);
  }

  @PostMapping(value = "/members", 
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CreateMemberResponse> create(
      @Valid @RequestBody CreateMemberRequest createMemberRequest) {
    Member member = createMemberService.create(createMemberRequest);
    CreateMemberResponse createMemberResponse = convertUtils.MemberToResponse(member);
    return new ResponseEntity<>(createMemberResponse, HttpStatus.CREATED);
  }

}
