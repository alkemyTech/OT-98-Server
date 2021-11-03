package com.alkemy.ong.common.imageUploader;

import java.util.UUID;

public class ValidateFileName implements IValidateFileName {

  public String validate(String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      UUID uuid = UUID.randomUUID();
      return uuid.toString();
    }
    return fileName.replace(" ", "_");
  }
}