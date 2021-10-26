package com.alkemy.ong.emailSenderTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import com.alkemy.ong.util.emailsender.EmailService;

@SpringBootTest(properties = {"email.sender.from=alkemyot98@gmail.com",
    "email.sender.sendgrid.token=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"})
public class EmailSendTest {

  @Autowired
  private EmailService es;

  @Test
  public void sendEmail() {
    try {
      assertEquals(HttpStatus.ACCEPTED.value(), es.sendEmail(new EmailTestClass()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
