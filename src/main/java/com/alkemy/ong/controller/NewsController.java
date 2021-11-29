package com.alkemy.ong.controller;

import com.alkemy.ong.common.PaginatedResultsHeaderUtils;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.PageOutOfRangeException;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.request.NewsDetailsRequest;
import com.alkemy.ong.model.response.ListCommentsResponse;
import com.alkemy.ong.model.response.ListNewsResponse;
import com.alkemy.ong.model.response.NewsDetailsCommentsResponse;
import com.alkemy.ong.model.response.NewsDetailsResponse;
import com.alkemy.ong.service.abstraction.ICreateNewsService;
import com.alkemy.ong.service.abstraction.IDeleteNewsService;
import com.alkemy.ong.service.abstraction.IGetNewsService;
import com.alkemy.ong.service.abstraction.IListCommentsService;
import com.alkemy.ong.service.abstraction.IListNewsService;
import com.alkemy.ong.service.abstraction.IUpdateNewsService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/news")
public class NewsController {

  @Autowired
  private ICreateNewsService createNewsService;

  @Autowired
  private IDeleteNewsService deleteNewsService;

  @Autowired
  private IGetNewsService getNewsService;

  @Autowired
  private IListNewsService listNewsService;

  @Autowired
  private IListCommentsService listCommentsService;

  @Autowired
  private IUpdateNewsService updateNewsService;

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  private PaginatedResultsHeaderUtils paginatedResultsHeaderUtils;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Create news", description = "Service for create news")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201",
          description = "News created correctly"),
      @ApiResponse(responseCode = "403",
          description = "You are not authorized to make the request")
  })
  public ResponseEntity<NewsDetailsResponse> create(
      @RequestBody(required = true) @Valid NewsDetailsRequest createNewsRequest)
      throws EntityNotFoundException {
    NewsDetailsResponse newsDetailsResponse = convertUtils.createToResponse(
        createNewsService.create(createNewsRequest));
    return new ResponseEntity<>(newsDetailsResponse, HttpStatus.CREATED);
  }


  @DeleteMapping("/{id}")
  @Operation(summary = "Delete news", description = "Service for delete news")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204",
          description = "News deleted"),
      @ApiResponse(responseCode = "403",
          description = "You are not authorized to make the request"),
      @ApiResponse(responseCode = "404",
          description = "News not found"),
  })
  public ResponseEntity<Empty> delete(@PathVariable("id") long id)
      throws EntityNotFoundException {
    deleteNewsService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Get new", description = "Service to get new")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Request made successfully"),
      @ApiResponse(responseCode = "403",
          description = "You are not authorized to make the request"),
      @ApiResponse(responseCode = "404",
          description = "News not found")
  })
  public ResponseEntity<NewsDetailsResponse> getBy(@PathVariable("id") long id)
      throws EntityNotFoundException {
    NewsDetailsResponse newsDetailsResponse = convertUtils.getToResponse(getNewsService.getBy(id));
    return new ResponseEntity<>(newsDetailsResponse, HttpStatus.OK);
  }


  @GetMapping(value = "/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Get comments from new", description = "Service to get comments from new")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Request made successfully"),
      @ApiResponse(responseCode = "403",
          description = "You are not authorized to make the request"),
      @ApiResponse(responseCode = "404",
          description = "News not found")
  })
  public ResponseEntity<NewsDetailsCommentsResponse> listNewsWithComments(
      @PathVariable("id") long id)
      throws EntityNotFoundException {
    NewsDetailsCommentsResponse newsDetailsCommentsResponse =
        listCommentsService.listNewsWithComments(id);
    return new ResponseEntity<>(newsDetailsCommentsResponse, HttpStatus.OK);
  }


  @GetMapping(params = "page")
  @Operation(summary = "List news", description = "Service for get all news with pagination")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Request made successfully"),
      @ApiResponse(responseCode = "400",
          description = "Request error"),
      @ApiResponse(responseCode = "403",
          description = "You are not authorized to make the request")
  })
  public ResponseEntity<ListNewsResponse> list(@RequestParam("page") int page,
      UriComponentsBuilder uriBuilder,
      HttpServletResponse response) throws PageOutOfRangeException {
    Page<News> pageResponse = listNewsService.list(page, PaginatedResultsHeaderUtils.PAGE_SIZE);

    paginatedResultsHeaderUtils.addLinkHeaderOnPagedResult(uriBuilder,
        response,
        page,
        pageResponse.getTotalPages(),
        "/news");

    ListNewsResponse toResponse = convertUtils.listNewsToResponse(pageResponse.getContent());
    return new ResponseEntity<>(toResponse, HttpStatus.OK);
  }


  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Update new", description = "Service for update news")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Request made successfully"),
      @ApiResponse(responseCode = "403",
          description = "You are not authorized to make the request"),
      @ApiResponse(responseCode = "404",
          description = "New not found")
  })
  public ResponseEntity<NewsDetailsResponse> update(@PathVariable("id") long id,
      @RequestBody(required = true) @Valid NewsDetailsRequest newsDetailsRequest) {
    NewsDetailsResponse newsDetailsResponse =
        convertUtils.createToResponse(updateNewsService.update(newsDetailsRequest, id));
    return new ResponseEntity<>(newsDetailsResponse, HttpStatus.OK);
  }
}
