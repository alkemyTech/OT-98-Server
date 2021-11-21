package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.ExternalServiceException;
import com.alkemy.ong.model.entity.Slide;
import com.alkemy.ong.model.request.CreateSlideRequest;
import com.alkemy.ong.model.request.SlideDetailsRequest;
import com.alkemy.ong.model.response.DetailsSlideResponse;
import com.alkemy.ong.service.abstraction.ICreateSlideService;
import com.alkemy.ong.model.response.ListSlidesResponse;
import com.alkemy.ong.service.abstraction.IDeleteSlideService;
import com.alkemy.ong.service.abstraction.IGetSlideService;
import com.alkemy.ong.service.abstraction.IListSlidesService;
import com.alkemy.ong.service.abstraction.IUpdateSlideService;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slides")
public class SlideController {

  @Autowired
  private IDeleteSlideService deleteSlideService;

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  private IListSlidesService listSlidesService;

  @Autowired
  private ICreateSlideService createSlideService;

  @Autowired
  private IGetSlideService getSlideService;

  @Autowired
  private IUpdateSlideService updateSlideService;

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") long id) throws EntityNotFoundException {
    deleteSlideService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListSlidesResponse> list() {
    ListSlidesResponse listSlidesResponse = listSlidesService.list();
    return new ResponseEntity<>(listSlidesResponse, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DetailsSlideResponse> getBy(@PathVariable("id") long id) {
    Slide slide = getSlideService.getBy(id);
    return new ResponseEntity<>(convertUtils.toResponse(slide), HttpStatus.OK);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DetailsSlideResponse> create(
      @RequestHeader HttpHeaders headers,
      @RequestBody(required = true) @Valid CreateSlideRequest createSlideRequest)
      throws EntityNotFoundException, ExternalServiceException {
    createSlideRequest.setImageContentType(headers.getFirst("Content-Type-Image"));
    createSlideRequest.setFileName(headers.getFirst("File-Name"));
    DetailsSlideResponse createSlideResponse = convertUtils.toResponse(
        createSlideService.create(createSlideRequest));
    return new ResponseEntity<>(createSlideResponse, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable("id") long id,
      @RequestBody SlideDetailsRequest slideDetailsRequest) {
    Slide slide = updateSlideService.update(id, slideDetailsRequest);
    DetailsSlideResponse detailsSlideResponse = convertUtils.toResponse(slide);
    return new ResponseEntity<>(detailsSlideResponse, HttpStatus.CREATED);
  }
}

