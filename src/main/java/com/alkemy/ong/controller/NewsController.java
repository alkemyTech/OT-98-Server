package com.alkemy.ong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alkemy.ong.exception.EntityNotExistException;
import com.alkemy.ong.model.request.CreateNewsRequest;
import com.alkemy.ong.service.abstraction.ICreateNewsService;

@RestController
@RequestMapping("/news")
public class NewsController {

  @Autowired
  private ICreateNewsService createNewsService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody CreateNewsRequest createNewsRequest)
      throws EntityNotExistException {
    return ResponseEntity.ok(createNewsService.create(createNewsRequest));
  }
}
