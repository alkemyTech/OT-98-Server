package com.alkemy.ong.service;

import java.text.MessageFormat;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alkemy.ong.common.JwtUtil;
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

  @Autowired
  private JwtUtil jwtUtil;

  private void validateUserId(Long userId) {
    if (userId == null) {
      throw new EntityNotFoundException(MessageFormat.format("News {0} not found.", userId));
    }
  }

  private void validateNewsId(Long newsId) {
    if (newsId == null) {
      throw new EntityNotFoundException(MessageFormat.format("News {0} not found.", newsId));
    }
  }

  @Override
  public Comment create(CreateCommentRequest createCommentRequest, String authorizationHeader) {
    String username = jwtUtil.extractUsername(authorizationHeader);

    Long userId = userRepository.findByEmail(username).getId();

    Comment comment = new Comment();

    Long newsId = createCommentRequest.getNewsId();

    validateUserId(userId);
    validateNewsId(newsId);

    comment.setBody(createCommentRequest.getBody());
    comment.setUserId(userRepository.findByEmail(username));
    comment.setNewsId(newsRepository.findById(newsId).get());
    commentRepository.save(comment);
    return comment;

  }
}
