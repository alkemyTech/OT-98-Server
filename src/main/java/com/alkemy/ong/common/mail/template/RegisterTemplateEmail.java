package com.alkemy.ong.common.mail.template;

import com.alkemy.ong.common.mail.IContent;
import com.alkemy.ong.common.mail.IEmail;
import java.text.MessageFormat;

public class RegisterTemplateEmail implements IEmail, IContent {

  private static final String TYPE = "text/plain";
  private static final String SUBJECT = "Register Successfully";
  private static final String WELCOME_TEXT = "Welcome to {0}!";

  private String emailTo;
  private String image;
  private String organizationName;
  private String address;
  private Integer phone;

  public RegisterTemplateEmail(String emailTo, String image, String organizationName,
      String address,
      Integer phone) {
    this.emailTo = emailTo;
    this.image = image;
    this.organizationName = organizationName;
    this.address = address;
    this.phone = phone;

  }

  @Override
  public String getType() {
    return TYPE;
  }

  @Override
  public String getValue() {
    return image
        + "\n" + organizationName
        + "\n" + MessageFormat.format(WELCOME_TEXT, organizationName)
        + "\n" + "Address: " + address
        + "\n" + "Telephone: " + phone;
  }

  @Override
  public String getEmailTo() {
    return emailTo;
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
