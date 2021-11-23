package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.ForbiddenAccessException;
import com.alkemy.ong.model.request.UpdateCommentRequest;
import com.alkemy.ong.model.response.DetailsCommentResponse;

public interface IUpdateCommentService {

  DetailsCommentResponse update(long id, String authorizationHeader, UpdateCommentRequest updateCommentRequest)
      throws ForbiddenAccessException;
}
