package com.alkemy.ong.model.request;

import com.alkemy.ong.common.CustomErrorMessageModelValidation;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RequestUser {

  @NotBlank(message = CustomErrorMessageModelValidation.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  @Size(max = 250, message = CustomErrorMessageModelValidation.REQUEST_PARAM_MAX_ERROR_MESSAGE)
  private String firstName;

  @NotBlank(message = CustomErrorMessageModelValidation.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  @Size(max = 250, message = CustomErrorMessageModelValidation.REQUEST_PARAM_MAX_ERROR_MESSAGE)
  private String lastName;

  @Email(message = CustomErrorMessageModelValidation.REQUEST_PARAM_EMAIL_ERROR_MESSAGE)
  @NotBlank(message = CustomErrorMessageModelValidation.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  @Size(max = 250, message = CustomErrorMessageModelValidation.REQUEST_PARAM_MAX_ERROR_MESSAGE)
  private String email;

  @NotBlank(message = CustomErrorMessageModelValidation.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  @Size(max = 250, message = CustomErrorMessageModelValidation.REQUEST_PARAM_MAX_ERROR_MESSAGE)
  private String password;

}
