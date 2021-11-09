package com.alkemy.ong.model.request;

import com.alkemy.ong.common.mail.IContent;
import com.alkemy.ong.common.mail.IEmail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactTemplateEmail implements IEmail, IContent {

  private static final String TYPE = "text/plain";

  private static final String VALUE = "Thank you for contact us";

  private static final String SUBJECT = "Contact Receive";

  private String email;

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
