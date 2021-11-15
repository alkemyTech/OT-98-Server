package com.alkemy.ong.common.mail.template;

import com.alkemy.ong.common.mail.IContent;
import com.alkemy.ong.common.mail.IEmail;

public class RegisterTemplateEmail implements IEmail, IContent {

  private static final String TYPE = "text/plain";
  private static final String SUBJECT = "Register Successfully";

  private String emailTo;
  private String image;
  private String name;
  private String welcomeText;
  private String address;
  private Integer phone;
  private String email;

  public RegisterTemplateEmail(String emailTo, String image, String name,
      String welcomeText, String address, Integer phone, String email) {
    this.emailTo = emailTo;
    this.image = image;
    this.name = name;
    this.welcomeText = welcomeText;
    this.address = address;
    this.phone = phone;
    this.email = email;
  }

  @Override
  public String getType() {
    return TYPE;
  }

  @Override
  public String getValue() {
    return image
        + "\n" + name
        + "\n" + welcomeText
        + "\n" + address
        + "\n" + phone
        + "\n" + email;
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
