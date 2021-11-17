package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.ActivityUpdateRequest;
import com.alkemy.ong.model.request.CreateActivityRequest;
import com.alkemy.ong.model.response.CreateActivityResponse;
import com.alkemy.ong.model.response.UpdateActivityResponse;
import com.alkemy.ong.service.abstraction.ICreateActivityService;
import com.alkemy.ong.service.abstraction.IUpdateActivityService;
import javax.validation.Valid;
import org.hibernate.sql.Update;
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
  public ResponseEntity<CreateActivityResponse> create(
      @Valid @RequestBody CreateActivityRequest createActivityRequest) {
    Activity activity = createActivityService.create(createActivityRequest);
    CreateActivityResponse createActivityResponse = convertUtils.toResponse(activity);
    return new ResponseEntity<>(createActivityResponse, HttpStatus.CREATED);
  }

  @PutMapping(value = "/activities/{id}")
  public ResponseEntity<UpdateActivityResponse> update(@PathVariable long id,
      @RequestBody ActivityUpdateRequest activityUpdateRequest) {

    Activity activity = updateActivityService.update(id, activityUpdateRequest);
    UpdateActivityResponse updateActivityResponse = convertUtils.toUpdateActivityResponse(activity);
    return new ResponseEntity<>(updateActivityResponse, HttpStatus.OK);

  }


}
