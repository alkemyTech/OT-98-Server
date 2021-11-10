package com.alkemy.ong.service;

import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alkemy.ong.exception.NewsNotFoundException;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.model.entity.Comment;
import com.alkemy.ong.model.request.CreateCommentRequest;
import com.alkemy.ong.repository.ICommentRepository;
import com.alkemy.ong.repository.INewsRepository;
import com.alkemy.ong.repository.IUserRepository;
import com.alkemy.ong.service.abstraction.ICreateCommentService;

@Service
public class CommentServiceImpl implements ICreateCommentService {

  @Autowired
  ICommentRepository commentRepository;

  @Autowired
  IUserRepository userRepository;

  @Autowired
  INewsRepository newsRepository;



  @Override
  public Comment create(CreateCommentRequest createCommentRequest)
      throws UserNotFoundException, NewsNotFoundException {
    Comment comment = new Comment();
    Long userId = createCommentRequest.getUserId();
    Long newsId = createCommentRequest.getNewsId();

    if (userRepository.findById(userId) == null) {
      throw new UserNotFoundException(MessageFormat.format("User {0} not found.", userId));
    }
    if (newsRepository.findById(userId) == null) {
      throw new NewsNotFoundException(MessageFormat.format("News {0} not found.", newsId));
    }

    comment.setBody(createCommentRequest.getBody());
    comment.setUserId(userRepository.findById(userId).get());
    comment.setNewsId(newsRepository.findById(newsId).get());
    commentRepository.save(comment);
    return comment;

  }
}
