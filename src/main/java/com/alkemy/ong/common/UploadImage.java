package com.alkemy.ong.common.imageUploader;

import com.alkemy.ong.config.AmazonConfig;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.InputStream;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

public class UploadImage {

  @Autowired
  private AmazonConfig amazonConfig;
  @Autowired
  private IValidateFileName validateFileName;
  @Autowired
  private IConvertFile convertFile;
  @Autowired
  private AmazonS3 amazonS3 = amazonConfig.initialize();

  public String uploadImage(InputStream inputStream, String fileName, ContentType contentType) {
    String url = amazonConfig.getEndpointUrl();
    String bucketName = amazonConfig.getBucketName();
    String fileUrl = "";
    try {
      File file = convertFile.convertInputstreamToFile(inputStream, fileName, contentType);
      String validatedFileName = validateFileName.validate(fileName);
      fileUrl = url + "/" + bucketName + "/" + validatedFileName + "." + contentType.getMimeType();
      uploadImageTos3Bucket(validatedFileName, file, bucketName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileUrl;
  }

  private void uploadImageTos3Bucket(String validatedFileName, File file, String bucketName) {
    amazonS3.putObject(new PutObjectRequest(bucketName, validatedFileName, file)
        .withCannedAcl(CannedAccessControlList.PublicRead));
  }

}

