package com.alkemy.ong.controller;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.model.entity.Comment;
import com.alkemy.ong.model.request.CreateCommentRequest;
import com.alkemy.ong.model.response.CreateCommentResponse;
import com.alkemy.ong.service.abstraction.ICreateCommentService;
import com.alkemy.ong.service.abstraction.IDeleteCommentsService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

  @Autowired
  ICreateCommentService createCommentService;

  @Autowired
  IDeleteCommentsService deleteCommentsService;

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

  @DeleteMapping("/comments/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") long id,
      @RequestHeader(value = "Authorization") String authorizationHeader) {
    boolean deleteComment = deleteCommentsService.delete(id, authorizationHeader);
    if (deleteComment) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>("Not authorized", HttpStatus.FORBIDDEN);
    }
  }

}
