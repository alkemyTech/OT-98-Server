package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.OperationNotAllowedException;
import com.alkemy.ong.model.entity.Comment;
import com.alkemy.ong.model.request.CreateCommentRequest;
import com.alkemy.ong.model.request.UpdateCommentRequest;
import com.alkemy.ong.model.response.CreateCommentResponse;
import com.alkemy.ong.model.response.DetailsCommentResponse;
import com.alkemy.ong.model.response.ListCommentsResponse;
import com.alkemy.ong.service.abstraction.ICreateCommentService;
import com.alkemy.ong.service.abstraction.IDeleteCommentsService;
import com.alkemy.ong.service.abstraction.IListCommentsService;
import com.alkemy.ong.service.abstraction.IUpdateCommentService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

  @Autowired
  private ICreateCommentService createCommentService;

  @Autowired
  private IDeleteCommentsService deleteCommentsService;

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  private IListCommentsService listCommentsService;

  @Autowired
  private IUpdateCommentService updateCommentService;

  @PostMapping(value = "/comments", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CreateCommentResponse> create(
      @Valid @RequestBody CreateCommentRequest createCommentRequest,
      @RequestHeader(value = "Authorization") String authorizationHeader) {
    Comment comment = createCommentService.create(createCommentRequest, authorizationHeader);
    CreateCommentResponse createCommentResponse = convertUtils.toResponse(comment);
    return new ResponseEntity<>(createCommentResponse, HttpStatus.CREATED);
  }

  @DeleteMapping("/comments/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") long id,
      @RequestHeader(value = "Authorization") String authorizationHeader)
      throws OperationNotAllowedException {
    deleteCommentsService.delete(id, authorizationHeader);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListCommentsResponse> list() {
    return new ResponseEntity<>(listCommentsService.list(), HttpStatus.OK);
  }

  @PutMapping(
      value = "/comments/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DetailsCommentResponse> update(
      @PathVariable("id") long id,
      @RequestHeader(value = "Authorization") String authorizationHeader,
      @Valid @RequestBody(required = true) UpdateCommentRequest updateCommentRequest)
      throws OperationNotAllowedException {
    return new ResponseEntity<>(
        updateCommentService.update(id, authorizationHeader, updateCommentRequest),
        HttpStatus.OK);
  }
}
