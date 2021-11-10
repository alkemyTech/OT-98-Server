package com.alkemy.ong.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentResponse {
  private long id;
  private String body;
  private long userId;
  private long newsId;
}
