package com.alkemy.ong.emailsender;

import com.alkemy.ong.common.mail.IContent;
import com.alkemy.ong.common.mail.IEmail;

public class EmailTestClass implements IEmail, IContent {

  @Override
  public String getEmailTo() {
    return "kevinraimolugo@gmail.com";
  }

  @Override
  public String getSubject() {
    return "This is a test";
  }

  @Override
  public IContent getContent() {
    return this;
  }

  @Override
  public String getType() {
    return "text/plain";
  }

  @Override
  public String getValue() {
    return "Hi, this is a test";
  }

}
