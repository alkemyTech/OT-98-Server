package com.alkemy.ong.model.response;

import com.alkemy.ong.model.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSlideResponse {

  private long id;
  private String image_Url;
  private String text;
  private int order;
  private Organization organizationId;

}
