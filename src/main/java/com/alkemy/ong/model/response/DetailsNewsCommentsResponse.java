package com.alkemy.ong.model.response;

import com.alkemy.ong.model.entity.News;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailsNewsCommentsResponse {

  private long id;
  private String body;
  private News newsId;
}
