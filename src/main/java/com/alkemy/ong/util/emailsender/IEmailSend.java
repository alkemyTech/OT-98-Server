package com.alkemy.ong.util.emailsender;

import com.sendgrid.helpers.mail.objects.Content;

public interface IEmailSend {
  String getEmailTo();

  String getSubject();

  Content getEmailContent();
}
