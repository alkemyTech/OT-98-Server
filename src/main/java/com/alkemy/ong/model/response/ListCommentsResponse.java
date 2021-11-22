package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonRootName("comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListCommentsResponse {

  private List<CommentsResponse> comments;

}
