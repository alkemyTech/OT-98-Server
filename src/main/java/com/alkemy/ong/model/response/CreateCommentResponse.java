package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@JsonRootName("comment")
@Getter
@Setter
public class CreateCommentResponse {
  private long id;
  private String body;
  private long userId;
  private long newsId;
}
