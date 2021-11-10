package com.alkemy.ong.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Comment;
import com.alkemy.ong.model.request.CreateCommentRequest;
import com.alkemy.ong.model.response.CreateCommentResponse;
import com.alkemy.ong.service.abstraction.ICreateCommentService;

@RestController
public class CommentController {

  @Autowired
  ICreateCommentService createCommentService;

  @Autowired
  private ConvertUtils convertUtils;

  @PostMapping(value = "/comments", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CreateCommentResponse> create(
      @Valid @RequestBody CreateCommentRequest createCommentRequest,
      @RequestHeader(value = "Authorization") String authorizationHeader) {
    Comment comment = createCommentService.create(createCommentRequest, authorizationHeader);
    CreateCommentResponse createCommentResponse = convertUtils.toResponse(comment);
    return new ResponseEntity<>(createCommentResponse, HttpStatus.CREATED);
  }
}
