package com.alkemy.ong.service;

import com.alkemy.ong.common.JwtUtil;
import com.alkemy.ong.model.entity.Comment;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.CreateCommentRequest;
import com.alkemy.ong.repository.ICommentRepository;
import com.alkemy.ong.repository.INewsRepository;
import com.alkemy.ong.repository.IUserRepository;
import com.alkemy.ong.service.abstraction.ICreateCommentService;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements ICreateCommentService {

  @Autowired
  private ICommentRepository commentRepository;

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private INewsRepository newsRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public Comment create(CreateCommentRequest createCommentRequest, String authorizationHeader) {
    User user = getUser(authorizationHeader);

    Comment comment = new Comment();
    comment.setBody(createCommentRequest.getBody());
    comment.setUserId(user);
    comment.setNewsId(getNews(createCommentRequest));
    commentRepository.save(comment);
    return comment;
  }

  private News getNews(CreateCommentRequest createCommentRequest) {
    Optional<News> news = newsRepository.findById(createCommentRequest.getNewsId());
    if (news.isEmpty()) {
      throw new EntityNotFoundException("News not found.");
    }
    return news.get();
  }

  private User getUser(String authorizationHeader) {
    String username = jwtUtil.extractUsername(authorizationHeader);
    User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new EntityNotFoundException("User not found.");
    }
    return user;
  }

}
