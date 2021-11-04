package com.alkemy.ong.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.alkemy.ong.common.validation.ValidationMessages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateActivityRequest {

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  @Pattern(regexp = ValidationMessages.REGEX_VALIDATION_STRING,
      message = ValidationMessages.REGEX_VALIDATION_STRING_MESSAGE)
  @Size(max = 250, message = ValidationMessages.REQUEST_PARAM_MAX_ERROR_MESSAGE)
  private String name;

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  @Size(max = 250, message = ValidationMessages.REQUEST_PARAM_MAX_ERROR_MESSAGE)
  private String content;

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  @Size(max = 250, message = ValidationMessages.REQUEST_PARAM_MAX_ERROR_MESSAGE)
  private String image;
}
