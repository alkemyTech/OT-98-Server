package com.alkemy.ong.external;

import com.alkemy.ong.common.mail.EmailHelper;
import com.alkemy.ong.common.mail.IContent;
import com.alkemy.ong.common.mail.IEmail;
import com.alkemy.ong.exception.SendEmailException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class SendEmailIntegrationTest {

  @Autowired
  private EmailHelper emailHelper;

  @Test
  public void shouldSendEmail() throws SendEmailException {
    emailHelper.send(new EmailTest());
  }

  public static class EmailTest implements IEmail, IContent {

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
}
