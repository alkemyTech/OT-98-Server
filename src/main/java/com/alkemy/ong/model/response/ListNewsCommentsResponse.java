package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("comments")
public class ListNewsCommentsResponse {

  @JsonProperty("listComment")
  private List<DetailsNewsCommentsResponse> listComentsResponse;

}
