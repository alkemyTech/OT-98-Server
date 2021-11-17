package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.ActivityDetailsRequest;
import com.alkemy.ong.model.response.ActivityDetailsResponse;
import com.alkemy.ong.service.abstraction.ICreateActivityService;
import com.alkemy.ong.service.abstraction.IUpdateActivityService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

  @Autowired
  ICreateActivityService createActivityService;

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  IUpdateActivityService updateActivityService;

  @PostMapping(value = "/activities",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ActivityDetailsResponse> create(
      @Valid @RequestBody ActivityDetailsRequest activityDetailsRequest) {
    Activity activity = createActivityService.create(activityDetailsRequest);
    ActivityDetailsResponse activityDetailsResponse = convertUtils.toResponse(activity);
    return new ResponseEntity<>(activityDetailsResponse, HttpStatus.CREATED);
  }

  @PutMapping(value = "/activities/{id}")
  public ResponseEntity<ActivityDetailsResponse> update(@PathVariable long id,
      @RequestBody ActivityDetailsRequest activityDetailsRequest) {

    Activity activity = updateActivityService.update(id, activityDetailsRequest);
    ActivityDetailsResponse activityDetailsResponse = convertUtils.toResponse(activity);
    return new ResponseEntity<>(activityDetailsResponse, HttpStatus.OK);

  }


}
