package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@JsonRootName("member")
@Getter
@Setter
public class CreateMemberResponse {
  private String name;
  private String image;
}
