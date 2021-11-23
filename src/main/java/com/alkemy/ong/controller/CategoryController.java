package com.alkemy.ong.controller;

import com.alkemy.ong.common.PaginatedResultsHeaderUtils;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.common.messages.DocumentationMessages;
import com.alkemy.ong.exception.EntityAlreadyExistException;
import com.alkemy.ong.exception.PageOutOfRangeException;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CategoryUpdateRequest;
import com.alkemy.ong.model.request.CreateCategoryRequest;
import com.alkemy.ong.model.response.CategoriesResponse;
import com.alkemy.ong.model.response.CreateCategoryResponse;
import com.alkemy.ong.model.response.DetailsCategoryResponse;
import com.alkemy.ong.model.response.ListCategoryResponse;
import com.alkemy.ong.service.abstraction.ICreateCategoryService;
import com.alkemy.ong.service.abstraction.IDeleteCategoryService;
import com.alkemy.ong.service.abstraction.IGetCategoryService;
import com.alkemy.ong.service.abstraction.IListCategoryService;
import com.alkemy.ong.service.abstraction.IUpdateCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Tag(name = DocumentationMessages.CATEGORY_CONTROLLER,
    description = DocumentationMessages.CATEGORY_CONTROLLER_DESCRIPTION)
public class CategoryController {

  @Autowired
  private ICreateCategoryService createCategoryService;

  @Autowired
  private IGetCategoryService getCategoryService;

  @Autowired
  private IListCategoryService listCategoryService;

  @Autowired
  private IUpdateCategoryService updateCategoryService;

  @Autowired
  private IDeleteCategoryService deleteCategoryService;

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  private PaginatedResultsHeaderUtils paginatedResultsHeaderUtils;

  @PostMapping(value = "/categories",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = DocumentationMessages.CATEGORY_CONTROLLER_SUMMARY_CREATE)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201",
          description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_201_DESCRIPTION),
      @ApiResponse(responseCode = "403",
          description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_403_DESCRIPTION)
  })
  public ResponseEntity<CreateCategoryResponse> create(
      @Valid @RequestBody CreateCategoryRequest createCategoryRequest)
      throws EntityAlreadyExistException {
    Category category = createCategoryService.create(createCategoryRequest);
    CreateCategoryResponse createCategoryResponse = convertUtils.toResponse(category);
    return new ResponseEntity<>(createCategoryResponse, HttpStatus.CREATED);
  }

  @GetMapping(params = "page", value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = DocumentationMessages.CATEGORY_CONTROLLER_SUMMARY_LIST)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_200_DESCRIPTION),
      @ApiResponse(responseCode = "400",
          description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_400_DESCRIPTION),
      @ApiResponse(responseCode = "403",
          description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_403_DESCRIPTION)
  })
  public ResponseEntity<ListCategoryResponse> findAllCategories(@RequestParam("page") int page,
      UriComponentsBuilder uriBuilder,
      HttpServletResponse response) throws PageOutOfRangeException {
    Page<Category>categoryPage = listCategoryService.findAll(page, PaginatedResultsHeaderUtils.PAGE_SIZE);
    paginatedResultsHeaderUtils.addLinkHeaderOnPagedResult(
        uriBuilder,
        response,
        page,
        categoryPage.getTotalPages(),
        "/categories"
    );
    List<CategoriesResponse> categoriesResponses = convertUtils.toCategoriesResponse(categoryPage.getContent());
    ListCategoryResponse listCategoryResponse = new ListCategoryResponse(categoriesResponses);
    return new ResponseEntity<>(listCategoryResponse, HttpStatus.OK);
  }

  @GetMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DetailsCategoryResponse> getCategoryDetails(@PathVariable("id") long id)
      throws EntityNotFoundException {
    return new ResponseEntity<>(getCategoryService.getBy(id), HttpStatus.OK);
  }

  @PutMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = DocumentationMessages.CATEGORY_CONTROLLER_SUMMARY_UPDATE)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_200_DESCRIPTION),
      @ApiResponse(responseCode = "403",
          description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_403_DESCRIPTION),
      @ApiResponse(responseCode = "404",
          description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<DetailsCategoryResponse> update(@PathVariable("id") long id,
      @Valid @RequestBody
          CategoryUpdateRequest categoryUpdateRequest) throws EntityAlreadyExistException {
    DetailsCategoryResponse detailsCategoryResponse = updateCategoryService.update(
        categoryUpdateRequest, id);
    return new ResponseEntity<>(detailsCategoryResponse, HttpStatus.OK);
  }

  @DeleteMapping(value = "/categories/{id}")
  @Operation(summary = DocumentationMessages.CATEGORY_CONTROLLER_SUMMARY_DELETE)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204",
          description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_204_DESCRIPTION),
      @ApiResponse(responseCode = "403",
          description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_403_DESCRIPTION),
      @ApiResponse(responseCode = "404",
          description = DocumentationMessages.CATEGORY_CONTROLLER_RESPONSE_404_DESCRIPTION)
  })
  public ResponseEntity<Long> deleteBy(@PathVariable long id) throws EntityNotFoundException {
    deleteCategoryService.deleteBy(id);
    return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
  }
}
