package com.alkemy.ong.integration.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.common.PaginatedResultsHeaderUtils;
import com.alkemy.ong.common.PaginationUtils;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.request.DetailsMemberRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.ListMemberResponse;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListMemberIntegrationTest extends AbstractBaseMemberIntegrationTest {

  private final String PATH = "/members?page=";

  @Test
  public void shouldReturnForbiddenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.GET, new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenPageOutOfRange() {
    int page = 2;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Member> members = stubMember(PaginatedResultsHeaderUtils.PAGE_SIZE);
    Page<Member> pagedMember = new PageImpl<>(members);

    when(memberRepository.findBySoftDeleteFalse(eq(pageable))).thenReturn(pagedMember);

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<DetailsMemberRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH + page),
        HttpMethod.GET, entity, ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertEquals(response.getBody().getMessage(), "Page " + page + " out of range");
  }


  @Test
  public void shouldListMemberSuccessfullyWithoutPrevAndNext() {
    int page = 0;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Member> members = stubMember(PaginatedResultsHeaderUtils.PAGE_SIZE);
    Page<Member> pagedNews =
        new PageImpl<>(members, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE);

    when(memberRepository.findBySoftDeleteFalse(eq(pageable))).thenReturn(pagedNews);

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<DetailsMemberRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListMemberResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity, ListMemberResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getMembers().isEmpty());
    assertTrue((PaginationUtils.getLink(response.getHeaders())).isBlank());
  }


  @Test
  public void shouldListMemberSuccessfullyOnlyWithNext() {
    int page = 0;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Member> members = stubMember(PaginatedResultsHeaderUtils.PAGE_SIZE * 2);
    Page<Member> pagedMember =
        new PageImpl<>(members, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 2);

    when(memberRepository.findBySoftDeleteFalse(eq(pageable))).thenReturn(pagedMember);

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<DetailsMemberRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListMemberResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity, ListMemberResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getMembers().isEmpty());

    String link = PaginationUtils.getLink(response.getHeaders());
    String nextURI = PaginationUtils.extractURIByRel(link, "next");
    String prevURI = PaginationUtils.extractURIByRel(link, "prev");
    assertEquals(nextURI, createURLWithPort(PATH + (page + 1)));
    assertNull(prevURI);
  }

  @Test
  public void shouldListMemberSuccessfullyOnlyWithPrev() {
    int page = 1;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Member> members = stubMember(PaginatedResultsHeaderUtils.PAGE_SIZE * 2);
    Page<Member> pagedMember =
        new PageImpl<>(members, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 2);

    when(memberRepository.findBySoftDeleteFalse(eq(pageable))).thenReturn(pagedMember);

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<DetailsMemberRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListMemberResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity, ListMemberResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getMembers().isEmpty());

    String link = PaginationUtils.getLink(response.getHeaders());
    String nextURI = PaginationUtils.extractURIByRel(link, "next");
    String prevURI = PaginationUtils.extractURIByRel(link, "prev");
    assertNull(nextURI);
    assertEquals(prevURI, createURLWithPort(PATH + (page - 1)));
  }

  @Test
  public void shouldListMemberSuccessfullyWithNextAndPrev() {
    int page = 1;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Member> members = stubMember(PaginatedResultsHeaderUtils.PAGE_SIZE * 3);
    Page<Member> pagedMember =
        new PageImpl<>(members, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 3);

    when(memberRepository.findBySoftDeleteFalse(eq(pageable))).thenReturn(pagedMember);

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<DetailsMemberRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListMemberResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity, ListMemberResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getMembers().isEmpty());

    String linkHeader = PaginationUtils.getLink(response.getHeaders());
    String nextURI = PaginationUtils.extractURIByRel(linkHeader, "next");
    String prevURI = PaginationUtils.extractURIByRel(linkHeader, "prev");
    assertEquals(nextURI, createURLWithPort(PATH + (page + 1)));
    assertEquals(prevURI, createURLWithPort(PATH + (page - 1)));
  }
}
