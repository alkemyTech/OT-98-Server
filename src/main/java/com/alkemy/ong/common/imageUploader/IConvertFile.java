package com.alkemy.ong.common.imageUploader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.entity.ContentType;

public interface IConvertFile {
  File convertInputstreamToFile(InputStream inputStream, String fileName, ContentType contentType) throws IOException;
}
