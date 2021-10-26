package com.alkemy.ong.common.mail;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import com.alkemy.ong.exception.SendEmailException;
import com.alkemy.ong.util.emailsender.IEmailSend;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class EmailHelper {
  @Value("${email.sender.from}")
  private String emailFrom;

  @Value("${email.sender.sendgrid.token}")
  private String sendGridToken;

  private static final String SEND_ENDPOINT = "mail/send";

  public void send(IEmailSend emailBody) throws IOException, SendEmailException {
    Email from = new Email(emailFrom);
    String subject = emailBody.getSubject();
    Email to = new Email(emailBody.getEmailTo());
    Content content = emailBody.getEmailContent();

    Mail mail = new Mail(from, subject, to, content);
    SendGrid sendGrid = new SendGrid(sendGridToken);
    Request request = new Request();

    try {
      request.setMethod(Method.POST);
      request.setEndpoint(SEND_ENDPOINT);
      request.setBody(mail.build());
      Response response = sendGrid.api(request);

      if (!(response.getStatusCode() >= 200 || response.getStatusCode() < 300))
        throw new SendEmailException("The email has not sent");

    } catch (IOException e) {
      throw new SendEmailException(e.getMessage());
    }
  }
}
