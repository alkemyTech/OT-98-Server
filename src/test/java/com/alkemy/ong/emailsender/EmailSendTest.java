package com.alkemy.ong.emailsender;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.alkemy.ong.common.mail.EmailHelper;
import com.alkemy.ong.exception.SendEmailException;

@SpringBootTest
public class EmailSendTest {

  @Autowired
  private EmailHelper emailHelper;

  @Test
  public void sendEmailTest() {
    boolean emailSend = false;
    try {
      emailHelper.send(new EmailTestConcreteClass());
      emailSend = true;
    } catch (SendEmailException e) {
      emailSend = false;
      e.printStackTrace();
    } finally {
      assertTrue(emailSend);
    }

  }
}
