package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.ExternalServiceException;
import com.alkemy.ong.model.request.CreateSlideRequest;
import com.alkemy.ong.model.response.CreateSlideResponse;
import com.alkemy.ong.service.abstraction.ICreateSlideService;
import com.alkemy.ong.service.abstraction.IDeleteSlideService;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  private ICreateSlideService createSlideService;

  @Autowired
  private ConvertUtils convertUtils;

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") long id) throws EntityNotFoundException {
    deleteSlideService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(
      @RequestHeader(value = "content-Type", required = true) ContentType contentType,
      @RequestHeader(value = "fileName", required = false) String fileName,
      @RequestBody(required = true) @Valid CreateSlideRequest createSlideRequest)
      throws EntityNotFoundException, ExternalServiceException, NullPointerException {
    CreateSlideResponse createSlideResponse = convertUtils.createSlideToResponse(
        createSlideService.create(contentType, fileName, createSlideRequest));
    return new ResponseEntity<>(createSlideResponse, HttpStatus.CREATED);
  }
}
