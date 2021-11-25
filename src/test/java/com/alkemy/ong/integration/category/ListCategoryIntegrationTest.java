package com.alkemy.ong.integration.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.alkemy.ong.common.PaginatedResultsHeaderUtils;
import com.alkemy.ong.common.PaginationUtils;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CreateCategoryRequest;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.ListCategoryResponse;
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
public class ListCategoryIntegrationTest extends AbstractBaseCategoryIntegrationTest {

  private final String PATH = "/categories?page=";

  @Test
  public void shouldReturnForbiddenWhenUserIsNotAdmin() {
    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort(PATH),
        HttpMethod.GET, new HttpEntity<>(headers), Object.class);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  public void shouldReturnBadRequestWhenPageOutOfRange() {
    int page = 2;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Category> categories = stubListCategory(PaginatedResultsHeaderUtils.PAGE_SIZE);
    Page<Category> categoryPage = new PageImpl<>(categories);

    when(categoryRepository.findBySoftDeleteFalse(eq(pageable))).thenReturn(categoryPage);

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<CreateCategoryRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH + page),
        HttpMethod.GET, entity, ErrorResponse.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(response.getBody().getMessage(), "Page " + page + " out of range");
  }


  @Test
  public void shouldListCategorySuccessfullyWithoutPrevAndNext() {
    int page = 0;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Category> categories = stubListCategory(PaginatedResultsHeaderUtils.PAGE_SIZE);
    Page<Category> categoryPage =
        new PageImpl<>(categories, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE);

    when(categoryRepository.findBySoftDeleteFalse(eq(pageable))).thenReturn(categoryPage);

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<CreateCategoryRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListCategoryResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity,
            ListCategoryResponse.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertFalse(response.getBody().getCategories().isEmpty());
    assertTrue((PaginationUtils.getLink(response.getHeaders())).isBlank());
  }


  @Test
  public void shouldListCategorySuccessfullyOnlyWithNext() {
    int page = 0;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Category> categories = stubListCategory(PaginatedResultsHeaderUtils.PAGE_SIZE * 2);
    Page<Category> categoryPage =
        new PageImpl<>(categories, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 2);

    when(categoryRepository.findBySoftDeleteFalse(eq(pageable))).thenReturn(categoryPage);

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<CreateCategoryRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListCategoryResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity,
            ListCategoryResponse.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertFalse(response.getBody().getCategories().isEmpty());

    String link = PaginationUtils.getLink(response.getHeaders());
    String nextURI = PaginationUtils.extractURIByRel(link, "next");
    String prevURI = PaginationUtils.extractURIByRel(link, "prev");
    assertEquals(nextURI, createURLWithPort(PATH + (page + 1)));
    assertNull(prevURI);
  }

  @Test
  public void shouldListCategorySuccessfullyOnlyWithPrev() {
    int page = 1;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Category> categories = stubListCategory(PaginatedResultsHeaderUtils.PAGE_SIZE * 2);
    Page<Category> categoryPage =
        new PageImpl<>(categories, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 2);

    when(categoryRepository.findBySoftDeleteFalse(eq(pageable))).thenReturn(categoryPage);

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<CreateCategoryRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListCategoryResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity,
            ListCategoryResponse.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertFalse(response.getBody().getCategories().isEmpty());

    String link = PaginationUtils.getLink(response.getHeaders());
    String nextURI = PaginationUtils.extractURIByRel(link, "next");
    String prevURI = PaginationUtils.extractURIByRel(link, "prev");
    assertNull(nextURI);
    assertEquals(prevURI, createURLWithPort(PATH + (page - 1)));
  }

  @Test
  public void shouldListCategorySuccessfullyWithNextAndPrev() {
    int page = 1;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Category> categories = stubListCategory(PaginatedResultsHeaderUtils.PAGE_SIZE * 3);
    Page<Category> categoryPage =
        new PageImpl<>(categories, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 3);

    when(categoryRepository.findBySoftDeleteFalse(eq(pageable))).thenReturn(categoryPage);

    login(ApplicationRole.ADMIN.getFullRoleName());

    HttpEntity<CreateCategoryRequest> entity = new HttpEntity<>(headers);
    ResponseEntity<ListCategoryResponse> response = restTemplate
        .exchange(createURLWithPort(PATH + page), HttpMethod.GET, entity,
            ListCategoryResponse.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertFalse(response.getBody().getCategories().isEmpty());

    String linkHeader = PaginationUtils.getLink(response.getHeaders());
    String nextURI = PaginationUtils.extractURIByRel(linkHeader, "next");
    String prevURI = PaginationUtils.extractURIByRel(linkHeader, "prev");
    assertEquals(nextURI, createURLWithPort(PATH + (page + 1)));
    assertEquals(prevURI, createURLWithPort(PATH + (page - 1)));
  }

}
