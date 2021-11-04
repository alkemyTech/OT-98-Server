package com.alkemy.ong.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.CreateActivityRequest;
import com.alkemy.ong.model.response.CreateActivityResponse;
import com.alkemy.ong.service.abstraction.ICreateActivityService;

@RestController
public class ActivityController {

  @Autowired
  ICreateActivityService iCreateActivityService;

  @Autowired
  private ConvertUtils convertUtils;


  @PostMapping(value = "/activities", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CreateActivityResponse> create(
      @Valid @RequestBody CreateActivityRequest createActivityRequest) {
    Activity activity = iCreateActivityService.create(createActivityRequest);
    CreateActivityResponse createActivityResponse = convertUtils.toResponse(activity);
    return new ResponseEntity<>(createActivityResponse, HttpStatus.CREATED);
  }
}
