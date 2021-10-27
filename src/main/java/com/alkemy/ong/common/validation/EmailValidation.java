package com.alkemy.ong.common.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {

  private static final String EMAIL_PATTERN =
      "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

  public static boolean isValid(String email) {
    if (email == null) {
      return false;
    }

    Pattern p = java.util.regex.Pattern.compile(EMAIL_PATTERN);
    Matcher m = p.matcher(email);
    return m.matches();
  }
}
