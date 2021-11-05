package com.alkemy.ong.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteNewsResponse {

   private long id;

   private String content;

   private String image;

   private String category;

}
