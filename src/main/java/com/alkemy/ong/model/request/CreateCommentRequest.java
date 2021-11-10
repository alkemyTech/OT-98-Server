package com.alkemy.ong.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.alkemy.ong.common.validation.ValidationMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCommentRequest {

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  private String body;

  @NotNull(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  private Long userId;

  @NotNull(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  private Long newsId;
}
