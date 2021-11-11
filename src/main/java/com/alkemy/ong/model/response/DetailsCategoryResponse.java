package com.alkemy.ong.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("category")
public class DetailsCategoryResponse {

  private long id;

  private String name;

  private String description;

  private String image;

  private Timestamp timestamp;

  private boolean softDelete;

}
