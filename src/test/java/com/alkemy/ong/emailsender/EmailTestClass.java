package com.alkemy.ong.emailsender;

import com.alkemy.ong.util.emailsender.IEmailSend;
import com.sendgrid.helpers.mail.objects.Content;

public class EmailTestClass implements IEmailSend {

  @Override
  public String getEmailTo() {
    return "kevinraimolugo@gmail.com";
  }

  @Override
  public String getSubject() {
    return "This is a test";
  }

  @Override
  public Content getEmailContent() {
    return new Content("text/plain", "Hii!, this is a test");
  }

}
