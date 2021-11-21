package com.alkemy.ong.integration.testimonials;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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
import com.alkemy.ong.common.PaginatedResultsHeaderUtils;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.response.ErrorResponse;
import com.alkemy.ong.model.response.ListTestimonialResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListTestimonialsIntegrationTest extends AbstractBaseTestimonialsIntegrationTest {

  private final String PATH = "/testimonials?page=";

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
    List<Testimonial> testimonials = stubTestimonial(PaginatedResultsHeaderUtils.PAGE_SIZE);
    Page<Testimonial> pagedTestimonials = new PageImpl<>(testimonials);

    when(testimonialRepository.findBySoftDeleteIsFalse(eq(pageable))).thenReturn(pagedTestimonials);

    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<ErrorResponse> response = restTemplate.exchange(createURLWithPort(PATH + page),
        HttpMethod.GET, new HttpEntity<>(headers), ErrorResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    assertEquals(response.getBody().getMessage(), "Page " + page + " out of range");
  }

  @Test
  public void shouldListTestimonialsSuccessfulWithoutPrevAndNext() {
    int page = 0;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Testimonial> testimonials = stubTestimonial(PaginatedResultsHeaderUtils.PAGE_SIZE);
    Page<Testimonial> pagedTestimonials =
        new PageImpl<>(testimonials, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE);

    when(testimonialRepository.findBySoftDeleteIsFalse(eq(pageable))).thenReturn(pagedTestimonials);

    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<ListTestimonialResponse> response =
        restTemplate.exchange(createURLWithPort(PATH + page), HttpMethod.GET,
            new HttpEntity<>(headers), ListTestimonialResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getTestimonials().isEmpty());
    assertTrue((response.getHeaders().getFirst("Link")).isBlank());
  }

  @Test
  public void shouldListTestimonialsSuccessfulOnlyWithNext() {
    int page = 0;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Testimonial> testimonials = stubTestimonial(PaginatedResultsHeaderUtils.PAGE_SIZE * 2);
    Page<Testimonial> pagedTestimonials =
        new PageImpl<>(testimonials, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 2);

    when(testimonialRepository.findBySoftDeleteIsFalse(eq(pageable))).thenReturn(pagedTestimonials);

    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<ListTestimonialResponse> response =
        restTemplate.exchange(createURLWithPort(PATH + page), HttpMethod.GET,
            new HttpEntity<>(headers), ListTestimonialResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getTestimonials().isEmpty());

    String nextURI = extractURIByRel(response.getHeaders().getFirst("Link"), "next");
    String prevURI = extractURIByRel(response.getHeaders().getFirst("Link"), "prev");
    assertEquals(nextURI, createURLWithPort(PATH + (page + 1)));
    assertNull(prevURI);
  }

  @Test
  public void shouldListTestimonialsSuccessfulOnlyWithPrev() {
    int page = 1;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Testimonial> testimonials = stubTestimonial(PaginatedResultsHeaderUtils.PAGE_SIZE * 2);
    Page<Testimonial> pagedTestimonials =
        new PageImpl<>(testimonials, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 2);

    when(testimonialRepository.findBySoftDeleteIsFalse(eq(pageable))).thenReturn(pagedTestimonials);

    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<ListTestimonialResponse> response =
        restTemplate.exchange(createURLWithPort(PATH + page), HttpMethod.GET,
            new HttpEntity<>(headers), ListTestimonialResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getTestimonials().isEmpty());

    String nextURI = extractURIByRel(response.getHeaders().getFirst("Link"), "next");
    String prevURI = extractURIByRel(response.getHeaders().getFirst("Link"), "prev");
    assertNull(nextURI);
    assertEquals(prevURI, createURLWithPort(PATH + (page - 1)));
  }

  @Test
  public void shouldListTestimonialsSuccessfulWithNextAndPrev() {
    int page = 1;
    Pageable pageable = PageRequest.of(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    List<Testimonial> testimonials = stubTestimonial(PaginatedResultsHeaderUtils.PAGE_SIZE * 3);
    Page<Testimonial> pagedTestimonials =
        new PageImpl<>(testimonials, pageable, PaginatedResultsHeaderUtils.PAGE_SIZE * 3);

    when(testimonialRepository.findBySoftDeleteIsFalse(eq(pageable))).thenReturn(pagedTestimonials);

    login(ApplicationRole.USER.getFullRoleName());

    ResponseEntity<ListTestimonialResponse> response =
        restTemplate.exchange(createURLWithPort(PATH + page), HttpMethod.GET,
            new HttpEntity<>(headers), ListTestimonialResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertFalse(response.getBody().getTestimonials().isEmpty());

    String nextURI = extractURIByRel(response.getHeaders().getFirst("Link"), "next");
    String prevURI = extractURIByRel(response.getHeaders().getFirst("Link"), "prev");
    assertEquals(nextURI, createURLWithPort(PATH + (page + 1)));
    assertEquals(prevURI, createURLWithPort(PATH + (page - 1)));
  }
}
