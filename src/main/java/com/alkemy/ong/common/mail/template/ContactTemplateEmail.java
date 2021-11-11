package com.alkemy.ong.common.mail.template;

import com.alkemy.ong.common.mail.IContent;
import com.alkemy.ong.common.mail.IEmail;

public class ContactTemplateEmail implements IEmail, IContent {

  private static final String TYPE = "text/plain";
  private static final String VALUE = "Thank you for contact us";
  private static final String SUBJECT = "Contact Receive";

  private final String email;

  public ContactTemplateEmail(String email) {
    this.email = email;
  }

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
