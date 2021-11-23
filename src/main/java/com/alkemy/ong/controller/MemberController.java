package com.alkemy.ong.controller;

import com.alkemy.ong.common.PaginatedResultsHeaderUtils;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.common.messages.DocumentationMessages;
import com.alkemy.ong.exception.PageOutOfRangeException;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.request.DetailsMemberRequest;
import com.alkemy.ong.model.response.DetailsMemberResponse;
import com.alkemy.ong.model.response.ListMemberResponse;
import com.alkemy.ong.service.abstraction.ICreateMemberService;
import com.alkemy.ong.service.abstraction.IDeleteMembersService;
import com.alkemy.ong.service.abstraction.IListMembersService;
import com.alkemy.ong.service.abstraction.IUpdateMembersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = DocumentationMessages.MEMBER_CONTROLLER,
    description = DocumentationMessages.MEMBER_CONTROLLER_DESCRIPTION)
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
  @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_LIST)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_200_DESCRIPTION),
      @ApiResponse(responseCode = "400",
          description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_400_DESCRIPTION),
      @ApiResponse(responseCode = "403",
          description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_403_DESCRIPTION)
  })
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
  @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_CREATE)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201",
          description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_201_DESCRIPTION),
      @ApiResponse(responseCode = "403",
          description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_403_DESCRIPTION)
  })
  public ResponseEntity<DetailsMemberResponse> create(
      @Valid @RequestBody DetailsMemberRequest detailsMemberRequest) {
    Member member = createMemberService.create(detailsMemberRequest);
    DetailsMemberResponse detailsMemberResponse = convertUtils.memberToResponse(member);
    return new ResponseEntity<>(detailsMemberResponse, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/members/{id}")
  @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_DELETE)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204",
          description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_204_DESCRIPTION),
      @ApiResponse(responseCode = "403",
          description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_403_DESCRIPTION),
      @ApiResponse(responseCode = "404",
          description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<Long> deleteBy(@PathVariable long id) throws EntityNotFoundException {
    deleteMembersService.deleteBy(id);
    return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);

  }

  @PutMapping(value = "/members/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = DocumentationMessages.MEMBER_CONTROLLER_SUMMARY_UPDATE)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_200_DESCRIPTION),
      @ApiResponse(responseCode = "403",
          description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_403_DESCRIPTION),
      @ApiResponse(responseCode = "404",
          description = DocumentationMessages.MEMBER_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<DetailsMemberResponse> update(@PathVariable long id,
      @Valid @RequestBody DetailsMemberRequest detailsMemberRequest) {
    Member member = updateMembersService.update(detailsMemberRequest, id);
    DetailsMemberResponse detailsMemberResponse = convertUtils.memberToResponse(member);
    return new ResponseEntity<>(detailsMemberResponse, HttpStatus.OK);
  }

}
