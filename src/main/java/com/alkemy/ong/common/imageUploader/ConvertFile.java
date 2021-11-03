package com.alkemy.ong.common.imageUploader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.entity.ContentType;

public class ConvertFile implements IConvertFile {

  public static final int DEFAULT_BUFFER_SIZE = 8192;

  public File convertInputstreamToFile(InputStream inputStream, String fileName, ContentType contentType) throws IOException {
    File file = File.createTempFile(fileName, contentType.getMimeType());

    try (FileOutputStream fos = new FileOutputStream(file, false)) {
      int read;
      byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
      while ((read = inputStream.read(bytes)) != -1) {
        fos.write(bytes, 0, read);
      }
    }
    return file;
  }
}
