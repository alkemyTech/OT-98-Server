package com.alkemy.ong.model.request;

import com.alkemy.ong.common.validation.ValidationMessages;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSlideRequest {

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  private String image;

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  private String text;

  private int order;

}
