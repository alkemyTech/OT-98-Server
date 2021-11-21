package com.alkemy.ong.integration.news;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.common.PaginatedResultsHeaderUtils;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.request.NewsDetailsRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.ListNewsResponse;
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
public class ListNewsIntegrationTest extends AbstractBaseNewsIntegrationTest {

  private final String PATH = "/news?page=";

  @Test
  public void shouldReturnForbiddenWhenUserIsNotLogged() {
    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH + 0),
        HttpMethod.GET, new HttpEntity<>(headers), Object.class);

    assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  @Test
  public void shouldReturnBadRequestWhenPageOutOfRange() {
    int page = 2;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<News> news = stubNews(PaginatedResultsHeaderUtils.PAGE_SIZE);
    Page<News> pagedNews = new PageImpl<>(news);

    when(newsRepository.findBySoftDeleteIsFalse(eq(pageable))).thenReturn(pagedNews);

    login(ApplicationRole.USER.getFullRoleName());

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH + page),
        HttpMethod.GET, entity, ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertEquals(response.getBody().getMessage(), "Page " + page + " out of range");
  }

  @Test
  public void shouldListNewsSuccessfulWithoutPrevAndNext() {
    int page = 0;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<News> news = stubNews(PaginatedResultsHeaderUtils.PAGE_SIZE);
    Page<News> pagedNews = new PageImpl<>(news, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE);

    when(newsRepository.findBySoftDeleteIsFalse(eq(pageable))).thenReturn(pagedNews);

    login(ApplicationRole.USER.getFullRoleName());

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListNewsResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity, ListNewsResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getNews().isEmpty());
    assertTrue((response.getHeaders().getFirst("Link")).isBlank());
  }

  @Test
  public void shouldListNewsSuccessfulOnlyWithNext() {
    int page = 0;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<News> news = stubNews(PaginatedResultsHeaderUtils.PAGE_SIZE * 2);
    Page<News> pagedNews =
        new PageImpl<>(news, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 2);

    when(newsRepository.findBySoftDeleteIsFalse(eq(pageable))).thenReturn(pagedNews);

    login(ApplicationRole.USER.getFullRoleName());

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListNewsResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity, ListNewsResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getNews().isEmpty());

    String nextURI = extractURIByRel(response.getHeaders().getFirst("Link"), "next");
    String prevURI = extractURIByRel(response.getHeaders().getFirst("Link"), "prev");
    assertEquals(nextURI, createURLWithPort(PATH + (page + 1)));
    assertNull(prevURI);
  }

  @Test
  public void shouldListNewsSuccessfulOnlyWithPrev() {
    int page = 1;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<News> news = stubNews(PaginatedResultsHeaderUtils.PAGE_SIZE * 2);
    Page<News> pagedNews =
        new PageImpl<>(news, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 2);

    when(newsRepository.findBySoftDeleteIsFalse(eq(pageable))).thenReturn(pagedNews);

    login(ApplicationRole.USER.getFullRoleName());

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListNewsResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity, ListNewsResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getNews().isEmpty());

    String nextURI = extractURIByRel(response.getHeaders().getFirst("Link"), "next");
    String prevURI = extractURIByRel(response.getHeaders().getFirst("Link"), "prev");
    assertNull(nextURI);
    assertEquals(prevURI, createURLWithPort(PATH + (page - 1)));
  }

  @Test
  public void shouldListNewsSuccessfulWithNextAndPrev() {
    int page = 1;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<News> news = stubNews(PaginatedResultsHeaderUtils.PAGE_SIZE * 3);
    Page<News> pagedNews =
        new PageImpl<>(news, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 3);

    when(newsRepository.findBySoftDeleteIsFalse(eq(pageable))).thenReturn(pagedNews);

    login(ApplicationRole.USER.getFullRoleName());

    HttpEntity<NewsDetailsRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListNewsResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity, ListNewsResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getNews().isEmpty());

    String nextURI = extractURIByRel(response.getHeaders().getFirst("Link"), "next");
    String prevURI = extractURIByRel(response.getHeaders().getFirst("Link"), "prev");
    assertEquals(nextURI, createURLWithPort(PATH + (page + 1)));
    assertEquals(prevURI, createURLWithPort(PATH + (page - 1)));
  }
}
