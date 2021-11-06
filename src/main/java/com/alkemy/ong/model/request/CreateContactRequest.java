package com.alkemy.ong.model.request;

import com.alkemy.ong.common.validation.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateContactRequest {
  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  @Size(max = 250, message = ValidationMessages.REQUEST_PARAM_MAX_ERROR_MESSAGE)
  private String name;

  private String phone;

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  @Size(max = 250, message = ValidationMessages.REQUEST_PARAM_MAX_ERROR_MESSAGE)
  private String email;
  private String message;


}
