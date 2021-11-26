package com.alkemy.ong.service;

import com.alkemy.ong.common.JwtUtil;
import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.exception.OperationNotAllowedException;
import com.alkemy.ong.model.entity.Comment;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.CreateCommentRequest;
import com.alkemy.ong.model.request.UpdateCommentRequest;
import com.alkemy.ong.model.response.CommentDetailsResponse;
import com.alkemy.ong.model.response.DetailsCommentResponse;
import com.alkemy.ong.model.response.ListCommentsResponse;
import com.alkemy.ong.model.response.NewsDetailsCommentsResponse;
import com.alkemy.ong.repository.ICommentRepository;
import com.alkemy.ong.repository.INewsRepository;
import com.alkemy.ong.repository.IUserRepository;
import com.alkemy.ong.service.abstraction.ICreateCommentService;
import com.alkemy.ong.service.abstraction.IDeleteCommentsService;
import com.alkemy.ong.service.abstraction.IListCommentsService;
import com.alkemy.ong.service.abstraction.IListNewsService;
import com.alkemy.ong.service.abstraction.IUpdateCommentService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements ICreateCommentService, IDeleteCommentsService,
    IListCommentsService, IUpdateCommentService {

  @Autowired
  private ICommentRepository commentRepository;

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private INewsRepository newsRepository;

  @Autowired
  private JwtUtil jwtUtil;

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
  public void delete(long id, String authorizationHeader) throws OperationNotAllowedException {
    Comment comment = getComment(id);

    throwExceptionIfOperationIsNotAllowed(
        authorizationHeader,
        comment,
        "User is not able to delete comment.");

    commentRepository.delete(comment);
  }

  private boolean haveRole(String nameRole, List<Role> roles) {
    return roles.stream().anyMatch(role -> nameRole.equals(role.getName()));
  }

  @Override
  @Transactional
  public ListCommentsResponse list() {
    List<Comment> comments = commentRepository.findAll();
    return convertUtils.toListCommentsResponse(comments);
  }


  @Override
  @Transactional
  public DetailsCommentResponse update(long id, String authorizationHeader,
      UpdateCommentRequest updateCommentRequest)
      throws OperationNotAllowedException {

    Comment comment = getComment(id);
    throwExceptionIfOperationIsNotAllowed(
        authorizationHeader,
        comment,
        "User is not able to update comment.");

    comment.setBody(updateCommentRequest.getBody());
    commentRepository.save(comment);
    return convertUtils.updateCommentResponse(comment);
  }

  private void throwExceptionIfOperationIsNotAllowed(
      String authorizationHeader,
      Comment comment,
      String errorMessage) {
    User user = getUser(authorizationHeader);
    boolean isRoleAdmin = haveRole(ApplicationRole.ADMIN.getFullRoleName(), user.getRoles());

    if (!comment.getUserId().getId().equals(user.getId()) && !isRoleAdmin) {
      throw new OperationNotAllowedException(errorMessage);
    }
  }

  private Comment getComment(long id) {
    Optional<Comment> commentOptional = commentRepository.findById(id);
    if (commentOptional.isEmpty()) {
      throw new EntityNotFoundException("Comment not found");
    }
    return commentOptional.get();
  }

  @Override
  @Transactional
  public NewsDetailsCommentsResponse listNewsWithComments(long id) throws EntityNotFoundException {


    List<Comment> commentsList = commentRepository.findByNewsId(id);

    if (commentsList.size() == 0) {
      throw new EntityNotFoundException("News not found");
    }

    NewsDetailsCommentsResponse newsResponse = new NewsDetailsCommentsResponse();

    List<CommentDetailsResponse> commentsResponse = new ArrayList();

    News news = commentsList.get(0).getNewsId();
    newsResponse.setId(news.getId());
    newsResponse.setName(news.getName());
    newsResponse.setContent(news.getContent());
    newsResponse.setImage(news.getImage());

    for (Comment comment : commentsList) {
      commentsResponse.add(new CommentDetailsResponse(comment.getId(), comment.getBody()));
    }
    newsResponse.setComments(commentsResponse);

    return newsResponse;
  }



  // @Override
  // @Transactional
  // public ListCommentsResponse listCommentsWithNewsIdPrueba(Long id) throws
  // EntityNotFoundException {
  //
  // ListCommentsResponse newsResponse = new ListCommentsResponse();
  //
  // List<Comment> commentList = newsRepository.getAll(id);
  // if(commentList.isEmpty()) {
  // throw new EntityNotFoundException("Comment not found");
  // }
  // News news = commentList.get(0).getNewsId();
  // newsResponse.setId(news.getId());
  // newsResponse.setName(news.getName());
  // newsResponse.setContent(news.getContent());
  // newsResponse.setImage(news.getImage());
  // newsResponse.setListComentsResponse(
  // commentList.stream().map(comment -> convertUtils.detailsNewsCommentsToResponse(comment))
  // .collect(
  // Collectors.toList()));
  //
  //
  // }
}
