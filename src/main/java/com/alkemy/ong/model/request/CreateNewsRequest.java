package com.alkemy.ong.model.request;

import javax.validation.constraints.NotBlank;
import com.alkemy.ong.common.validation.ValidationMessages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNewsRequest {

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  private String name;

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  private String content;

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  private String image;
}
