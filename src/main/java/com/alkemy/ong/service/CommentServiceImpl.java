package com.alkemy.ong.service;

import com.alkemy.ong.common.JwtUtil;
import com.alkemy.ong.model.entity.Comment;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.CreateCommentRequest;
import com.alkemy.ong.repository.ICommentRepository;
import com.alkemy.ong.repository.INewsRepository;
import com.alkemy.ong.repository.IUserRepository;
import com.alkemy.ong.service.abstraction.ICreateCommentService;
import com.alkemy.ong.service.abstraction.IDeleteCommentsService;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements ICreateCommentService, IDeleteCommentsService {

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

  @Override
  public boolean delete(long id, String authorizationHeader) {
    Optional<Comment> commentOptional = commentRepository.findById(id);
    User user = getUser(authorizationHeader);
    boolean roleAdmin = haveRole("ROLE_ADMIN", user.getRoles());

    if (commentOptional.isEmpty()) {
      throw new EntityNotFoundException("Comment not found");
    }

    Comment comment = commentOptional.get();

    if (comment.getUserId().getId() == user.getId() || roleAdmin) {
      commentRepository.delete(comment);
      return true;
    }

    return false;

  }

  private boolean haveRole(String nameRole, List<Role> roles) {
    for (Role role : roles) {
      if (role.getName().equals(nameRole)) {
        return true;
      }
    }
    return false;
  }
}
