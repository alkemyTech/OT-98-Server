package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CategoryCreationRequest;
import com.alkemy.ong.model.response.CategoryCreationResponse;
import com.alkemy.ong.service.abstraction.ICategoryCreationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

  @Autowired
  private ICategoryCreationService categoryCreationService;

  @Autowired
  private ConvertUtils convertUtils;

  @PostMapping(value = "/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryCreationResponse> creation(
      @Valid @RequestBody CategoryCreationRequest categoryCreationRequest) {
    Category category = categoryCreationService.creation(categoryCreationRequest);
    CategoryCreationResponse categoryCreationResponse = convertUtils.toResponse(category);
    return new ResponseEntity<>(categoryCreationResponse, HttpStatus.CREATED);
  }

}
