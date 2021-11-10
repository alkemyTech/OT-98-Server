package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.NewsNotFoundException;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.model.entity.Comment;
import com.alkemy.ong.model.request.CreateCommentRequest;

public interface ICreateCommentService {

  Comment create(CreateCommentRequest createCommentRequest)
      throws UserNotFoundException, NewsNotFoundException;
}
