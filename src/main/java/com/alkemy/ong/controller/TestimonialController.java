package com.alkemy.ong.controller;


import com.alkemy.ong.common.PaginatedResultsHeaderUtils;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.PageOutOfRangeException;
import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.request.TestimonialDetailsRequest;
import com.alkemy.ong.model.response.CreateTestimonialResponse;
import com.alkemy.ong.model.response.ListTestimonialResponse;
import com.alkemy.ong.service.abstraction.ICreateTestimonialService;
import com.alkemy.ong.service.abstraction.IDeleteTestimonialService;
import com.alkemy.ong.service.abstraction.IListTestimonialsService;
import com.alkemy.ong.service.abstraction.IUpdateTestimonialService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

  @Autowired
  private IListTestimonialsService testimonialsService;

  @Autowired
  private PaginatedResultsHeaderUtils paginatedResultsHeaderUtils;

  @Autowired
  private ICreateTestimonialService createTestimonialService;

  @Autowired
  private IUpdateTestimonialService updateTestimonialService;

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  private IDeleteTestimonialService deleteTestimonialService;

  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CreateTestimonialResponse> create(@Valid @RequestBody
      TestimonialDetailsRequest createTestimonialRequest) {
    Testimonial testimonial = createTestimonialService.create(createTestimonialRequest);
    CreateTestimonialResponse createTestimonialResponse = convertUtils.toResponse(testimonial);
    return new ResponseEntity<>(createTestimonialResponse, HttpStatus.CREATED);

  }

  @GetMapping(params = "page", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@RequestParam("page") int page, UriComponentsBuilder uriBuilder,
      HttpServletResponse response) throws PageOutOfRangeException {
    Page<Testimonial> pageResponse = testimonialsService.list(page,
        PaginatedResultsHeaderUtils.PAGE_SIZE);
    paginatedResultsHeaderUtils.addLinkHeaderOnPagedResult(uriBuilder,
        response,
        page,
        pageResponse.getTotalPages(),
        "/testimonials");
    ListTestimonialResponse toResponse = convertUtils.listTestimonialToResponse(
        pageResponse.getContent());
    return new ResponseEntity<>(toResponse, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deleteBy(@PathVariable Long id) throws EntityNotFoundException {
    deleteTestimonialService.deleteBy(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CreateTestimonialResponse> update(@PathVariable("id") Long id,
      @RequestBody(required = true) @Valid TestimonialDetailsRequest createTestimonialRequest) {
    CreateTestimonialResponse testimonialDetailsResponse =
        convertUtils.toResponse(updateTestimonialService.update(createTestimonialRequest, id));
    return new ResponseEntity<>(testimonialDetailsResponse, HttpStatus.OK);
  }
}
