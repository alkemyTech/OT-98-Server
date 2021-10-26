package com.alkemy.ong.emailsender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import com.alkemy.ong.util.emailsender.EmailService;

@SpringBootTest
public class EmailSendTest {

  @Autowired
  private EmailService emailService;

  @Test
  public void sendEmailTest() {
    try {
      assertEquals(HttpStatus.ACCEPTED.value(), emailService.sendEmail(new EmailTestClass()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
