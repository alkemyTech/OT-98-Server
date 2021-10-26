package com.alkemy.ong.util.emailsender;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {

  @Value("${email.sender.from}")
  private String emailFrom;

  @Value("${email.sender.sendgrid.token}")
  private String sendGridToken;

  public int sendEmail(IEmailSend emailBody) throws IOException {
    Email from = new Email(emailFrom);
    String subject = emailBody.getSubject();
    Email to = new Email(emailBody.getEmailTo());
    Content content = emailBody.getEmailContent();

    Mail mail = new Mail(from, subject, to, content);
    SendGrid sg = new SendGrid(sendGridToken);
    Request request = new Request();

    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      return response.getStatusCode();
    } catch (IOException e) {
      throw e;
    }
  }
}
