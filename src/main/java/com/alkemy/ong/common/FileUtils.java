package com.alkemy.ong.common;

import java.util.UUID;

public class FileUtils {

  private FileUtils() {
  }

  public static String getFileNameOrDefault(String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      UUID uuid = UUID.randomUUID();
      return uuid.toString();
    }
    return fileName.replace(" ", "_");
  }
}
