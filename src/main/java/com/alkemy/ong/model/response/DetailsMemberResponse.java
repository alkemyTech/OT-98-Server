package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonRootName("member")
@Getter
@Setter
@NoArgsConstructor
public class DetailsMemberResponse {

  private long id;
  private String name;
  private String image;
  private String description;
  private Timestamp timestamp;
  private String facebookUrl;
  private String linkedinUrl;
  private String instagramUrl;

}
