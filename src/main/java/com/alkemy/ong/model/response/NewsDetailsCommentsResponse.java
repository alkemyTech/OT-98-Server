package com.alkemy.ong.model.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonRootName("news")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsDetailsCommentsResponse {
  @JsonInclude(Include.NON_NULL)
  private Long id;
  private String name;
  private String content;
  private String image;
  private List<CommentDetailsResponse> comments;

}
