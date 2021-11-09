package com.alkemy.ong.model.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonRootName("news")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListNewsResponse {

  private List<NewsDetailsResponse> news;
}
