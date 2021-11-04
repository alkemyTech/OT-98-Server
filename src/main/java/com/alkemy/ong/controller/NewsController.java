package com.alkemy.ong.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.EntityNotExistException;
import com.alkemy.ong.model.request.CreateNewsRequest;
import com.alkemy.ong.model.response.CreateNewsResponse;
import com.alkemy.ong.service.abstraction.ICreateNewsService;

@RestController
@RequestMapping("/news")
public class NewsController {

  @Autowired
  private ICreateNewsService createNewsService;

  @Autowired
  private ConvertUtils convertUtils;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CreateNewsResponse> create(
      @RequestBody(required = true) @Valid CreateNewsRequest createNewsRequest)
      throws EntityNotExistException {
    return ResponseEntity.ok(convertUtils.toResponse(createNewsService.create(createNewsRequest)));
  }
}
