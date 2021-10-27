package com.alkemy.ong.common.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {

  public static boolean isValid(String email) {

    if (email == null)
      return false;

    String ePattern =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    Pattern p = java.util.regex.Pattern.compile(ePattern);
    Matcher m = p.matcher(email);
    return m.matches();
  }
}
