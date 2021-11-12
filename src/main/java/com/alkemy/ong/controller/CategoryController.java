package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.EntityAlreadyExistException;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CreateCategoryRequest;
import com.alkemy.ong.model.response.CreateCategoryResponse;
import com.alkemy.ong.model.response.CategoriesResponse;
import com.alkemy.ong.model.response.ListCategoryResponse;
import com.alkemy.ong.service.abstraction.ICreateCategoryService;
import com.alkemy.ong.service.abstraction.IListCategoryService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

  @Autowired
  private ICreateCategoryService createCategoryService;

  @Autowired
  private IListCategoryService listCategoryService;

  @Autowired
  private ConvertUtils convertUtils;

  @PostMapping(value = "/categories",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CreateCategoryResponse> create(
      @Valid @RequestBody CreateCategoryRequest createCategoryRequest)
      throws EntityAlreadyExistException {
    Category category = createCategoryService.create(createCategoryRequest);
    CreateCategoryResponse createCategoryResponse = convertUtils.toResponse(category);
    return new ResponseEntity<>(createCategoryResponse, HttpStatus.CREATED);
  }

  @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListCategoryResponse> findAllCategories() {
    return new ResponseEntity<>(listCategoryService.findAll(), HttpStatus.OK);
  }

}
