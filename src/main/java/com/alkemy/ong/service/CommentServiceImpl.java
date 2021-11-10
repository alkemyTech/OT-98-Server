package com.alkemy.ong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
  public Comment create(CreateCommentRequest createCommentRequest) {
    // TODO Auto-generated method stub
    Comment comment = new Comment();
    Long userId = createCommentRequest.getUserId();
    Long newsId = createCommentRequest.getNewsId();
    comment.setBody(createCommentRequest.getBody());
    comment.setUserId(userRepository.findById(userId).get());
    comment.setNewsId(newsRepository.findById(newsId).get());
    commentRepository.save(comment);
    return comment;
  }
}
