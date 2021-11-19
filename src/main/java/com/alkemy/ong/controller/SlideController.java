package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.ExternalServiceException;
import com.alkemy.ong.model.request.CreateSlideRequest;
import com.alkemy.ong.model.response.DetailsSlideResponse;
import com.alkemy.ong.service.abstraction.ICreateSlideService;
import com.alkemy.ong.model.response.ListSlidesResponse;
import com.alkemy.ong.service.abstraction.IDeleteSlideService;
import com.alkemy.ong.service.abstraction.IListSlidesService;
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

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DetailsSlideResponse> create(
      @RequestHeader HttpHeaders headers,
      @RequestBody(required = true) @Valid CreateSlideRequest createSlideRequest)
      throws EntityNotFoundException, ExternalServiceException, NullPointerException {
    DetailsSlideResponse createSlideResponse = convertUtils.toResponse(
        createSlideService.create(headers.getFirst("Content-Type-Image"), headers.getFirst("File-Name"), createSlideRequest));
    return new ResponseEntity<>(createSlideResponse, HttpStatus.CREATED);
  }
}

