package com.alkemy.ong.emailSenderTest;

import com.alkemy.ong.util.emailsender.IEmailSend;
import com.sendgrid.helpers.mail.objects.Content;

public class EmailTestClass implements IEmailSend {

  public EmailTestClass() {}

  @Override
  public String getEmailTo() {
    return "kevinraimolugo@gmail.com";
  }

  @Override
  public String getSubject() {
    return "Test";
  }

  @Override
  public Content getEmailContent() {
    return new Content("text/plain", "This is an email example");
  }

}
