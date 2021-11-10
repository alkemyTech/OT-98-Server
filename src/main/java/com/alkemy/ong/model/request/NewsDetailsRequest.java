package com.alkemy.ong.model.request;

import com.alkemy.ong.common.validation.ValidationMessages;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsDetailsRequest {

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  private String name;

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  private String content;

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  private String image;

}
