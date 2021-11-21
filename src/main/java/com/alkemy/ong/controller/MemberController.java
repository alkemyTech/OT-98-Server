package com.alkemy.ong.controller;

import com.alkemy.ong.common.PaginatedResultsHeaderUtils;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.PageOutOfRangeException;
import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.request.ActivityDetailsRequest;
import com.alkemy.ong.model.request.DetailsMemberRequest;
import com.alkemy.ong.model.response.ActivityDetailsResponse;
import com.alkemy.ong.model.response.DetailsMemberResponse;
import com.alkemy.ong.model.response.ListMemberResponse;
import com.alkemy.ong.service.abstraction.ICreateMemberService;
import com.alkemy.ong.service.abstraction.IDeleteMembersService;
import com.alkemy.ong.service.abstraction.IListMembersService;
import com.alkemy.ong.service.abstraction.IUpdateMembersService;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class MemberController {

  @Autowired
  IListMembersService listMembersService;

  @Autowired
  ICreateMemberService createMemberService;

  @Autowired
  IDeleteMembersService deleteMembersService;

  @Autowired
  IUpdateMembersService updateMembersService;
  
  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  private PaginatedResultsHeaderUtils paginatedResultsHeaderUtils;

  @GetMapping(params = "page", value = "/members")
  public ResponseEntity<ListMemberResponse> list(@RequestParam("page") int page,
      UriComponentsBuilder uriBuilder,
      HttpServletResponse response) throws PageOutOfRangeException {
    Page<Member> memberPage = listMembersService.list(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    paginatedResultsHeaderUtils.addLinkHeaderOnPagedResult(
        uriBuilder,
        response,
        page,
        memberPage.getTotalPages(),
        "/members"
    );
    ListMemberResponse toResponse = convertUtils.toResponseList(memberPage.getContent());
    return new ResponseEntity<>(toResponse, HttpStatus.OK);
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

  @PutMapping(value = "/members/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DetailsMemberResponse> update(@PathVariable long id,
      @Valid @RequestBody DetailsMemberRequest detailsMemberRequest) {
    Member member = updateMembersService.update(detailsMemberRequest, id);
    DetailsMemberResponse detailsMemberResponse = convertUtils.memberToResponse(member);
    return new ResponseEntity<>(detailsMemberResponse, HttpStatus.OK);
  }

}
