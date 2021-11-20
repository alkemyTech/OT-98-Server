package com.alkemy.ong.service;

import com.alkemy.ong.common.JwtUtil;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.exception.UnableToDeleteObjectException;
import com.alkemy.ong.model.entity.Comment;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.CreateCommentRequest;
import com.alkemy.ong.model.response.DetailsNewsCommentsResponse;
import com.alkemy.ong.model.response.ListNewsCommentsResponse;
import com.alkemy.ong.repository.ICommentRepository;
import com.alkemy.ong.repository.INewsRepository;
import com.alkemy.ong.repository.IUserRepository;
import com.alkemy.ong.service.abstraction.ICreateCommentService;
import com.alkemy.ong.service.abstraction.IDeleteCommentsService;
import com.alkemy.ong.service.abstraction.IListNewsCommentService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements ICreateCommentService, IDeleteCommentsService,
    IListNewsCommentService {

  @Autowired
  private ICommentRepository commentRepository;

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private INewsRepository newsRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private IListNewsCommentService listNewsCommentService;

  @Autowired
  private ConvertUtils convertUtils;

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
  public void delete(long id, String authorizationHeader) throws UnableToDeleteObjectException {
    Optional<Comment> commentOptional = commentRepository.findById(id);
    if (commentOptional.isEmpty()) {
      throw new EntityNotFoundException("Comment not found");
    }

    Comment comment = commentOptional.get();
    User user = getUser(authorizationHeader);
    boolean isRoleAdmin = haveRole(ApplicationRole.ADMIN.getName(), user.getRoles());

    if (!comment.getUserId().getId().equals(user.getId()) && !isRoleAdmin) {
      throw new UnableToDeleteObjectException("User is not able to delete comment.");
    }

    commentRepository.delete(comment);
  }

  private boolean haveRole(String nameRole, List<Role> roles) {
    return roles.stream().anyMatch(role -> nameRole.equals(role.getName()));
  }

  @Override
  public ListNewsCommentsResponse findByNews(Long id) throws EntityNotFoundException {

    List<Comment> comments = commentRepository.findAll().stream()
        .filter(comment -> comment.getNewsId().getId() == id).collect(
            Collectors.toList());

    List<DetailsNewsCommentsResponse> detailsNewsCommentsResponseList = convertUtils.toDetailsNewsCommentsResponse(
        comments);

    return new ListNewsCommentsResponse(detailsNewsCommentsResponseList);
  }
}
