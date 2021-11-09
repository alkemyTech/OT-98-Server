package com.alkemy.ong.model.request;

import com.alkemy.ong.common.mail.IContent;
import com.alkemy.ong.common.mail.IEmail;
import com.alkemy.ong.common.validation.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateContactRequest implements IEmail, IContent {

  private static final String TYPE = "text/plain";

  private static final String VALUE = "Thank you for contact us";

  private static final String SUBJECT = "Contact Receive";

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  @Size(max = 250, message = ValidationMessages.REQUEST_PARAM_MAX_ERROR_MESSAGE)
  private String name;

  private String phone;

  @NotBlank(message = ValidationMessages.REQUEST_PARAM_EMPTY_ERROR_MESSAGE)
  @Size(max = 250, message = ValidationMessages.REQUEST_PARAM_MAX_ERROR_MESSAGE)
  private String email;

  private String message;

  @Override
  public String getType() {
    return TYPE;
  }

  @Override
  public String getValue() {
    return VALUE;
  }

  @Override
  public String getEmailTo() {
    return email;
  }

  @Override
  public String getSubject() {
    return SUBJECT;
  }

  @Override
  public IContent getContent() {
    return this;
  }
}
