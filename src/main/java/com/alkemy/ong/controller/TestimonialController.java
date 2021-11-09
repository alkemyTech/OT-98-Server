package com.alkemy.ong.controller;


import com.alkemy.ong.common.PaginatedResultsHeaderUtils;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.PageOutOfRangeException;
import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.request.CreateTestimonialRequest;
import com.alkemy.ong.model.response.CreateTestimonialResponse;
import com.alkemy.ong.model.response.TestimonialResponse;
import com.alkemy.ong.service.abstraction.ICreateTestimonialService;
import com.alkemy.ong.service.abstraction.IListTestimonialsService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  private ConvertUtils convertUtils;

  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CreateTestimonialResponse> create(@Valid @RequestBody
      CreateTestimonialRequest createTestimonialRequest) {
    Testimonial testimonial = createTestimonialService.create(createTestimonialRequest);
    CreateTestimonialResponse createTestimonialResponse = convertUtils.toResponse(testimonial);
    return new ResponseEntity<>(createTestimonialResponse, HttpStatus.CREATED);

  }

  @GetMapping(params = "page", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getList(@RequestParam("page") int page, UriComponentsBuilder uriBuilder,
      HttpServletResponse response) throws PageOutOfRangeException {

    Page<Testimonial> getList = testimonialsService.getList(page);

    paginatedResultsHeaderUtils.addLinkHeaderOnPagedResult(uriBuilder, response, page,
        getList.getTotalPages(), "/testimonials");

    return new ResponseEntity<>(convertUtils.listToTestimonialResponse(getList.getContent()),
        HttpStatus.OK);
  }

}
