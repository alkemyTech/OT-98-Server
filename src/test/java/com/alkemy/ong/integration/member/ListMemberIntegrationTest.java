package com.alkemy.ong.integration.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.alkemy.ong.common.ListTestUtils;
import com.alkemy.ong.common.PaginatedResultsHeaderUtils;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.integration.news.AbstractBaseNewsIntegrationTest;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.request.DetailsMemberRequest;
import com.alkemy.ong.model.request.NewsDetailsRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.ListMemberResponse;
import com.alkemy.ong.model.response.ListNewsResponse;
import com.alkemy.ong.common.ListTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListMemberIntegrationTest extends AbstractBaseMemberIntegrationTest {

  private final String PATH = "/members?page=";


  ListTestUtils listTestUtils = new ListTestUtils();

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
    assertTrue((response.getHeaders().getFirst("Link")).isBlank());
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

    String nextURI = listTestUtils.extractURIByRel(response.getHeaders().getFirst("Link"), "next");
    String prevURI = listTestUtils.extractURIByRel(response.getHeaders().getFirst("Link"), "prev");
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

    String nextURI = listTestUtils.extractURIByRel(response.getHeaders().getFirst("Link"), "next");
    String prevURI = listTestUtils.extractURIByRel(response.getHeaders().getFirst("Link"), "prev");
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

    String nextURI = listTestUtils.extractURIByRel(response.getHeaders().getFirst("Link"), "next");
    String prevURI = listTestUtils.extractURIByRel(response.getHeaders().getFirst("Link"), "prev");
    assertEquals(nextURI, createURLWithPort(PATH + (page + 1)));
    assertEquals(prevURI, createURLWithPort(PATH + (page - 1)));
  }
}
